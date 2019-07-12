package de.xcraft.jan.xcraftexperience.handler;

import de.xcraft.jan.xcraftexperience.commands.CheckCommand;
import de.xcraft.jan.xcraftexperience.commands.CreateCommand;
import de.xcraft.jan.xcraftexperience.commands.HelpCommand;
import de.xcraft.jan.xcraftexperience.commands.SetCommand;
import org.bukkit.Bukkit;
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
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length == 1) { //Was soll angezeigt werden wenn der Spieler /b eingibt... Es werden weitere Überprüfungen gemacht ob der jenige Spieler Rechte hat für die einzelnen Befehle
                if (player.hasPermission("xcraftexperience.help")) {
                    completions.add("help");
                    completions.add("info");
                }
                if (player.hasPermission("xcraftexperience.check")) completions.add("check");
                if (player.hasPermission("xcraftexperience.create")) completions.add("create");
                if (player.hasPermission("xcraftexperience.set")) completions.add("set");
                return completions;
            } else if (args.length == 2 && args[0].equals("check") && player.hasPermission("xcraftexperience.check.other")) { // Überprüfung ob der Spieler ein Mod/Admin ist und die Namen der Spieler angezeigt werden
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
                return completions;
            } else if (args.length == 2 && args[0].equals("create")) { //Überprüfung ob der Spieler /b create eingegeben hat
                completions.add("<Amount>");
                return completions;
            } else if (args.length == 2 && args[0].equals("set") && player.hasPermission("xcraftexperience.set")) { //Überprüfung ob der Spieler /b set eingegeben hat und auch die Permissions dafür hat
                completions.add("euronencost");
                completions.add("experiencecost");
                completions.add("diplayedname");
                completions.add("lore");
                return completions;
            } else if (args.length == 3 && args[0].equals("set") && player.hasPermission("xcraftexperience.set")) { // Überprüfung ob der Spieler /b set gemacht hat und auch die Permissions hat um weitere hilfe zu bekommen
                completions.add("<value>");
                return completions;
            }
        }
        return completions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("info")) {
                if (player.hasPermission("xcraftexperience.help")) {
                    HelpCommand.PluginInfo(player, plugin);
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
                }
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
                    if (args.length == 2 && !args[1].equals("")) {
                        if (args.length == 2 && isInt(args[1])) {
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
                        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_UNFINISHED_COMMAND"));
                    }
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
                }
            } else if (args[0].equals("set")) {
                if (player.hasPermission("xcraftexperience.set")) {
                    if (args.length >= 2 && !args[1].equals("")) {
                        if (args.length == 3 && !args[2].equals("")) {
                            SetCommand.setCommand(args[1], args[2], player);
                        } else {
                            player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_UNFINISHED_COMMAND"));
                        }
                    } else {
                        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_UNFINISHED_COMMAND"));
                    }
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_PERMISSIONS"));
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