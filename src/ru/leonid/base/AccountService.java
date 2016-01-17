package ru.leonid.base;

public interface AccountService extends Abonent {
	MessageSystem getMessageSystem();

        Integer getUserId(String name);
}
