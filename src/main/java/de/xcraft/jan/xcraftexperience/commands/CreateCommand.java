package de.xcraft.jan.xcraftexperience.commands;

import de.xcraft.jan.xcraftexperience.util.Registry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static de.xcraft.jan.xcraftexperience.XcraftExperience.CONFIGHANDLER;
import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

/**
 * Class for create command.
 */
public class CreateCommand {
    Player player;
    ItemStack itemToAdd;
    int amount;

    public CreateCommand(Player player, String amount) {
        this.player = player;
        this.amount = Integer.parseInt(amount);
        itemToAdd = new ItemStack(Material.EXPERIENCE_BOTTLE, this.amount);
    }

    /**
     * If successful the player will be given Exp-Bottles. The Exp and money of the player will be decreased after that.
     *
     * @return string with a specific message depending if successful or not
     */
    public String createPlayer() {
        if (player.getTotalExperience() / CONFIGHANDLER.getExperienceCost() >= amount) { //Überprüft ob der Spieler genug Erfahrungspunkte besitzt.

            if (validateSpace()) {

                if (Registry.ECONOMY.has(player, amount * CONFIGHANDLER.getEuronenCost())) { //Überprüft ob der Spieler genug Geld besitzt.

                    givePlayer();
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + "Dir wurden " + amount * CONFIGHANDLER.getEuronenCost() + " " + MESSAGEHANDLER.getConfiguration().getString("MONEY_NAME") + " abgezogen. "); //Sagt dem Spieler das ihm Geld genommen wurde.
                    return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + ChatColor.DARK_AQUA + " Du hast " + ChatColor.YELLOW + Integer.toString(amount) + ChatColor.DARK_AQUA + " Erfahrungsfläschchen erhalten."; //Sage dem Spieler das er Exp-Flschen erhalten hat.

                } else {
                    return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_MONEY");//Sagt dem Spieler das er nicht genug Geld besitzt.
                }
            } else {
                return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_SPACE"); //Sagt dem Spieler das er nicht genug Platz im Inventar hat.
            }
        } else {
            return MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_XP");//Sagt dem Spieler das er nicht genug XP-Punkte besietzt.
        }
    }

    /**
     * @return the XP bottle that will be created with custom name and lore
     */
    private ItemStack itemToAdd() {
        ItemMeta m = itemToAdd.getItemMeta();
        m.setLore(CONFIGHANDLER.getLore()); //Setzt die Lore der XP-Flasche.
        m.setDisplayName(CONFIGHANDLER.getDisplayedName()); //Setzt den Namen der XP-Flaschen.
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
                if (s.getType() == Material.EXPERIENCE_BOTTLE && s.getItemMeta().getDisplayName().equals(CONFIGHANDLER.getDisplayedName()) && s.getItemMeta().getLore().equals(Arrays.asList(CONFIGHANDLER.getLore()))) {
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
        Registry.ECONOMY.withdrawPlayer(player, amount * CONFIGHANDLER.getEuronenCost()); //Nimmt dem Spieler Geld.
    }
}