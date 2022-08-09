package pluginsmiesny.pluginsmiensy;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.UUID;

public class tp implements CommandExecutor
{
    private final PluginSmiensy plugin;
    public tp(PluginSmiensy plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args[0].length() < 1){
                return false;
            }else{
                UUID recipient = Bukkit.getPlayerExact(args[0]).getUniqueId();
                if(args[0] != null){
                    if(!Bukkit.getPlayer(recipient).isOnline()) {
                        player.sendRawMessage(ChatColor.RED + "That player is offline!");
                    } else {
                        this.plugin.sendTpRequest(recipient, player.getUniqueId());
                        player.sendRawMessage(ChatColor.YELLOW + "tp request sent to " + Bukkit.getPlayer(recipient).getDisplayName());
                        Bukkit.getPlayer(recipient).sendRawMessage(ChatColor.YELLOW  + player.getDisplayName() + " sent you a tp request!" + "\n" + "Type [/tpa] to accept.");
                    }
                }else{
                    player.sendRawMessage(ChatColor.RED + "Enter the name of a player you want to send a tp request!");
                    return false;
                }

            }

        } else {
            return false;
        }

        return true;
    }
}
