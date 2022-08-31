package pluginsmiesny.pluginsmiensy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
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
            if(args.length == 1){


                //Listing all residencies that a player has


                if(args[0].equalsIgnoreCase("list")){
                    List<resObject> _reses= this.plugin.getResListById(player.getUniqueId());
                    if(_reses == null || _reses.isEmpty()){
                        player.sendRawMessage(ChatColor.RED + "You don't have any residences!");
                        return false;
                    }
                    player.sendRawMessage(ChatColor.YELLOW + "Your residences:");
                    for(resObject res: _reses){
                        player.sendRawMessage(ChatColor.YELLOW + res.getName());
                    }
                    return true;
                }
            }




            if(args.length == 2){

                //creating a residence

                player.sendRawMessage(args.length + "");
                if(args[0].equalsIgnoreCase("create")){
                    resObject newres;
                    if(this.plugin.getRes(player.getUniqueId(), args[1]) != null){
                        player.sendRawMessage(ChatColor.RED + "Can not create res: You already have a res with this name!" + "\n" + "Use a different name.");
                        return false;
                    }
                    if(this.plugin.getTempLocs(player.getUniqueId()) != null && this.plugin.getTx(player.getUniqueId()) != null && this.plugin.getTy(player.getUniqueId()) != null){
                        newres = new resObject(this.plugin.getTx(player.getUniqueId()), this.plugin.getTy(player.getUniqueId()), player.getUniqueId());
                        Bukkit.getLogger().info(newres.getX()+"");
                        Bukkit.getLogger().info(newres.getY()+"");
                    }else{
                        player.sendRawMessage(ChatColor.RED + "Can not create res: You didn't select the res points");
                        return false;
                    }
                    if(this.plugin.isIntersectingWithOtherRes(newres)){
                        player.sendRawMessage(ChatColor.RED + "Can not create res: Your res intersects with some other res!");
                        return false;
                    }
                    newres.setName(args[1]);
                    newres.solidify();
                    try {
                        this.plugin.addRes(player.getUniqueId(), newres);
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    player.sendRawMessage(ChatColor.GREEN + "Res created with name: " + args[1]);
                    return true;



                }else if(args[0].equalsIgnoreCase("delete")){


                    //deleting residence


                    if(this.plugin.getRes(player.getUniqueId(), args[0]) != null){
                        this.plugin.deleteRes(player.getUniqueId(), args[1]);
                        player.sendRawMessage(ChatColor.GREEN + "Desired res has been removed!");
                        return true;
                    }else{
                        player.sendRawMessage(ChatColor.RED + "Can not delete res: There is no such res!");
                        return false;
                    }

                }else{
                    return false;
                }



            }else if(args.length == 3){


                //adding/removing a player form a residence


                resObject res = this.plugin.getRes(player.getUniqueId(), args[1]);
                if(this.plugin.getRes(player.getUniqueId(), args[1]) == null){
                    player.sendRawMessage(ChatColor.RED + "Can not add/remove player: You don't have permission to do this or this res does not exist!");
                    return false;
                }
                if(args[0].equalsIgnoreCase("add")) {
                    //adding a player to a residence
                    if (res != null) {
                        if(Bukkit.getPlayerExact(args[2]) != null){
                            res.addMember(Bukkit.getPlayerExact(args[2]).getUniqueId());
                            player.sendRawMessage(ChatColor.GREEN + args[2] + " Has been added to your" + " '" + args[1] + "' res!");
                            return true;
                        }else{
                            player.sendRawMessage(ChatColor.RED + "Can not add player: There is no such player!");
                            return false;
                        }

                    } else {
                        return false;
                    }
                }else if(args[0].equalsIgnoreCase("remove")){
                    //removing a player from a residence
                    if(res != null){
                        if(Bukkit.getPlayerExact(args[2]) != null) {
                            res.removeMember(Bukkit.getPlayerExact(args[2]).getUniqueId());
                            return true;
                        }else{
                            player.sendRawMessage(ChatColor.RED + "Can not remove player: There is no such player!");
                            return false;
                        }
                    }else{
                        return false;
                    }
                }else{return false;}

            }else{
                return false;
            }

        }else{
            return  false;
        }

    }

}
