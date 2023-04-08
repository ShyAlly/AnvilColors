package com.jeff_media.anvilcolors;

import net.md_5.bungee.api.ChatColor;

public class VersionUtils {

    private static Boolean hasHexColorSupport = null;

    public static boolean hasHexColorSupport() {
        if(hasHexColorSupport != null) {
            return hasHexColorSupport;
        }
        try {
            return hasHexColorSupport = ChatColor.class.getDeclaredMethod("of", String.class) != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }



}
