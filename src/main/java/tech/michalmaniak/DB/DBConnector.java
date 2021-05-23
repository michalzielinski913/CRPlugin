package tech.michalmaniak.DB;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBConnector {
    private ConnectionSource conn;
    public DBConnector() throws SQLException {
        // this uses h2 but you can change it to match your database
        String databaseUrl = "jdbc:sqlite:plugins/CRP/CRPluginDB.db";
// create a connection source to our database
        conn = new JdbcConnectionSource(databaseUrl);


    }

}
