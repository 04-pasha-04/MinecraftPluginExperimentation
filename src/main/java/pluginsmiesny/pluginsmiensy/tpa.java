package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class tpa implements CommandExecutor {

    private final PluginSmiensy plugin;
    public tpa(PluginSmiensy plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(this.plugin.getTpRequest(player.getUniqueId()) != null){
                UUID requester = this.plugin.getTpRequest(player.getUniqueId());
                Bukkit.getPlayer(requester).teleport(player.getLocation());
                this.plugin.deleteTpRequest(player.getUniqueId());
            }else{
                player.sendRawMessage(ChatColor.RED + "You don't have a tp request addressed to you!");
            }

        }else{
            return false;
        }

        return true;
    }
}
