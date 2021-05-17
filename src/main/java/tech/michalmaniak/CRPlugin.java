package tech.michalmaniak;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.michalmaniak.Commands.crp;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.RProfile.RProfile;


import java.util.HashMap;

public class CRPlugin extends JavaPlugin {
    public static CRPlugin plugin;
    public static HashMap<Player, RProfile> playerStatistics;
    public static Database db;


    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        this.playerStatistics=new HashMap<>();
        this.db= tech.michalmaniak.DB.Database.getDB();


        this.getCommand("crp").setExecutor(new crp());
    }

    @Override
    public void onDisable(){

    }

}
