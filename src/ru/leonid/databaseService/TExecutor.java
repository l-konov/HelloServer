/*
 * Класс с типизированными методами для работы с запросами
 */

package ru.leonid.databaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Лёня
 */
public class TExecutor {
    public <T> T execQuery(Connection connection, 
            String query, 
            TResultHandler<T> handler)
            throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        T value = handler.handle(result);
        result.close();
        stmt.close();

        return value;
    }

    public void execUpdate(Connection connection, DataSet sataSet){
        
    }
}
