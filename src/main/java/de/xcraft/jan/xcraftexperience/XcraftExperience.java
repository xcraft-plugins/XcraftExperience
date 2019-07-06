package de.xcraft.jan.xcraftexperience;

import de.xcraft.jan.xcraftexperience.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.jan.xcraftexperience.util.Registry.initVault;
import static de.xcraft.jan.xcraftexperience.util.Registry.*;

public class XcraftExperience extends JavaPlugin {

    CommandHandler commandHandler = new CommandHandler(this);
    PluginManager pm;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        initVault(getServer().getPluginManager(), getServer().getServicesManager());

        saveDefaultConfig();

        pm = Bukkit.getPluginManager();

        getCommand("bottle").setExecutor(commandHandler);
    }
}