package tech.michalmaniak.Utility;

import org.bukkit.ChatColor;

public class ChatParser {

    public static String colorChat(String value){
        return ChatColor.translateAlternateColorCodes('&', value);
    }
    public static String prefixColorChat(String value){
        return ChatColor.translateAlternateColorCodes('&', "&e[CRP] &f"+value);
    }
}
