package tech.michalmaniak;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.michalmaniak.Commands.crp;
import tech.michalmaniak.Commands.skills;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Listeners.onPlayerJoin;
import tech.michalmaniak.Listeners.onPlayerLeave;
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
        this.getCommand("skills").setExecutor(new skills());
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new onPlayerLeave(), this);


    }

    @Override
    public void onDisable(){

    }

}
