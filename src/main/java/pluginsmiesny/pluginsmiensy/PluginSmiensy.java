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

    private HashMap<UUID, resObject> reses;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.homes = new HashMap<UUID, Location>();
        this.tprequests = new HashMap<UUID, UUID>();
        this.reses = new HashMap<UUID, resObject>();
        getCommand("res").setExecutor(new res(this));
        getCommand("tpa").setExecutor(new tpa(this));
        getCommand("tp").setExecutor(new tp(this));
        getCommand("home").setExecutor(new home(this));
        getCommand("sethome").setExecutor(new sethome(this));
        getCommand("flyon").setExecutor(new flyon());
        getCommand("flyoff").setExecutor(new flyoff());
        getCommand("die").setExecutor(new die());
        getServer().getPluginManager().registerEvents(new resHoeEvent(this), this);
        getServer().getPluginManager().registerEvents(new onJoin(), this);
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

        resObject ro = this.reses.get(id);
        Player player = Bukkit.getPlayer(id);


        if(ro == null){
            this.reses.put(id, new resObject(l, null, id));
        }else if(ro.getX() == null && !ro.isSolidified()){
            ro.setX(l);
            player.sendRawMessage(ChatColor.YELLOW + "First point set at: " + "\n" + l);
        }else if(ro.getY() == null && !ro.isSolidified()){
            ro.setY(l);
            player.sendRawMessage(ChatColor.YELLOW + "Residence selected!" + "\n" + "type [/res create <name>] to create the residence!");
        }else if(ro.getX() != null && ro.getY() != null && !ro.isSolidified()){
            ro.locClear();
            player.sendRawMessage(ChatColor.YELLOW + "Selection cleared!");
        }

    }

    //finish these two functions!!!!!!!
    public boolean isBlockInRes(Location l){

        for(resObject value: this.reses.values()) {
            if(value.isInRes(l)){
                return true;
            }
        }
        return false;
    }

    public resObject getBlockRes(Location l){
        for(resObject value: this.reses.values()) {
            if(value.isInRes(l)){
                return value;
            }
        }
        return null;
    }


    public resObject getRes(UUID id){
        return this.reses.get(id);
    }

    //untested might not work!!! finish tomorrow
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







}
