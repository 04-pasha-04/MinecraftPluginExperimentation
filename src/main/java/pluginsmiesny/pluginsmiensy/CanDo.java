package pluginsmiesny.pluginsmiensy;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public class CanDo implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event){
        event.setCancelled(true);
        event.getPlayer().sendRawMessage(ChatColor.RED + "You dont have permission to destroy blocks here");
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        event.setCancelled(true);
        event.getPlayer().sendRawMessage(ChatColor.RED + "You dont have permission to place blocks here");
    }


}
