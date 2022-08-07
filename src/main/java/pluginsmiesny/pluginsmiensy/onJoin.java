package pluginsmiesny.pluginsmiensy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        String name = event.getPlayer().getDisplayName();

        if(!event.getPlayer().hasPlayedBefore()){
            event.setJoinMessage("New player joined! " + name + " welcome to the server!");
        }

        event.setJoinMessage("Welcome back! " + name);
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage(event.getPlayer().getDisplayName() + " disconnected from the server!");
    }
}
