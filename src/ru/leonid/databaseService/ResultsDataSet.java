/*
 * Объект с данными одной строки таблицы Results
 */

package ru.leonid.databaseService;

/**
 *
 * @author Лёня
 */
public class ResultsDataSet {
    private long sessionId;
    private long id1;
    private long id2;
    private int score1;
    private int score2;
    private long winnerId;

    public ResultsDataSet(long sessionId, long id1, long id2, int score1, int score2, long winnerId) {
        this.sessionId = sessionId;
        this.id1 = id1;
        this.id2 = id2;
        this.score1 = score1;
        this.score2 = score2;
        this.winnerId = winnerId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getId1() {
        return id1;
    }

    public long getId2() {
        return id2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public long getWinnerId() {
        return winnerId;
    }
    
    
}
