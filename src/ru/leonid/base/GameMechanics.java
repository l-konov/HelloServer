package ru.leonid.base;

public interface GameMechanics extends Abonent {
    
    MessageSystem getMessageSystem();
    GameSession getGameSession();
    void startGameSession(int id1, int id2);
}
