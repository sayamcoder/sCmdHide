package developer.sayamdev.sCmdHide;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    // Pattern to match hex color codes like <#FFFFFF>
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([a-fA-F0-9]{6})>");

    public static String translate(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        // Translate modern hex codes
        Matcher matcher = HEX_PATTERN.matcher(text);
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            text = text.replace(matcher.group(), ChatColor.of("#" + hexCode).toString());
        }

        // Translate classic '&' color codes
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
