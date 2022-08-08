package pluginsmiesny.pluginsmiensy;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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
        if(this.plugin.isBlockInRes(event.getBlock().getLocation())){
            resObject res = this.plugin.getBlockRes(event.getBlock().getLocation());
            UUID resowner = res.getOwner();
            List<UUID> members = res.getMembers();
            if(resowner != playerId){
                willbecancelled = true;
            }else if(!members.contains(playerId)){
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
        if(this.plugin.isBlockInRes(event.getBlock().getLocation())){
            resObject res = this.plugin.getBlockRes(event.getBlock().getLocation());
            UUID resowner = res.getOwner();
            List<UUID> members = res.getMembers();
            if(resowner != playerId){
                willbecancelled = true;
            }else if(!members.contains(playerId)){
                willbecancelled = true;
            }
        }
        event.setCancelled(willbecancelled);
        if(willbecancelled){event.getPlayer().sendRawMessage(ChatColor.RED + "You don't have permission to place blocks here");}
    }


}
