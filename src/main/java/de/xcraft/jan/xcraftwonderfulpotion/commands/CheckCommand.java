package de.xcraft.jan.xcraftwonderfulpotion.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class CheckCommand {

    public File config = new File("config");

    public static String checkPlayer(Player player, JavaPlugin plugin) {
        if(player.getTotalExperience() >= plugin.getConfig().getInt("experience")){
            return plugin.getConfig().getString("PLUGIN_PREFIX")+ChatColor.DARK_AQUA + "Du hast " + ChatColor.GOLD + Integer.toString(player.getTotalExperience()) + " Erfahrungspunkte. " + ChatColor.DARK_AQUA +
                    "Du kannst "+ ChatColor.GOLD + Integer.toString(player.getTotalExperience() / 7) + " Erfahrungsfläschchen" + ChatColor.DARK_AQUA + " herstellen. " +
                    "Das würde dich "+ ChatColor.GOLD + Integer.toString(player.getTotalExperience() / 7 * plugin.getConfig().getInt("costs"))+ " " + plugin.getConfig().getString("MONEY_NAME") + ChatColor.DARK_AQUA +" kosten.";
        } else {
            return ChatColor.DARK_AQUA + "Du hast " + Integer.toString(player.getTotalExperience()) + " Erfahrungspunkte. " +
                    "Du kannst keine Erfahrungsfläschen herstellen.";
        }
    }

    public static String checkAdmin(Player player, String otherPlayer) {
        if (Bukkit.getServer().getPlayer(otherPlayer) != null) {
            return ChatColor.DARK_AQUA + " " + otherPlayer + " hat" + Integer.toString(Bukkit.getServer().getPlayer(otherPlayer).getTotalExperience()) + " Erfahrungspunkte.";
        } else {
            return ChatColor.RED + " " +  otherPlayer + " wurde nicht gefunden!";
        }
    }
}
