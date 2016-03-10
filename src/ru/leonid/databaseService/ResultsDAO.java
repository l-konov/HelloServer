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
                ResultsDataSet dataSet = new ResultsDataSet(resultSet.getInt("sessionId"), 
                        resultSet.getInt("id1"), 
                        resultSet.getInt("id2"), 
                        resultSet.getInt("score1"), 
                        resultSet.getInt("score2"), 
                        resultSet.getInt("winnerId"));
                return dataSet;
            }
        });
    }
    
    public void add(ResultsDataSet dataSet) throws SQLException{
        TExecutor ex = new TExecutor();
        int id1 = dataSet.getId1();
        int id2 = dataSet.getId2();
        int score1 = dataSet.getScore1();
        int score2 = dataSet.getScore2();
        int winnerId = dataSet.getWinnerId();
        String s1 = String.format("INSERT INTO Results (id1, id2, score1, score2, winnerId) "
                + "VALUES (%d, %d, %d, %d, %d)", id1, id2, score1, score2, winnerId);
        ex.execUpdate(con, s1);
    }
    
    public void delete(int sessionId) throws SQLException{
        TExecutor ex = new TExecutor();
        String update = "DELETE FROM Results WHERE sessionId=" + sessionId;
        ex.execUpdate(con, update);
    }
    
}
