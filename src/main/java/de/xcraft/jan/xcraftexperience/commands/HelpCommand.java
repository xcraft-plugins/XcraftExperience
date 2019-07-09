package de.xcraft.jan.xcraftexperience.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

/**
 * Class for info command.
 */
public class HelpCommand {
    public static void PluginInfo(Player player, JavaPlugin plugin) {
        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.GOLD + plugin.getDescription().getVersion() + " by " + plugin.getDescription().getAuthors());
        player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_PLAYER_CHECK_HELP_TEXT"));
        if (player.hasPermission("xcraftexperience.check.other"))
            player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_ADMIN_CHECK_HELP_TEXT"));
        if (player.hasPermission("xcraftexperience.set"))
            player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_ADMIN_SET_HELP_TEXT"));
        player.sendMessage(ChatColor.GRAY + "->" + MESSAGEHANDLER.getConfiguration().getString("BOTTLE_CREATE_HELP_TEXT"));
    }
}
