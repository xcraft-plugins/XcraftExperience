package de.xcraft.jan.xcraftexperience.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static de.xcraft.jan.xcraftexperience.XcraftExperience.CONFIGHANDLER;
import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

/**
 * Class for check command.
 */
public class CheckCommand {

    Player player;
    JavaPlugin plugin;

    public CheckCommand(Player player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    /**
     * @return string with values of the amount of Exp and money and how many Exp-Bottle can be created
     */
    public String checkPlayer() {
        if (player.getTotalExperience() >= CONFIGHANDLER.getExperienceInt()) {//Überprüft ob der Spieler mehr XP hat als in der Config angeben wenn ja sagt er ihm we viele Flaschen er herstellen kann und wie viel es ihm Kostet.
            String sb = new String(MESSAGEHANDLER.getConfiguration().getString("PLAYER_CHECK_MESSAGE")).replace("[Exp]",Integer.toString(player.getTotalExperience())).replace("[BottleAmount]",Integer.toString(player.getTotalExperience() / CONFIGHANDLER.getExperienceInt())).replace("[cost]",Integer.toString(player.getTotalExperience() / CONFIGHANDLER.getExperienceInt() * CONFIGHANDLER.getCostsInt()));
            return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + sb;
        } else {
            return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_XP"); //Sagt dem Spieler das er zu wenig XP-Punkte hat um überhaupt eine Flasche herstellen zu können.
        }
    }

    /**
     * @param otherPlayer the player to check
     * @return the Exp value of the player
     */
    public String checkAdmin(String otherPlayer) {
        if (Bukkit.getServer().getPlayer(otherPlayer) != null) {
            String sb = MESSAGEHANDLER.getConfiguration().getString("ADMIN_CHECK_MESSAGE").replace("[playername]",otherPlayer).replace("[Exp]", Integer.toString(Bukkit.getServer().getPlayer(otherPlayer).getTotalExperience()));
            return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + sb;
        } else {
            return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_PLAYER");
        }
    }
}
