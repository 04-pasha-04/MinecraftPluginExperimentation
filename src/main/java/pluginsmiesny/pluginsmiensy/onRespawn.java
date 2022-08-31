package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

public class onRespawn implements Listener {

    private final PluginSmiensy plugin;
    public onRespawn(PluginSmiensy plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        UUID id = event.getPlayer().getUniqueId();
        Location loc = this.plugin.getHome(id);
        if(event.isBedSpawn()){;
            return;
        }else if(loc != null){
            event.setRespawnLocation(loc);
        }

    }
}
