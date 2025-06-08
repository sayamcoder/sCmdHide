package developer.sayamdev.sCmdHide;

import org.bukkit.plugin.java.JavaPlugin;

public final class SCmdHide extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save the default config.yml if it doesn't exist
        saveDefaultConfig();

        // Register the command listener
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        // Register the plugin commands
        getCommand("scmdhide").setExecutor(new PluginCommands(this));

        getLogger().info("sCmdHide has been enabled successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().info("sCmdHide has been disabled.");
    }
}