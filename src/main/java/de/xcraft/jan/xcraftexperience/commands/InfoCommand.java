package de.xcraft.jan.xcraftexperience.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

/**
 * Class for info command.
 */
public class InfoCommand {
    public static String PluginInfo(Player player, JavaPlugin plugin) {
        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_GREEN + " " + ChatColor.UNDERLINE + "Info:");
        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Version: " + plugin.getDescription().getVersion());
        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Umbreon & MyNameIsJan");
        player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX"));
        return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " GitHub: https://github.com/UmbreonMajora/XcraftExperience";
    }
}
