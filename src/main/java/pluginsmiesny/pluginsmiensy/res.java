package pluginsmiesny.pluginsmiensy;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class res implements CommandExecutor {

    private final PluginSmiensy plugin;
    public res(PluginSmiensy plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 2){
                player.sendRawMessage(args.length + "");
                if(args[0].equalsIgnoreCase("create")){
                    if(this.plugin.isIntersectingWithOtherRes(this.plugin.getRes(player.getUniqueId()))){
                        player.sendRawMessage(ChatColor.RED + "Your res intersects with someone else's res!");
                        return false;
                    }
                    this.plugin.getRes(player.getUniqueId()).setName(args[1]);
                    this.plugin.getRes(player.getUniqueId()).solidify();
                    player.sendRawMessage(ChatColor.GREEN + "Res created with name: " + args[1]);
                    return true;
                }else if(args[0].equalsIgnoreCase("delete")){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        }else{
            return  false;
        }

    }

}
