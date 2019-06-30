package de.xcraft.jan.xcraftwonderfulpotion.commands;

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
        if (player.getTotalExperience() / 7 >= amount) {
            if (player.getInventory().firstEmpty() != -1) {
                if(economy.has(player, amount * plugin.getConfig().getInt("costs"))){
                    player.giveExp(-(amount * 7));
                    player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, amount));
                    return ChatColor.DARK_AQUA + "Du hast " + Integer.toString(amount) + " Erfahrungsfläschen erhalten.";
                } else {
                    return ChatColor.DARK_AQUA + "Du hast nicht genug Geld.";
                }
            } else {
                return ChatColor.RED + "Dein Inventar ist voll!";
            }
        } else {
            return ChatColor.RED + "Du hast nicht genug Erfahrungspunkte um " + Integer.toString(amount) + " Erfahrungsfläschen herzustellen.";
        }
    }
}
//player.getTotalExperience() / plugin.getConfig().getInt("experience") * plugin.getConfig().getInt("costs")