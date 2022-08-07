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

    private HashMap<UUID, List<Location>> reses;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.homes = new HashMap<UUID, Location>();
        this.tprequests = new HashMap<UUID, UUID>();
        this.reses = new HashMap<UUID, List<Location>>();

        Bukkit.getLogger().info("Meow Meow Meow !!!");

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
        getServer().getPluginManager().registerEvents(new CanDo(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Mrrrp!");
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

        if(this.reses.get(id) != null){
            if(this.reses.get(id).isEmpty()){
                this.reses.get(id).add(0, l);
                Bukkit.getPlayer(id).sendRawMessage(ChatColor.YELLOW + "First point of the res selected!");
            }else{
                try {
                    this.reses.get(id).get(1);
                    this.reses.get(id).clear();
                    Bukkit.getPlayer(id).sendRawMessage(ChatColor.YELLOW + "Res selection cancelled!");
                } catch (IndexOutOfBoundsException e) {
                    this.reses.get(id).add(1, l);
                    Bukkit.getPlayer(id).sendRawMessage(ChatColor.YELLOW + "Res selected!" + "\n" + "type [/res set <name>] to set the selected res.");

                }
            }

        }else {
            List<Location> xy = new ArrayList<Location>(2);
            xy.add(0,l);
            this.reses.put(id, xy);
            Bukkit.getPlayer(id).sendRawMessage(ChatColor.YELLOW + "First point the res selected!");
        }




    }
    public List getLocations(UUID id){
        return this.reses.get(id);
    }







}
