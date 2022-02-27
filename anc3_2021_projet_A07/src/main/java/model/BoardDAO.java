package model;

import java.sql.*;

public final class BoardDAO extends DAO<Board> {

    private Board board;
    private static Board instance;

    private static Board getInstance() { return instance; }

    @Override
    boolean create(Board board) {
        boolean res = false;
        try {
            connect= DriverManager.getConnection(url);
            String sql = "INSERT INTO board(idBoard, name) VALUES(?,?);";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, board.getIdBoard());
            preparedStatement.setString(2, board.getName());
            preparedStatement.execute();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                } catch ( SQLException ignore ) { }
            }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
        return res;
    }

    @Override
    boolean delete(Board board) {
        boolean res = false;
        try {
            connect= DriverManager.getConnection(url);
            if(board.getColumns().size()>0){
                deleteAll(board);
            }
            connect= DriverManager.getConnection(url);
            String sql = "DELETE FROM board WHERE idBoard = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, board.getIdBoard());
            preparedStatement.execute();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                } catch ( SQLException ignore ) { }
            }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
        return res;
    }

    @Override
    boolean update(Board board) {
        boolean res = false;
        try {
            connect= DriverManager.getConnection(url);
            String sql = "UPDATE Board SET name  = ? WHERE idBoard = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, board.getName());
            preparedStatement.setInt(2,board.getIdBoard() );
            preparedStatement.execute();
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                } catch ( SQLException ignore ) { }
            }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
        return res;
    }

    @Override
    public Board find(int id) {
        Statement statement=null;
        ResultSet result=null;
        try{
            connect= DriverManager.getConnection(url);
            String sql = "SELECT * FROM Board WHERE idBoard="+id +";";
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            String name = result.getString("name");
            if(instance==null){
                board=new Board(name);
                instance=board;
            }
            else board=getInstance();

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            if(result!=null){
                try{
                    result.close();
                } catch ( SQLException ignore ) { }
            }
            if(statement!=null){
                try{
                    statement.close();
                } catch ( SQLException ignore ) { }
            }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
        return board;
    }



    @Override
    void deleteAll(Board board) {
        try{
            connect= DriverManager.getConnection(url);
            String sql = "DELETE FROM Column WHERE idBoard = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, board.getIdBoard());
            preparedStatement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        } finally {
            if ( preparedStatement != null )
                try {
                    preparedStatement.close();
                } catch ( SQLException ignore ) { }
            if ( connect != null )
                try {
                    connect.close();
                } catch ( SQLException ignore ) { }
        }
    }


}
