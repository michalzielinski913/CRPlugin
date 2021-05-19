package tech.michalmaniak.DB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tech.michalmaniak.Stats.Stat;

import java.sql.*;

public class Database {
    private static Database instance=new Database();
    private static Connection connection;

    private Database() {
        try{
            connection=DriverManager.getConnection("jdbc:sqlite:plugins/CRP/CRPluginDB.db");
            createTablePlayers();
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());

        }


    }

    private static void createTablePlayers() {
        String query="CREATE TABLE IF NOT EXISTS \"Players\"  (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"uuid\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"Combat\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Dodge\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Magic\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Shooting\"\tINTEGER DEFAULT 0,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");";
        try(Statement stm=connection.createStatement()){
            stm.executeUpdate(query);
        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }

    }

    public static boolean checkIfUserExist(Player pl){
        String query="SELECT COUNT(*) AS TOTAL FROM Players WHERE uuid=?";
        //TO FIX!! NOT SUPPORTED JDBC FOR SQLITE PREPARED STATEMENTS
        try(PreparedStatement stm=connection.prepareStatement(query)){
            stm.setString(1, pl.getUniqueId().toString());
            ResultSet res=stm.executeQuery();
            if(res.getInt("TOTAl")==0){
                return false;
            }else{
                return true;
            }

        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }

        return false;
    }


    public static boolean insertUser(Player pl){
        String query="INSERT INTO \"main\".\"Players\"(\"id\",\"uuid\") VALUES (NULL, ?);\n";

        try(PreparedStatement stm=connection.prepareStatement(query)){
            stm.setString(1, pl.getUniqueId().toString());
            stm.executeUpdate();
            return true;
        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
            return false;
        }

    }

    public static int getStat(Player pl, Stat.SKILL skill){
        String s=new String(skill.toString());

        String query="SELECT "+s+" FROM Players WHERE uuid=?";

        try(PreparedStatement stm=connection.prepareStatement(query)){
            stm.setString(1, pl.getUniqueId().toString());
            ResultSet res=stm.executeQuery();
            return res.getInt(s);

        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }

        return 0;
    }

    public static Database getDB() {
        return instance;
    }
}
