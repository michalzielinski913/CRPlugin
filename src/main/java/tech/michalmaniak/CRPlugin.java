package tech.michalmaniak;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.RProfile.RProfile;


import java.util.HashMap;

public class CRPlugin extends JavaPlugin {
    public static CRPlugin plugin;
    public static HashMap<Player, RProfile> playerStatistics;
    public static Database db;

    @Override
    public void onEnable(){
        this.playerStatistics=new HashMap<>();
    }

    @Override
    public void onDisable(){

    }

}
