package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionSqlite {
    private static String url = "jdbc:sqlite:dbtrello.db";
    private static Connection connect;

    public static Connection getConnect(){
            try{
                connect= DriverManager.getConnection(url);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        return connect;
    }

}
