package de.xcraft.jan.xcraftexperience.handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handler class for the default config of this plugin
 */
public class ConfigHandler {

    //The main class of this plugin
    JavaPlugin plugin;
    //The FileConfiguration for the default config
    FileConfiguration config;

    /**
     * Default constructor
     * @param plugin the main class of this plugin
     */
    public ConfigHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    /**
     * @return the default config of this plugin
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Sets the cost of the EXP-Bottle in the config file
     * @param costs the amount of how much a EXP-Bottle should cost
     */
    public void setCosts(int costs){
        config.addDefault("costs", costs);
        plugin.saveConfig();
    }

    /**
     * Sets the Exchange amount of an EXP-Bottle in the config file
     * @param exchangeAmount the amount of how much EXP will be decrease of the player from getting one EXP-Bottle
     */
    public void setExchangeAmount(int exchangeAmount){
        config.addDefault("experience", exchangeAmount);
        plugin.saveConfig();
    }

    /**
     * @return the cost as an int
     */
    public int getCostsInt(){
        return config.getInt("costs");
    }

    /**
     * @return the cost as a String
     */
    public String getCostsString(){
        return Integer.toString(getCostsInt());
    }

    /**
     * @return the Exchange amount as an int
     */
    public int getExperienceInt(){
        return config.getInt("experience");
    }

    /**
     * @return the Exchange amount as a String
     */
    public String getExperienceString(){
        return Integer.toString(getExperienceInt());
    }
}
