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

    public UsersDataSet get(long id) throws SQLException{
        TExecutor exec = new TExecutor();
        return exec.execQuery(con, "SELECT * FROM Users WHERE id = " + id, new TResultHandler<UsersDataSet>() {
            @Override
            public UsersDataSet handle(ResultSet resultSet) throws SQLException {
                resultSet.next();
                UsersDataSet dataSet = new UsersDataSet(resultSet.getLong("id"), resultSet.getString("name"));
                return dataSet;
            }
        });
    }
    
    public void set(UsersDataSet dataSet){
        
    }
    
}
