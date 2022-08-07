package pluginsmiesny.pluginsmiensy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class sethome implements CommandExecutor {


    private final PluginSmiensy plugin;
    public sethome(PluginSmiensy plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof  Player) {
            Player player = (Player) sender;
            Location homelocation = player.getLocation();
            UUID id = player.getUniqueId();

            plugin.addHome(id, homelocation);

            player.sendRawMessage(ChatColor.YELLOW + "Home successfully set!");

        }else{
            return false;
        }

    return true;
    }
}
