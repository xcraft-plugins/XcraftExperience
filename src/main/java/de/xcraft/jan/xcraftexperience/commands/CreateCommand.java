package de.xcraft.jan.xcraftexperience.commands;

import de.xcraft.jan.xcraftexperience.util.Registry;
import de.xcraft.jan.xcraftexperience.util.VaultEconomy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for create command.
 */
public class CreateCommand extends VaultEconomy {

    public File config = new File("config");
    Player player;
    JavaPlugin plugin;
    ItemStack itemToAdd;

    public CreateCommand(Player player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;
        itemToAdd = new ItemStack(Material.EXPERIENCE_BOTTLE);
    }

    /**
     * If successful the player will be given Exp-Bottles. The Exp and money of the player will be decreased after that.
     *
     * @param amount the amount of bottle to be created
     * @return string with a specific message depending if successful or not
     */
    public String createPlayer(int amount) {
        if (player.getTotalExperience() / 7 >= amount) { //Überprüft ob der Spieler genug Erfahrungspunkte besitzt.

            itemToAdd = new ItemStack(Material.EXPERIENCE_BOTTLE, amount); //Die XP-Flasche, welche hinzugefügt werden soll.
            itemToAdd.getItemMeta().setLore(new ArrayList<String>(Arrays.asList("OH wie praktisch!"))); //Setzt die Lore der XP-Flasche.
            itemToAdd.getItemMeta().setDisplayName("Ardanische XP-Flasche");

            if (validateSpace(amount)) {

                if (Registry.ECONOMY.has(player, amount * plugin.getConfig().getInt("costs"))) { //Überprüft ob der Spieler genug Geld besitzt.

                    givePlayer(amount);
                    player.sendMessage(ChatColor.GRAY + "Dir wurden " + amount * plugin.getConfig().getInt("costs") + " " + plugin.getConfig().getString("MONEY_NAME") + " abgezogen. "); //Sagt dem Spieler das ihm Geld genommen wurde.
                    return ChatColor.GRAY + plugin.getConfig().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Du hast " + Integer.toString(amount) + " Erfahrungsfläschen erhalten."; //Sage dem Spieler das er Exp-Flschen erhalten hat.

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

    /**
     * Validate if the player has enough space in his inventory
     *
     * @param amount the amount of bottle to be created
     * @return true if the player has enough space in his inventory
     */
    private boolean validateSpace(int amount) {
        int freeSpace = 0;
        int usedStacks = 0;
        for (ItemStack s : player.getInventory().getStorageContents()) {
            if (s != null) {
                if (s.getType() == Material.EXPERIENCE_BOTTLE) {
                    freeSpace += (s.getMaxStackSize() - s.getAmount());
                }
                usedStacks++;
            }
        }
        freeSpace += (36 - usedStacks) * 64;
        if (freeSpace >= amount) {
            return true;
        }
        return false;
    }

    /**
     * Decrease XP and Money and gives player XP-Bottles.
     */
    private void givePlayer(int amount) {
        player.giveExp(-(amount * 7)); //Nimmt dem Spieler Exp.
        player.getInventory().addItem(itemToAdd); //Gibt dem Spieler die EXP-Flaschen
        Registry.ECONOMY.withdrawPlayer(player, amount * plugin.getConfig().getInt("costs")); //Nimmt dem Spieler Geld.
    }
}