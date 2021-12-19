package database;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLConnection {
    private static String ip =  "192.168.1.5";
    private static String port = "1433";
    private static String database = "LostAndFound";
    private static String username = "maria";
    private static String password = "123456";

    @SuppressLint("NewApi")
    public Connection connect() {
        Connection conn = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

            ConnURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" +
                    "databasename=" + database + ";user=" + username + ";password=" + password + ";";

            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            System.out.println(se);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}