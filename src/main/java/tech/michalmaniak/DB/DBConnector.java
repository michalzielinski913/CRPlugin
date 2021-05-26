package tech.michalmaniak.DB;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import tech.michalmaniak.DB.ORM.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class DBConnector {
    private ConnectionSource conn;
    Dao<Player, String> playerDao;

    private void checkPlayerTable(){

    }

    public DBConnector() throws SQLException {
        // this uses h2 but you can change it to match your database
        String databaseUrl = "jdbc:sqlite:plugins/CRP/CRPluginDB.db";
// create a connection source to our database
        conn = new JdbcConnectionSource(databaseUrl);
        playerDao= DaoManager.createDao(conn, Player.class);

    }

    public boolean checkIfPlayerExist(UUID uuid) {
        QueryBuilder<Player, String> queryBuilder=playerDao.queryBuilder();
        try{
            queryBuilder.where().eq(Player.UUID_FIELD_NAME, uuid.toString());
            PreparedQuery<Player>preparedQuery=queryBuilder.prepare();
            List<Player>playerList=playerDao.query(preparedQuery);
            if(playerList.size()==1){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }

    }

    public void insertPlayer(UUID uuid) {
        Player pl=new Player(uuid.toString());
        try{
            playerDao.create(pl);
        }catch (SQLException e){

        }

    }

}
