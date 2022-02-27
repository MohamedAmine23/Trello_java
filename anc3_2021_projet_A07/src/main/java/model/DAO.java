package model;

import java.sql.*;

public abstract class  DAO<E> {
    protected static final String url = "jdbc:sqlite:dbtrello.db";
    protected Connection connect;
    protected PreparedStatement preparedStatement;
    
    abstract boolean create(E elem);
    abstract boolean delete(E elem);
    abstract boolean update(E elem);
    abstract void deleteAll(E elem);
    public abstract E find(int i);
}
