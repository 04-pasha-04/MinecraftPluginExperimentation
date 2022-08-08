package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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



        if((action == Action.RIGHT_CLICK_BLOCK) && (item.getType() == Material.STICK)){
            Location l = event.getClickedBlock().getLocation();
            UUID id = event.getPlayer().getUniqueId();

            if(!this.plugin.isBlockInRes(l)) {
                this.plugin.resPointSet(id, l);
            }else{
                Bukkit.getPlayer(id).sendRawMessage(ChatColor.RED + "There is already a res here!");
            }
        }

    }
}
