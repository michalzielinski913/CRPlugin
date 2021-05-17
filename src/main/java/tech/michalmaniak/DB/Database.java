package tech.michalmaniak.DB;
import org.bukkit.Bukkit;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Database {
    private static Database instance=new Database();
    private static Connection connection;

    private Database() {
        try{
            connection=DriverManager.getConnection("jdbc:sqlite:plugins/CRP/CRPluginDB.db");
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());

        }

    }

    public static Database getDB() {
        return instance;
    }
}
