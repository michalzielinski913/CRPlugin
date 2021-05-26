package tech.michalmaniak;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tech.michalmaniak.Commands.crp;
import tech.michalmaniak.Commands.moderation.skillManipulator;
import tech.michalmaniak.Commands.roll;
import tech.michalmaniak.Commands.skills;
import tech.michalmaniak.DB.DBConnector;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Listeners.onPlayerJoin;
import tech.michalmaniak.Listeners.onPlayerLeave;
import tech.michalmaniak.RProfile.RProfile;
import tech.michalmaniak.Stats.Stat;


import java.sql.SQLException;
import java.util.HashMap;

public class CRPlugin extends JavaPlugin {
    public static CRPlugin plugin;
    public static HashMap<Player, RProfile> playerStatistics;
    public static Database db;
    public static FileConfiguration config;
    public static DBConnector d;


    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        this.playerStatistics=new HashMap<>();
        this.db= tech.michalmaniak.DB.Database.getDB();

        try {
            this.d=new DBConnector();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.saveDefaultConfig();
        config=this.getConfig();

        this.getCommand("crp").setExecutor(new crp());
        this.getCommand("skills").setExecutor(new skills());

        this.getCommand("croll").setExecutor(new roll(Stat.SKILL.COMBAT));
        this.getCommand("droll").setExecutor(new roll(Stat.SKILL.DODGE));
        this.getCommand("sroll").setExecutor(new roll(Stat.SKILL.SHOOTING));
        this.getCommand("mroll").setExecutor(new roll(Stat.SKILL.MAGIC));

        this.getCommand("skill").setExecutor(new skillManipulator());
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new onPlayerLeave(), this);


    }

    @Override
    public void onDisable(){

    }

}
