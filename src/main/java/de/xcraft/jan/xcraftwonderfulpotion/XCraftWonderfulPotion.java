package de.xcraft.jan.xcraftwonderfulpotion;

import de.xcraft.jan.xcraftwonderfulpotion.commands.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static de.xcraft.jan.xcraftwonderfulpotion.util.Registry.initVault;
import static de.xcraft.jan.xcraftwonderfulpotion.util.Registry.*;

public class XCraftWonderfulPotion extends JavaPlugin {

    CommandHandler commandHandler = new CommandHandler(this);
    PluginManager pm;

    @Override
    public void onEnable() {
        LOGGER = getLogger();
        initVault(getServer().getPluginManager(), getServer().getServicesManager());

        saveDefaultConfig();

        pm = Bukkit.getPluginManager();

        getCommand("xp").setExecutor(commandHandler);
    }
}