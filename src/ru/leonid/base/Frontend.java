package ru.leonid.base;

public interface Frontend extends Abonent {
    MessageSystem getMessageSystem();
    void setId(String name, Integer id);
}
