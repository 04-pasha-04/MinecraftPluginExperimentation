package pluginsmiesny.pluginsmiensy;

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

        if (sender instanceof Player){
            Player player = (Player) sender;

            if((args[0] != null) && (args[1] != null)){
                String name = args[1];
                if(args[0] == "set"){


                }else if(args[0] == "delete"){

                }


            }else{
                return false;
            }
        }


        return true;
    }
}
