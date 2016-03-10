/*
 * Объект доступа к данным таблицы Users
 */

package ru.leonid.databaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Лёня
 */
public class UsersDAO {
    private Connection con;

    public UsersDAO(Connection con) {
        this.con = con;
    }

    public UsersDataSet get(int id) throws SQLException{
        TExecutor exec = new TExecutor();
        return exec.execQuery(con, "SELECT * FROM Users WHERE id = " + id, new TResultHandler<UsersDataSet>() {
            @Override
            public UsersDataSet handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                    UsersDataSet dataSet = new UsersDataSet(resultSet.getInt("id"), resultSet.getString("name"));
                    return dataSet;
                } else
                    return null;
            }
        });
    }
    
    public UsersDataSet getByName(String name) throws SQLException{
        TExecutor exec = new TExecutor();
        return exec.execQuery(con, "SELECT * FROM Users WHERE name = " + name, new TResultHandler<UsersDataSet>() {
            @Override
            public UsersDataSet handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()){
                    UsersDataSet dataSet = new UsersDataSet(resultSet.getInt("id"), resultSet.getString("name"));
                    return dataSet;
                } else 
                    return null;
            }
        });
    }    
    
    public void add(UsersDataSet dataSet) throws SQLException{
        TExecutor ex = new TExecutor();
        long id = dataSet.getId();
        String name = dataSet.getName();
        String updates = String.format("INSERT INTO Users (name) VALUES (%s)",  name);
        ex.execUpdate(con, updates);
    }
    
    public void delete(long id) throws SQLException{
        TExecutor ex = new TExecutor();
        String updates = "DELETE FROM Users WHERE id=" + id;
        ex.execUpdate(con, updates);
    }
    
}
