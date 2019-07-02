package de.xcraft.jan.xcraftwonderfulpotion.commands;

import de.xcraft.jan.xcraftwonderfulpotion.util.Registry;
import de.xcraft.jan.xcraftwonderfulpotion.util.VaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public class CreateCommand extends VaultEconomy {

    public File config = new File("config");

    public static String createPlayer(Player player, int amount, JavaPlugin plugin) {
        if (player.getTotalExperience() / 7 >= amount) { //Überprüft ob der Spieler genug Erfahrungspunkte besitzt.
            if (player.getInventory().firstEmpty() != -1) { //Überprüft ob der Spieler genug Platz im Inventar hat.
                if(Registry.ECONOMY.has(player, amount * plugin.getConfig().getInt("costs"))){ //Überprüft ob der Spieler genug Geld besitzt.
                    player.giveExp(-(amount * 7)); //Nimmt dem Spieler Exp.
                    player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, amount)); //Gibt dem Spieler die EXP-Flaschen
                    Registry.ECONOMY.withdrawPlayer(player, amount * plugin.getConfig().getInt("costs")); //Nimmt dem Spieler Geld.
                    player.sendMessage(ChatColor.GRAY + "Dir wurden " + amount * plugin.getConfig().getInt("costs") + " " + plugin.getConfig().getString("MONEY_NAME") + " abgezogen. "); //Sagt dem Spieler das ihm Geld genommen wurde.
                    return ChatColor.GRAY+plugin.getConfig().getString("PLUGIN_PREFIX")+ChatColor.DARK_AQUA + " Du hast " + Integer.toString(amount) + " Erfahrungsfläschen erhalten."; //Sage dem Spieler das er Exp-Flschen erhalten hat.
                } else {
                    return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.RED + " Du hast nicht genug Geld.";//Sagt dem Spieler das er nicht genug Geld besitzt.
                }
            } else {
                return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.RED + " Dein Inventar ist voll!"; //Sagt dem Spieler das er nicht genug Platz im Inventar hat.
            }
        } else {
            return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.RED + " Du hast nicht genug Erfahrungspunkte um " + Integer.toString(amount) + " Erfahrungsfläschen herzustellen.";//Sagt dem Spieler das er nicht genug XP-Punkte besietzt.
        }
    }
}