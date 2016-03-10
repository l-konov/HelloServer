/*
 * Объект доступа к данным таблицы Results
 */

package ru.leonid.databaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Лёня
 */
public class ResultsDAO {
    private Connection con;

    public ResultsDAO(Connection con) {
        this.con = con;
    }
    
    public ResultsDataSet get(long sessionId) throws SQLException{
        TExecutor ex = new TExecutor();
        return ex.execQuery(con, "SELECT * FROM Results WHERE sessionId = " + sessionId, new TResultHandler<ResultsDataSet>() {
            @Override
            public ResultsDataSet handle(ResultSet resultSet) throws SQLException {
                resultSet.next();
                ResultsDataSet dataSet = new ResultsDataSet(resultSet.getLong("sessionId"), 
                        resultSet.getLong("id1"), 
                        resultSet.getLong("id2"), 
                        resultSet.getInt("score1"), 
                        resultSet.getInt("score2"), 
                        resultSet.getLong("winnerId"));
                return dataSet;
            }
        });
    }
    
}
