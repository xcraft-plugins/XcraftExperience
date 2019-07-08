package de.xcraft.jan.xcraftexperience.handler;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handler class for the messages of this plugin.
 */
public class MessageHandler {

    FileConfiguration configuration;

    public MessageHandler(JavaPlugin plugin){
        createCustomConfig(plugin);
    }

    /**
     * @param plugin the main class of this plugin
     * @return the messages.yml file with messages of this plugin
     */
    private File createCustomConfig(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @return the yml file
     */
    public FileConfiguration getConfiguration(){
        return configuration;
    }
}
