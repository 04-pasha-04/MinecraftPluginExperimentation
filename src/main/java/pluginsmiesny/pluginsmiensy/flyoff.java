package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class flyoff implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("flyoff")) {

            if (sender instanceof Player) {
                if (sender.isOp()) {
                    if (!Objects.equals(args[0], "")) {
                        Bukkit.getPlayerExact(args[0]).setAllowFlight(false);
                        return true;
                    } else {
                        sender.sendMessage("Enter the name of the player you want to remove flight ability from!");
                        return false;
                    }
                } else {
                    sender.sendMessage("You don't have permission to do this!");
                    return false;
                }

            }
        }

        return false;
    }
}
