package de.xcraft.jan.xcraftexperience.handler;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

/**
 * Handler class for the messages of this plugin.
 */
public class MessageHandler {

    HashMap<String, String> messages = new HashMap<>();

    public MessageHandler(JavaPlugin plugin){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(createCustomConfig(plugin))));
            String message;
            while ((message = reader.readLine()) != null) {
                String[] args = message.split("[:]", 2);
                if (args.length != 2) continue;
                messages.put(args[0], args[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        FileConfiguration configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @return the hashmap with all messages from the messages.yml file
     */
    public HashMap<String, String> getMessages() {
        return messages;
    }
}
