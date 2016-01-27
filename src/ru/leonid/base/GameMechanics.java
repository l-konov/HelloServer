package ru.leonid.base;

public interface GameMechanics extends Abonent {
    
    MessageSystem getMessageSystem();
    void increment(int id);
    void startGameSession(int id1, int id2);
}
