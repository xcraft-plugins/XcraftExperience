package de.xcraft.jan.xcraftwonderfulpotion.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler implements CommandExecutor {

    JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.GRAY +" " + plugin.getConfig().getString("PLUGIN_PREFIX") +" " + ChatColor.GREEN + "Help:");
                player.sendMessage(ChatColor.GRAY + "->" + ChatColor.GREEN + "/xp check " + ChatColor.DARK_AQUA + " - Zeigt dir an wie viele Erfahrungsfläschchen du herstellen kannst.");
                player.sendMessage(ChatColor.GRAY + "->" + ChatColor.GREEN + "/xp create <amount>" + ChatColor.DARK_AQUA + " - Erstellt Erfahrungsfläschchen.");
            } else if (args[0].equals("check")) {
                if (player.hasPermission("xcraftexperience.check")) {
                    player.sendMessage(CheckCommand.checkPlayer(player, plugin));
                }
                else if (player.hasPermission("xcraftexperience.check.other")) {
                    player.sendMessage(CheckCommand.checkAdmin(player, args[1]));
                }
                else {
                    player.sendMessage(ChatColor.RED + "Das darfst du nicht tun!");
                }
            } else if (args[0].equals("create")) {
                if (player.hasPermission("xcraftexperience.create")) {
                    player.sendMessage(CreateCommand.createPlayer(player, Integer.parseInt(args[1]), plugin));
                } else {
                    player.sendMessage(ChatColor.RED + "Das darfst du nicht tun!");
                }
            }
        }
        return false;
    }

    public FileConfiguration getConfig(){
        return this.getConfig();
    }
}