package tech.michalmaniak.Utility;

import org.bukkit.Bukkit;

import java.util.UUID;

public class usernameToUUID {
    public static UUID get(String username){
        UUID uuid;
        try{
            uuid= Bukkit.getPlayer(username).getUniqueId();
        }catch (NullPointerException e){
            uuid=Bukkit.getOfflinePlayer(username).getUniqueId();
        }
        return uuid;
    }
}
