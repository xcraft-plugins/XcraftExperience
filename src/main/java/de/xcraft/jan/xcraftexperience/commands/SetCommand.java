package de.xcraft.jan.xcraftexperience.commands;

import org.bukkit.entity.Player;

import static de.xcraft.jan.xcraftexperience.XcraftExperience.CONFIGHANDLER;
import static de.xcraft.jan.xcraftexperience.XcraftExperience.MESSAGEHANDLER;

public class SetCommand {

    public static void setCommand(String variable, String value, Player player){
        if (variable.equalsIgnoreCase("euronencost")){
            if (isInt(value)) {
                if (isPositive(value)) {
                    CONFIGHANDLER.setEuronenCost(Integer.parseInt(value));
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("VALUE_CHANGE"));
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NEGATIVE_NUMBER"));
                }
            } else {
                player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_NUMBER"));
            }
        }else if (variable.equalsIgnoreCase("experiencecost")){
            if (isInt(value)) {
                if (isPositive(value)) {
                    CONFIGHANDLER.setExperienceCost(Integer.parseInt(value));
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("VALUE_CHANGE"));
                } else {
                    player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NEGATIVE_NUMBER"));
                }
            } else {
                player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("ERROR_NO_NUMBER"));
            }
        }else if (variable.equalsIgnoreCase("diplayedname")){
            CONFIGHANDLER.setDisplayedName(value);
            player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("VALUE_CHANGE"));
        }else if (variable.equalsIgnoreCase("lore")){
            CONFIGHANDLER.setLore(value);
            player.sendMessage(MESSAGEHANDLER.getConfiguration().getString("PLUGIN_PREFIX") + MESSAGEHANDLER.getConfiguration().getString("VALUE_CHANGE"));
        }else {

        }
    }

    /**
     * @param str the Integer to check
     * @return true if it is an int
     */
    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param str the Integer to check
     * @return true if it is positive
     */
    private static boolean isPositive(String str) {
        if (Integer.parseInt(str) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
