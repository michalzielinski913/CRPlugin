package tech.michalmaniak.DB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
//INSERT INTO "main"."Perks"("uuid","skill","value") VALUES (0,2,1);
    public static void insertPerk(UUID sender, Stat.SKILL sk, int value, UUID givenBY, int validTo, String lore){
        String query="INSERT INTO \"main\".\"Perks\"(\"id\",\"userUUID\",\"skill\",\"value\",\"givenBy\",\"validUntil\",\"validUntil\") VALUES (NULL,?,?,?,?,?,?);\n";

        try(PreparedStatement stm=connection.prepareStatement(query)){
            stm.setString(1, sender.toString());
            stm.setString(2, sk.toString());
            stm.setInt(3, value);
            if(givenBY==null){
                stm.setString(4, "CONSOLE");
            }else{
                stm.setString(4, givenBY.toString());
            }
            stm.setInt(5, validTo);
            stm.setString(6, lore);
            stm.executeUpdate();
        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }

    }

    public static ResultSet getPerk(UUID uuid, Stat.SKILL sk) throws SQLException {
        String query="SELECT * FROM Perks WHERE userUUID=? AND skill=?";

        PreparedStatement stm=connection.prepareStatement(query);
        stm.setString(1, uuid.toString());
        stm.setString(2, sk.toString());
        ResultSet res=stm.executeQuery();
        return res;
    }


    private static void createTablePerks(){
        StringBuilder query= new StringBuilder("CREATE TABLE \"Perks\" (\n" +
                "\t\"id\"\tINTEGER UNIQUE,\n" +
                "\t\"userUUID\"\tTEXT,\n" +
                "\t\"skill\"\tTEXT,\n" +
                "\t\"value\"\tINTEGER,\n" +
                "\t\"givenBy\"\tTEXT,\n" +
                "\t\"validUntil\"\tINTEGER,\n" +
                "\t\"lore\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");
        try(Statement stm=connection.createStatement()){
            stm.executeUpdate(query.toString());
        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }
    }
    private static void createTablePlayers() {
        StringBuilder query= new StringBuilder("CREATE TABLE IF NOT EXISTS \"Players\"  (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"uuid\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"Combat\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Dodge\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Magic\"\tINTEGER DEFAULT 0,\n" +
                "\t\"Shooting\"\tINTEGER DEFAULT 0,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");
        try(Statement stm=connection.createStatement()){
            stm.executeUpdate(query.toString());
        }catch(SQLException e){
            Bukkit.getLogger().info(e.toString());
        }

    }

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
