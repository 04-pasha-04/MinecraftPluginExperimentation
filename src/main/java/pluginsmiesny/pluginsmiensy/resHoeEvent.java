package pluginsmiesny.pluginsmiensy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class resHoeEvent implements Listener {

    private final PluginSmiensy plugin;
    public resHoeEvent(PluginSmiensy plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){

        Action action = event.getAction();
        ItemStack item = event.getItem();

        if(item == null){return;}
        if(action == null){return;}


        if((action == Action.RIGHT_CLICK_BLOCK) && (item.getType() == Material.STICK)){
            Location l = event.getClickedBlock().getLocation();
            UUID id = event.getPlayer().getUniqueId();
            this.plugin.resPointSet(id, l);
        }

    }
}
