package de.xcraft.jan.xcraftexperience.handler;

import de.xcraft.jan.xcraftexperience.commands.CheckCommand;
import de.xcraft.jan.xcraftexperience.commands.CreateCommand;
import de.xcraft.jan.xcraftexperience.commands.InfoCommand;
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

import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

/**
 * Handler class for all commands.
 */
public class CommandHandler implements CommandExecutor, TabCompleter {

    JavaPlugin plugin;
    CreateCommand createCommand;
    CheckCommand checkCommand;

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
                player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + " " + ChatColor.DARK_GREEN + "Help:");
                player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_CHECK_HELP_TEXT"));
                player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_CREATE_HELP_TEXT"));
            } else if (args[0].equals("check") && args.length == 1) {
                if (player.hasPermission("xcraftexperience.check")) {
                    checkCommand = new CheckCommand(player, plugin);
                    player.sendMessage(checkCommand.checkPlayer());
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
                }
            } else if (args[0].equals("check") && args[1] != null) {
                if (player.hasPermission("xcraftexperience.check.other")) {
                    checkCommand = new CheckCommand(player, plugin);
                    player.sendMessage(checkCommand.checkAdmin(args[1]));
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
                }
            } else if (args[0].equals("create")) {
                if (player.hasPermission("xcraftexperience.create")) {
                    if (args.length == 2 && !args[1].equals("") || args.length == 2 && isInt(args[1])) {
                        if (isPositive(args[1])) {
                            createCommand = new CreateCommand(player, args[1]);
                            player.sendMessage(createCommand.createPlayer());
                        } else {
                            player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NEGATIVE_NUMBER"));
                        }
                    } else {
                        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_NUMBER"));
                    }
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
                }
            } else if (args[0].equals("info")) {
                if (player.hasPermission("xcraftexperience.info")) {
                    player.sendMessage(InfoCommand.PluginInfo(player, plugin));
                }
            }
        }
        return false;
    }

    /**
     * @param str the Integer to check
     * @return true if it is an int
     */
    private boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param str the Integer to check
     * @return true if it is positive
     */
    private boolean isPositive(String str) {
        if (Integer.parseInt(str) > 0) {
            return true;
        } else {
            return false;
        }
    }
}