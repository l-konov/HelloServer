package ru.leonid.base;

public interface DatabaseService extends Abonent {
	MessageSystem getMessageSystem();

        Integer getUserId(String name);
        
        void setGameResult(int id1, int id2, int result1, int result2, int winnerId);
}
