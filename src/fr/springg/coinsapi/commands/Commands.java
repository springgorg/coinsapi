package fr.springg.coinsapi.commands;

import fr.springg.coinsapi.coins.Coins;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("money")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(args.length == 0){
                    p.sendMessage("§aVous avez §e" + new Coins().getCoins(p) + "§a€");
                    return false;
                } else if(args.length == 1) {
                    String targetName = args[0];
                    if (args[0].equalsIgnoreCase(targetName)) {
                        Player target = Bukkit.getPlayer(targetName);
                        if (target == null) {
                            p.sendMessage("§cCe joueur n'existe pas !");
                            return false;
                        } else if(target != null){
                            p.sendMessage("§a" + target.getName() + " a §e" + new Coins().getCoins(target) + "§a€");
                            return false;
                        }
                    }
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§cSeul un joueur peut executer cette commande !");
                return false;
            }
        }

        return false;
    }
}
