/*
 * Объект доступа к данным таблицы Results
 */

package ru.leonid.databaseService;

import java.sql.Connection;

/**
 *
 * @author Лёня
 */
public class ResultsDAO {
    private Connection con;

    public ResultsDAO(Connection con) {
        this.con = con;
    }
    
    
    
}
