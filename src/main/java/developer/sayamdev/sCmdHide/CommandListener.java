package developer.sayamdev.sCmdHide;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    private final SCmdHide plugin;

    public CommandListener(SCmdHide plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        // Allow OPs and players with the bypass permission
        if (player.isOp() || player.hasPermission("scmdhide.bypass")) {
            return;
        }

        // Get the command without the '/' and arguments
        String command = event.getMessage().substring(1).toLowerCase();
        String baseCommand = command.split(" ")[0];

        // Check if the command is in the blocked list
        List<String> blockedCommands = plugin.getConfig().getStringList("settings.blocked-commands");
        if (blockedCommands.stream().anyMatch(blockedCmd -> baseCommand.equalsIgnoreCase(blockedCmd))) {
            // Cancel the command
            event.setCancelled(true);

            // Send the beautiful message
            sendBlockedMessages(player);

            // Play the sound effect
            playSound(player);

            // Notify admins
            notifyAdmins(player, command);
        }
    }

    private void sendBlockedMessages(Player player) {
        // Send chat messages
        for (String line : plugin.getConfig().getStringList("messages.blocked-command-message")) {
            player.sendMessage(ColorUtils.translate(line.replace("%player%", player.getName())));
        }

        // Send title message
        if (plugin.getConfig().getBoolean("messages.send-title-message")) {
            String title = ColorUtils.translate(plugin.getConfig().getString("messages.title"));
            String subtitle = ColorUtils.translate(plugin.getConfig().getString("messages.subtitle"));
            player.sendTitle(title, subtitle, 10, 70, 20); // fade-in, stay, fade-out ticks
        }
    }

    private void playSound(Player player) {
        if (plugin.getConfig().getBoolean("sounds.play-sound")) {
            try {
                String soundName = plugin.getConfig().getString("sounds.sound-effect");
                Sound sound = Sound.valueOf(soundName.toUpperCase());
                float volume = (float) plugin.getConfig().getDouble("sounds.volume");
                float pitch = (float) plugin.getConfig().getDouble("sounds.pitch");
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid sound name in config.yml: " + plugin.getConfig().getString("sounds.sound-effect"));
            }
        }
    }

    private void notifyAdmins(Player player, String command) {
        if (plugin.getConfig().getBoolean("settings.notify-admins")) {
            String notification = ColorUtils.translate(
                    plugin.getConfig().getString("messages.admin-notification")
                            .replace("%player%", player.getName())
                            .replace("%command%", command)
            );

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("scmdhide.notify")) {
                    onlinePlayer.sendMessage(notification);
                }
            }
            // Also log to console
            plugin.getLogger().info(notification);
        }
    }
}
