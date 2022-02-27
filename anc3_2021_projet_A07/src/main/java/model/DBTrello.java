package model;

import java.sql.*;

public final class DBTrello {

    private static final String url = "jdbc:sqlite:dbtrello.db";
    private static Statement stmt;
    private static PreparedStatement preparedStatement;
    private static String sql;

    public DBTrello(){
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url);
            configDB(conn);
            createTables(conn);
            clearDB(conn);
            seedData(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private static void configDB(Connection conn) throws SQLException {
        stmt = conn.createStatement();
        // Activation of checks FK
        sql = "PRAGMA foreign_keys = ON;";
        stmt.execute(sql);
    }


    private static void createTables(Connection conn) throws SQLException {
        stmt = conn.createStatement();

        // SQL statement for board table
        sql = "CREATE TABLE IF NOT EXISTS board ("
                + "	idBoard integer PRIMARY KEY,"
                + "	name text NOT NULL);";
        stmt.execute(sql);

        // SQL statement for column table
        sql = "CREATE TABLE IF NOT EXISTS column ("
                + "	idColumn integer  PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "position int NOT NULL,"
                + "	idBoard integer NOT NULL,"

         + " CONSTRAINT fk_board FOREIGN KEY (idBoard) "
         + " REFERENCES board(idBoard));";
        stmt.execute(sql);

        // SQL statement for card table
        sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	idCard integer  PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "position int NOT NULL,"
                + "	idColumn integer NOT NULL,"

            + " CONSTRAINT fk_column FOREIGN KEY (idColumn) "
           + " REFERENCES column(idColumn));";
        stmt.execute(sql);
    }

    private static void clearDB(Connection conn) throws SQLException {
        stmt = conn.createStatement();

        sql = "DELETE FROM card;";
        stmt.execute(sql);

        sql = "DELETE FROM column;";
        stmt.execute(sql);

        sql = "DELETE FROM board;";
        stmt.execute(sql);
    }

    private static void seedBoard(Connection conn) throws SQLException {
        String sql = "INSERT INTO board(idBoard, name) VALUES(?,?);";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Board ");
        preparedStatement.execute();

    }

    private static void seedColumn(Connection conn) throws SQLException {
        String sql = "INSERT INTO column(idColumn, name,position,idBoard) VALUES(?,?,?,?);";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Column 1");
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,1);
        preparedStatement.execute();


        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "Column 2");
        preparedStatement.setInt(3,2);
        preparedStatement.setInt(4,1);
        preparedStatement.execute();


        preparedStatement.setInt(1, 3);
        preparedStatement.setString(2, "Column 3");
        preparedStatement.setInt(3,3);
        preparedStatement.setInt(4,1);
        preparedStatement.execute();

    }
    private static void seedCard(Connection conn) throws SQLException {
        String sql = "INSERT INTO card(idCard, name,position,idColumn) VALUES(?,?,?,?);";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Card 1");
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,1);
        preparedStatement.execute();

        preparedStatement.setInt(1,2);
        preparedStatement.setString(2, "Card 2");
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,2);
        preparedStatement.execute();

        preparedStatement.setInt(1,3);
        preparedStatement.setString(2, "Card 3");
        preparedStatement.setInt(3,1);
        preparedStatement.setInt(4,3);
        preparedStatement.execute();

        preparedStatement.setInt(1,4);
        preparedStatement.setString(2, "Card 4");
        preparedStatement.setInt(3,2);
        preparedStatement.setInt(4,3);
        preparedStatement.execute();


    }

    private static void seedData(Connection conn) throws SQLException {
        seedBoard(conn);
        seedColumn(conn);
        seedCard(conn);
    }


}





