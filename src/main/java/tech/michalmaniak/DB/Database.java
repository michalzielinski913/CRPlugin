package tech.michalmaniak.DB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.Stats.Stat;

import java.sql.*;
import java.util.UUID;

public class Database {
    private static Database instance=new Database();
    private static Connection connection;

    private Database() {
        try{
            connection=DriverManager.getConnection("jdbc:sqlite:plugins/CRP/CRPluginDB.db");
            createTablePlayers();
            createTablePerks();
        }catch (SQLException e){
            Bukkit.getLogger().info(e.toString());

        }


    }

/*
CREATE TABLE "Perks" (
	"id"	INTEGER NOT NULL UNIQUE,
	"uuid"	INTEGER NOT NULL,
	"skill"	TEXT,
	"value"	INTEGER,
	"lore" TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
)
 */
    private static void createTablePerks(){
        String query="CREATE TABLE IF NOT EXISTS \"Perks\" (\n" +
                "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                "\t\"uuid\"\tINTEGER NOT NULL,\n" +
                "\t\"skill\"\tTEXT,\n" +
                "\t\"value\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");";
        try(Statement stm=connection.createStatement()){
            stm.executeUpdate(query);
        }catch(SQLException e){
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
//INSERT INTO "main"."Perks"("uuid","skill","value") VALUES (0,2,1);
    //SELECT COUNT(value) as modifier FROM Perks WHERE uuid='0' and skill='1'

    public static boolean checkIfUserExist(String uuid){
        String query="SELECT COUNT(*) AS TOTAL FROM Players WHERE uuid=?";
        try(PreparedStatement stm=connection.prepareStatement(query)){
            stm.setString(1, uuid);
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

    public static boolean checkIfUserExist(Player pl){
        return checkIfUserExist(pl.getUniqueId().toString());
    }

    public static void updateStat(String id, Stat.SKILL skill, int value){
        try{
            String query="UPDATE Players SET "+skill.toString()+"=? WHERE uuid=?";

            try(PreparedStatement stm=connection.prepareStatement(query)){
                stm.setInt(1, value);
                stm.setString(2, id);
                stm.executeUpdate();
            }catch(SQLException e){
                Bukkit.getLogger().info(e.toString());
            }
        }catch (IllegalArgumentException e){
            Bukkit.getLogger().info(e.toString());
        }



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


    public static ResultSet getStats(UUID uuid) throws SQLException {
        String query="SELECT * FROM Players WHERE uuid=?";

        PreparedStatement stm=connection.prepareStatement(query);
            stm.setString(1, uuid.toString());
            ResultSet res=stm.executeQuery();
            return res;
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
