package de.xcraft.xcraftexperience.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("check");
            completions.add("create");

            return completions;
        } else if (args.length > 1 && args[0].equals("create")) {
            completions.add("<Amount>");

            return completions;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            completions.add(p.getName());
        }

        return completions;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.GRAY +" " + plugin.getConfig().getString("PLUGIN_PREFIX") +" " + ChatColor.GREEN + "Help:");
                player.sendMessage(ChatColor.GRAY + "->" + ChatColor.GREEN + "/bottle check " + ChatColor.DARK_AQUA + " - Zeigt dir an wie viele Erfahrungsfläschchen du herstellen kannst.");
                player.sendMessage(ChatColor.GRAY + "->" + ChatColor.GREEN + "/bottle create <amount>" + ChatColor.DARK_AQUA + " - Erstellt Erfahrungsfläschchen.");
            } else if (args[0].equals("check") && args.length == 1) {
                if (player.hasPermission("xcraftexperience.check")) {
                    player.sendMessage(CheckCommand.checkPlayer(player, plugin));
                }
                else {
                    player.sendMessage(ChatColor.RED + "Das darfst du nicht tun!");
                }
            } else if (args[0].equals("check") && args[1] != null) {
                if (player.hasPermission("xcraftexperience.check.other")) {
                    player.sendMessage(CheckCommand.checkAdmin(player, args[1], plugin));
                }
                else {
                    player.sendMessage(ChatColor.RED + "Das darfst du nicht tun!");
                }
            } else if (args[0].equals("create")) {
                if (player.hasPermission("xcraftexperience.create")) {
                    if(args.length == 2 && !args[1].equals("")) {
                        if(isInt(args[1])) {
                            player.sendMessage(CreateCommand.createPlayer(player, Integer.parseInt(args[1]), plugin));
                        } else {
                            player.sendMessage(ChatColor.RED + "Du musst eine Zahl eingeben!");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Du musst eine Zahl eingeben!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Das darfst du nicht tun!");
                }
            } else if (args[0].equals("info")){
                if (player.hasPermission("xcraftexperience.info")) {
                    player.sendMessage(InfoCommand.PluginInfo(player, plugin));
                }
            }
        }
        return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}