package com.jeff_media.anvilcolors;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.permissions.Permissible;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {

    private final Plugin plugin;

    private static final Pattern HEX_PATTERN = Pattern.compile("#([0-9a-fA-F]{6})");

    public Formatter(Plugin plugin) {
        this.plugin = plugin;
    }

    public String colorize(@Nullable Permissible permissible, String input, ItalicsMode italicsMode) {

        if(VersionUtils.hasHexColorSupport() && hasPermission(permissible,"anvilcolors.hexcolors")) {
            input = replaceHexColors(input);
        }

        for(Color color : Color.list()) {
            if(hasPermission(permissible, color.getPermission())) {
                input = color.transform(input, italicsMode == ItalicsMode.FORCE);
            }
        }
        if(italicsMode == ItalicsMode.REMOVE) {
            input = ChatColor.RESET + input;
        }
        return input;
    }

    private boolean hasPermission(Permissible permissible, String permission) {
        return !plugin.getConfig().getBoolean("require-permissions") || permissible == null || permissible.hasPermission(permission);
    }

    public static String replaceHexColors(String input) {
        int lastIndex = 0;
        StringBuilder output = new StringBuilder();
        Matcher matcher = HEX_PATTERN.matcher(input);
        while (matcher.find()) {
            output.append(input, lastIndex, matcher.start())
                    .append(ChatColor.of("#" + matcher.group(1)));

            lastIndex = matcher.end();
        }
        if (lastIndex < input.length()) {
            output.append(input, lastIndex, input.length());
        }
        return output.toString();
    }

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
