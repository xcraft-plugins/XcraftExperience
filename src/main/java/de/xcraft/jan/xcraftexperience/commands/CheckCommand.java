package de.xcraft.jan.xcraftexperience.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Class for check command.
 */
public class CheckCommand {

    public File config = new File("config");

    /**
     *
     * @param player the player who execute the command
     * @param plugin the plugin of this command
     * @return string with values of the amount of Exp and money and how many Exp-Bottle can be created
     */
    public static String checkPlayer(Player player, JavaPlugin plugin) {
        if (player.getTotalExperience() >= plugin.getConfig().getInt("experience")) {//Überprüft ob der Spieler mehr XP hat als in der Config angeben wenn ja sagt er ihm we viele Flaschen er herstellen kann und wie viel es ihm Kostet.
            return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Du hast " + ChatColor.GOLD + Integer.toString(player.getTotalExperience()) + " Erfahrungspunkte. " + ChatColor.DARK_AQUA +
                    "Du kannst " + ChatColor.GOLD + Integer.toString(player.getTotalExperience() / plugin.getConfig().getInt("experience")) + " Erfahrungsfläschchen" + ChatColor.DARK_AQUA + " herstellen " +
                    "und würde dich " + ChatColor.GOLD + Integer.toString(player.getTotalExperience() / plugin.getConfig().getInt("experience") * plugin.getConfig().getInt("costs")) + " " + plugin.getConfig().getString("MONEY_NAME") + ChatColor.DARK_AQUA + " kosten.";
        } else {
            return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Du hast " + Integer.toString(player.getTotalExperience()) + " Erfahrungspunkte. " +
                    "Du kannst keine Erfahrungsfläschen herstellen."; //Sagt dem Spieler das er zu wenig XP-Punkte hat um überhaupt eine Flasche herstellen zu können.
        }
    }

    /**
     *
     * @param player the player who execute the command
     * @param otherPlayer the player to check
     * @param plugin the plugin of this command
     * @return the Exp value of the player
     */
    public static String checkAdmin(Player player, String otherPlayer, JavaPlugin plugin) {
        if (Bukkit.getServer().getPlayer(otherPlayer) != null) {
            return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " " + otherPlayer + " hat " + Integer.toString(Bukkit.getServer().getPlayer(otherPlayer).getTotalExperience()) + " Erfahrungspunkte.";
        } else {
            return ChatColor.RED + " " + otherPlayer + " wurde nicht gefunden!";
        }
    }
}
