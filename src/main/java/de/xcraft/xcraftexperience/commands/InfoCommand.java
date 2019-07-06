package de.xcraft.xcraftexperience.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InfoCommand {
    public static String PluginInfo(Player player, JavaPlugin plugin){

        player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_GREEN + " " + ChatColor.UNDERLINE +  "Info:");
        player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Version: 1.0.1");
        player.sendMessage(ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Umbreon & MyNameIsJan");
        return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + " GitHub: https://github.com/UmbreonMajora/XcraftExperience";
    }
}
