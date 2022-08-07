package pluginsmiesny.pluginsmiensy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class die implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if(cmd.getName().equalsIgnoreCase("die")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.setHealth(0.0);
                return true;
            }else{
                return false;
            }
        }
            return false;
    }


}
