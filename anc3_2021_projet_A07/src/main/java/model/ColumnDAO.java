package model;

import java.sql.*;

public final class ColumnDAO extends DAO<Column> {


    @Override
   boolean create(Column col) {
        boolean res=false;

        try{
            connect= DriverManager.getConnection(url);
            String sql = "INSERT INTO column(idColumn, name,position,idBoard) VALUES(?,?,?,?);";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,col.getIdColumn());
            preparedStatement.setString(2, col.getName());
            preparedStatement.setInt(3,-5);
            preparedStatement.setInt(4,col.getMyBoard().getIdBoard());
            preparedStatement.execute();
            res= true;
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
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
    boolean delete(Column col) {
        boolean res=false;

        try
        {
            connect= DriverManager.getConnection(url);
            if(col.getCards().size()>0){
               deleteAll(col);
            }
            String sql = "DELETE FROM Column WHERE idColumn = ?;";
            connect= DriverManager.getConnection(url);
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,col.getIdColumn() );
            preparedStatement.execute();
            res= true;
        }catch(SQLException e){
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
    boolean update(Column col) {
        boolean res=false;
        try{
        connect= DriverManager.getConnection(url);
        String sql = "UPDATE Column SET name  = ?,position=? WHERE idColumn = ?;";
        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, col.getName() );
        preparedStatement.setInt(2,col.getMyBoard().getIndex(col)+1 );
        preparedStatement.setInt(3, col.getIdColumn() );
        preparedStatement.execute();
        res= true;
        }catch(SQLException e){
            e.printStackTrace();
          } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ignore) {
                }
            }
            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException ignore) { }
            }
        }
        return res;
    }


    @Override
    void deleteAll(Column col) {
        try{
            connect= DriverManager.getConnection(url);
            String sql = "DELETE FROM Card WHERE idColumn = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, col.getIdColumn());
            preparedStatement.execute();
        } catch(SQLException e){
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
    }

    @Override
    public Column find(int i) { return null; }


}
