package ru.leonid.base;

public interface DatabaseService extends Abonent {
	MessageSystem getMessageSystem();

        Integer getUserId(String name);
}
