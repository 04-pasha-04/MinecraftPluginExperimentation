package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class PluginSmiensy extends JavaPlugin implements Listener {

    private database db;
    private HashMap<UUID, Location> homes;
    private HashMap<UUID, UUID> tprequests;

    private HashMap<UUID, List<resObject>> reses;

    private HashMap<UUID, List<Location>> templocs;
    @Override
    public void onEnable() {

        this.homes = new HashMap<UUID, Location>();
        this.tprequests = new HashMap<UUID, UUID>();
        this.reses = new HashMap<UUID, List<resObject>>();
        this.templocs = new HashMap<UUID, List<Location>>();

        db = new database(this);
        try {
            db.updateHomesMap();
            db.updateResesMap();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        getCommand("res").setExecutor(new res(this));
        getCommand("tpa").setExecutor(new tpa(this));
        getCommand("tp").setExecutor(new tp(this));
        getCommand("home").setExecutor(new home(this));
        getCommand("sethome").setExecutor(new sethome(this));
        getCommand("flyon").setExecutor(new flyon());
        getCommand("flyoff").setExecutor(new flyoff());
        getCommand("die").setExecutor(new die());
        getServer().getPluginManager().registerEvents(new resStickEvent(this), this);
        getServer().getPluginManager().registerEvents(new onRespawn(this), this);
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new CanDo(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public database getDb(){
        return this.db;
    }

    //adds a home location with a player's id as a key to the homes hashmap
    public void addHome(UUID id, Location loc) throws SQLException, ClassNotFoundException {

        db.addHome(id, loc, Bukkit.getWorlds().get(0));
        this.homes.put(id, loc);
    }

    //return location where player has set his home to be
    public Location getHome(UUID id){
        if(homes.containsKey(id)){
            return homes.get(id);
        }else{
            return null;
        }

    }

    //sends a tp request to a player and stores it temporarily in the tprequests hashmap with the recipient's id as a key
    public void sendTpRequest(UUID recipient, UUID requester){
        this.tprequests.put(recipient, requester);
    }

    //returns id of the recipient if the tprequest
    public UUID getTpRequest(UUID recipient){
        if(this.tprequests.containsKey(recipient)){
            return this.tprequests.get(recipient);
        }else{
            return null;
        }


    }

    //removes the tprequest form the tprequests hashmap
    public void deleteTpRequest(UUID recipient){
        this.tprequests.remove(recipient);
    }

    //saves the locations of stick res marks temporarily in the templocs hashmap under players id
    public void resPointSet(UUID id, Location l){

        Player player = Bukkit.getPlayer(id);

        if(!this.templocs.containsKey(id)){
            this.templocs.put(id, new ArrayList<Location>(2));
            this.templocs.get(id).add(0, l);
            this.templocs.get(id).add(1, null);
            player.sendRawMessage(ChatColor.YELLOW + "First point set at: " + "\n" + l);
        }
        else if(!this.templocs.get(id).isEmpty()){
            if(this.templocs.get(id).get(1) != null)  {
                this.templocs.remove(id);
                player.sendRawMessage(ChatColor.YELLOW + "Selection cleared!");
            }else if(this.templocs.get(id).get(1) == null){
                this.templocs.get(id).add(1, l);
                player.sendRawMessage(ChatColor.YELLOW + "Residence selected!" + "\n" + "type [/res create <name>] to create the residence!");
            }
        }

    }

    //returns the first of the two res point locations that a player has set
    public Location getTx(UUID id) {
        if(this.templocs.get(id) == null){
            return null;
        }else {
            return this.templocs.get(id).get(0);

        }
    }

    //returns the second of the two res point locations that a player has set
    public Location getTy(UUID id) {
        if(this.templocs.get(id) == null){
            return null;
        }else {
            return this.templocs.get(id).get(1);
        }
    }

    //checks if block's locations is in any residence
    public boolean isBlockInRes(Location l){
        for(List<resObject> valueList : reses.values()) {
            for(resObject value : valueList) {
                if(value.isInRes(l)){return true;};
            }
        }
        return false;
    }


    //return the residence that the block is located in
    public resObject getBlockRes(Location l){
        for(List<resObject> list: this.reses.values()) {
            for(resObject res: list){
                if(res.isInRes(l)){
                    return res;
                }
            }
        }
        return null;
    }


    //returns the resobject that is under the specified id in the reses hashmap and with the specified name
    public resObject getRes(UUID id, String name){
        if(this.reses.containsKey(id)){
            for(int i=0; i<this.reses.get(id).size(); i++){
                if(this.reses.get(id).get(i).getName().equalsIgnoreCase(name)){
                    return this.reses.get(id).get(i);
                }
            }
            return null;
        }else{
            return null;
        }
    }



    //checks if the selected res intersects with any other created res
    public boolean isIntersectingWithOtherRes(resObject res){

        double bx,sx,by,sy,bz,sz;

        if(res.getX().getX() < res.getY().getX()){bx = res.getY().getX();sx = res.getX().getX();}else{sx = res.getY().getX();bx = res.getX().getX();}
        if(res.getX().getY() < res.getY().getY()){by = res.getY().getY();sy = res.getX().getY();}else{sy = res.getY().getY();by = res.getX().getY();}
        if(res.getX().getZ() < res.getY().getZ()){bz = res.getY().getZ();sz = res.getX().getZ();}else{sz = res.getY().getZ();bz = res.getX().getZ();}

        for(int x=0; x < bx-sx; x++){
            for(int y=0; y < by-sy; y++){
                for(int z=0; z < bz-sz; z++){
                    Location loc = new Location(Bukkit.getWorld(res.getOwner()), sx + x, sy + y, sz + z);
                    if(isBlockInRes(loc)){return true;}
                }
            }
        }

        return false;
    }

    //deletes the res with specified name under the specified id in the hashmap
    public void deleteRes(UUID id, String name) throws SQLException {
        if(this.reses.containsKey(id)){
            for(int i=0; i<this.reses.get(id).size(); i++){
                if(this.reses.get(id).get(i).getName().equalsIgnoreCase(name)){
                    this.db.deleteRes(id, name);
                    this.reses.get(id).remove(i);
                }
            }
        }
    }

    //creates a value pair in a hashmap and adds the res under the specified id
    public void addRes(UUID id, resObject res) throws SQLException, ClassNotFoundException {
        if(this.reses.containsKey(id)){
            this.reses.get(id).add(res);
            this.db.addRes(id, res);
        }else{
            this.reses.put(id, new ArrayList<resObject>());
            this.reses.get(id).add(res);
            this.db.addRes(id, res);
        }
    }


    public void hmAddRes(UUID id, resObject res){
        if(this.reses.containsKey(id)){
            this.reses.get(id).add(res);
        }else{
            this.reses.put(id, new ArrayList<resObject>());
            this.reses.get(id).add(res);
        }
    }

    //returns a list of temporary selected res corners that are under the specified id in the templocs hashmap
    public List<Location> getTempLocs(UUID id){
        return this.templocs.get(id);
    }

    //return a list of the residences from the reses hashmap that belong to a player with specified id
    public List<resObject> getResListById(UUID id){
        return this.reses.get(id);
    }







}
