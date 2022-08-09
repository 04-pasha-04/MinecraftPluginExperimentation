package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class PluginSmiensy extends JavaPlugin implements Listener {

    private HashMap<UUID, Location> homes;
    private HashMap<UUID, UUID> tprequests;

    private HashMap<UUID, List<resObject>> reses;

    private HashMap<UUID, List<Location>> templocs;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.homes = new HashMap<UUID, Location>();
        this.tprequests = new HashMap<UUID, UUID>();
        this.reses = new HashMap<UUID, List<resObject>>();
        this.templocs = new HashMap<UUID, List<Location>>();
        getCommand("res").setExecutor(new res(this));
        getCommand("tpa").setExecutor(new tpa(this));
        getCommand("tp").setExecutor(new tp(this));
        getCommand("home").setExecutor(new home(this));
        getCommand("sethome").setExecutor(new sethome(this));
        getCommand("flyon").setExecutor(new flyon());
        getCommand("flyoff").setExecutor(new flyoff());
        getCommand("die").setExecutor(new die());
        getServer().getPluginManager().registerEvents(new resStickEvent(this), this);
        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new CanDo(this), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void addHome(UUID id, Location loc){
        this.homes.put(id, loc);
    }

    public Location getHome(UUID id){
        if(homes.containsKey(id)){
            return homes.get(id);
        }else{
            return null;
        }

    }

    public void sendTpRequest(UUID recipient, UUID requester){
        this.tprequests.put(recipient, requester);
    }

    public UUID getTpRequest(UUID recipient){
        if(this.tprequests.containsKey(recipient)){
            return this.tprequests.get(recipient);
        }else{
            return null;
        }


    }

    public void deleteTpRequest(UUID recipient){
        this.tprequests.remove(recipient);
    }

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

    public Location getTx(UUID id) {
        if(this.templocs.get(id) == null){
            return null;
        }else {
            return this.templocs.get(id).get(0);
        }
    }

    public Location getTy(UUID id) {
        if(this.templocs.get(id) == null){
            return null;
        }else {
            return this.templocs.get(id).get(1);
        }
    }

    public boolean isBlockInRes(Location l){
        for(List<resObject> valueList : reses.values()) {
            for(resObject value : valueList) {
                if(value.isInRes(l)){return true;};
            }
        }
        return false;
    }


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



    //work on edge cases!!!
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

    public void deleteRes(UUID id, String name){
        if(this.reses.containsKey(id)){
            for(int i=0; i<this.reses.get(id).size(); i++){
                if(this.reses.get(id).get(i).getName().equalsIgnoreCase(name)){
                    this.reses.get(id).remove(i);
                }
            }
        }
    }

    public void addRes(UUID id, resObject res){
        if(this.reses.containsKey(id)){
            this.reses.get(id).add(res);
        }else{
            this.reses.put(id, new ArrayList<resObject>());
            this.reses.get(id).add(res);
        }
    }

    public List<Location> getTempLocs(UUID id){
        return this.templocs.get(id);
    }

    public List<resObject> getResListById(UUID id){
        return this.reses.get(id);
    }







}
