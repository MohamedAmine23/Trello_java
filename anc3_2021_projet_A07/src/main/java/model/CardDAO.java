package model;

import java.sql.*;

public final class CardDAO extends DAO<Card>{

    @Override
    boolean create(Card card) {
        boolean res=false;
        try{
            connect= DriverManager.getConnection(url);
            String sql = "INSERT INTO card(idCard,name,position,idColumn) VALUES(?,?,?,?);";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1,card.getIdCard());
            preparedStatement.setString(2, card.getName() );
            preparedStatement.setInt(3,-10);
            preparedStatement.setInt(4,card.getMyColumn().getIdColumn());
            preparedStatement.execute();
            res= true;
        }
        catch(SQLException e){
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
    boolean delete(Card card) {
        boolean res=false;
        try{
            connect= DriverManager.getConnection(url);
            String sql = "DELETE FROM Card WHERE idCard = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, card.getIdCard());
            preparedStatement.execute();
            res= true;
        }
        catch(SQLException e){
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
    boolean update(Card card) {
        boolean res=false;
        try{
            connect= DriverManager.getConnection(url);
            String sql = "UPDATE Card SET name  = ?,position = ?,idColumn=? WHERE idCard = ?;";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, card.getName());
            preparedStatement.setInt(2,card.getMyColumn().getIndex(card)+1);
            preparedStatement.setInt(3,card.getMyColumn().getIdColumn());
            preparedStatement.setInt(4,card.getIdCard() );
            preparedStatement.execute();
            res= true;
        }
        catch(SQLException e){
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
    void deleteAll(Card card) { }

    @Override
    public Card find(int i) {
        return null;
    }


}
