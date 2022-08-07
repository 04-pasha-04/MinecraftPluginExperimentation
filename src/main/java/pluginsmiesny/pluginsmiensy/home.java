package pluginsmiesny.pluginsmiensy;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class home implements CommandExecutor {

    private final PluginSmiensy plugin;
    public home(PluginSmiensy plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(plugin.getHome(player.getUniqueId()) == null){
                player.sendRawMessage(Color.RED + "You don't have a home set!");
            }else{
                player.teleport(plugin.getHome(player.getUniqueId()));
            }
        }

        return false;
    }
}
