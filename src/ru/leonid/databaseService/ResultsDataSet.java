/*
 * Объект с данными одной строки таблицы Results
 */

package ru.leonid.databaseService;

/**
 *
 * @author Лёня
 */
public class ResultsDataSet {
    private int sessionId;
    private int id1;
    private int id2;
    private int score1;
    private int score2;
    private int winnerId;

    public ResultsDataSet(int sessionId, int id1, int id2, int score1, int score2, int winnerId) {
        this.sessionId = sessionId;
        this.id1 = id1;
        this.id2 = id2;
        this.score1 = score1;
        this.score2 = score2;
        this.winnerId = winnerId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getId1() {
        return id1;
    }

    public int getId2() {
        return id2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public int getWinnerId() {
        return winnerId;
    }
    
    
}
