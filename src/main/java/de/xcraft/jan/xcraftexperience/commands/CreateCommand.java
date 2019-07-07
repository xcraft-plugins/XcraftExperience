package de.xcraft.jan.xcraftexperience.commands;

import de.xcraft.jan.xcraftexperience.util.Registry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for create command.
 */
public class CreateCommand {

    public File config = new File("config");
    Player player;
    JavaPlugin plugin;
    ItemStack itemToAdd;
    int amount;

    public CreateCommand(Player player, JavaPlugin plugin, String amount) {
        this.player = player;
        this.plugin = plugin;
        this.amount = Integer.parseInt(amount);
        itemToAdd = new ItemStack(Material.EXPERIENCE_BOTTLE, this.amount);
    }

    /**
     * If successful the player will be given Exp-Bottles. The Exp and money of the player will be decreased after that.
     *
     * @return string with a specific message depending if successful or not
     */
    public String createPlayer() {
        if (player.getTotalExperience() / 7 >= amount) { //Überprüft ob der Spieler genug Erfahrungspunkte besitzt.

            if (validateSpace()) {

                if (Registry.ECONOMY.has(player, amount * plugin.getConfig().getInt("costs"))) { //Überprüft ob der Spieler genug Geld besitzt.

                    givePlayer();
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

    private ItemStack itemToAdd() {
        ItemMeta m = itemToAdd.getItemMeta();
        m.setLore(new ArrayList<String>(Arrays.asList("OH wie praktisch!"))); //Setzt die Lore der XP-Flasche.
        m.setDisplayName("Ardanische XP-Flasche"); //Setzt den Namen der XP-Flaschen.
        itemToAdd.setItemMeta(m); //Setzt die ItemMeta der XP-Flaschen.
        return itemToAdd;
    }

    /**
     * Validate if the player has enough space in his inventory
     *
     * @return true if the player has enough space in his inventory
     */
    private boolean validateSpace() {
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
    private void givePlayer() {
        player.giveExp(-(amount * 7)); //Nimmt dem Spieler Exp.
        player.getInventory().addItem(itemToAdd()); //Gibt dem Spieler die EXP-Flaschen
        Registry.ECONOMY.withdrawPlayer(player, amount * plugin.getConfig().getInt("costs")); //Nimmt dem Spieler Geld.
    }
}