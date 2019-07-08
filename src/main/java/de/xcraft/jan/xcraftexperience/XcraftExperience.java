package de.xcraft.jan.xcraftexperience;

import de.xcraft.jan.xcraftexperience.handler.CommandHandler;
import de.xcraft.jan.xcraftexperience.handler.ConfigHandler;
import de.xcraft.jan.xcraftexperience.handler.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.jan.xcraftexperience.util.Registry.initVault;
import static de.xcraft.jan.xcraftexperience.util.Registry.*;

public class XcraftExperience extends JavaPlugin {

    CommandHandler commandHandler = new CommandHandler(this);
    public static MessageHandler MESSAGEHANDLER;
    public static ConfigHandler CONFIGHANDLER;
    PluginManager pm;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        initVault(getServer().getPluginManager(), getServer().getServicesManager());
        MESSAGEHANDLER = new MessageHandler(this);
        CONFIGHANDLER = new ConfigHandler(this);

        saveDefaultConfig();

        pm = Bukkit.getPluginManager();

        getCommand("bottle").setExecutor(commandHandler);
    }
}