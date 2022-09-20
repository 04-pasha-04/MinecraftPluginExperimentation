package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.List;
import java.util.UUID;


public class CanDo implements Listener {

    private final PluginSmiensy plugin;

    public CanDo(PluginSmiensy plugin){
        this.plugin = plugin;

    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event){
        UUID playerId = event.getPlayer().getUniqueId();
        boolean willbecancelled = false;
        //if the block is in a res checks if the player is a member or owner and if he is not cancels the blockbreak event
        if(this.plugin.isBlockInRes(event.getBlock().getLocation())){
            resObject res = this.plugin.getBlockRes(event.getBlock().getLocation());
            List<UUID> members = res.getMembers();
            if(!res.isOwner(playerId) && !res.isMember(playerId)){
                willbecancelled = true;
            }
        }
        event.setCancelled(willbecancelled);
        if(willbecancelled){event.getPlayer().sendRawMessage(ChatColor.RED + "You don't have permission to destroy blocks here");}


    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        UUID playerId = event.getPlayer().getUniqueId();
        boolean willbecancelled = false;
        //if the block is in a res checks if the player is a member or owner and if he is not cancels the blockplace event
        if(this.plugin.isBlockInRes(event.getBlock().getLocation())){
            resObject res = this.plugin.getBlockRes(event.getBlock().getLocation());
            List<UUID> members = res.getMembers();
            if(!res.isOwner(playerId) && !res.isMember(playerId)){
                willbecancelled = true;
            }
        }
        event.setCancelled(willbecancelled);
        if(willbecancelled){event.getPlayer().sendRawMessage(ChatColor.RED + "You don't have permission to place blocks here");}
    }

    //finish this!!! (chest protection under res)
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest || e.getInventory().getHolder() instanceof Furnace){

            }
        }


}
