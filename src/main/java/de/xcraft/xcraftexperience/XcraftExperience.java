package de.xcraft.xcraftexperience;

import de.xcraft.xcraftexperience.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.xcraftexperience.util.Registry.initVault;
import static de.xcraft.xcraftexperience.util.Registry.*;

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