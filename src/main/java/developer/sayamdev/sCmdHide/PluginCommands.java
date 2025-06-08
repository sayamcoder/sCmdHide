package developer.sayamdev.sCmdHide;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PluginCommands implements CommandExecutor {

    private final SCmdHide plugin;

    public PluginCommands(SCmdHide plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("scmdhide.reload")) {
                sender.sendMessage(ColorUtils.translate("&cYou do not have permission to use this command."));
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(ColorUtils.translate("<#FFBF00>&l sCmdHide &8» &aConfiguration reloaded successfully!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission("scmdhide.help")) {
                sender.sendMessage(ColorUtils.translate("&cYou do not have permission to use this command."));
                return true;
            }
            sendHelpMessage(sender);
            return true;
        }

        sendHelpMessage(sender);
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ColorUtils.translate("&8&m----------------------------------"));
        sender.sendMessage(ColorUtils.translate("<#FFBF00>&l sCmdHide &7- Help Menu"));
        sender.sendMessage("");
        sender.sendMessage(ColorUtils.translate("&e/scmdhide reload &8- &7Reloads the config file."));
        sender.sendMessage(ColorUtils.translate("&e/scmdhide help &8- &7Shows this help message."));
        sender.sendMessage("");
        sender.sendMessage(ColorUtils.translate("&8&m----------------------------------"));
    }
}
