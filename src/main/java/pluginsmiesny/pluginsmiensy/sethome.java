package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
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

            try {
                plugin.addHome(id, homelocation);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Bukkit.getLogger().info(id.toString().length() + "");
            player.sendRawMessage(ChatColor.YELLOW + "Home successfully set!");

        }else{
            return false;
        }

    return true;
    }
}
