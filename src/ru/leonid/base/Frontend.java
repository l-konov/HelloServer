package ru.leonid.base;

public interface Frontend extends Abonent {
    MessageSystem getMessageSystem();
    void createUser(String name, Integer id);
    void refresh(int id1, int id2, int r1, int r2);
    void finish(int id1, int id2, int r1, int r2, int winner);
    void start(int id1, int id2);
}
