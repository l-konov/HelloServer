package ru.leonid.frontend;

import ru.leonid.databaseService.MsgUpdateUserId;
import ru.leonid.messageSystem.MsgToDb;
import ru.leonid.base.Address;
import ru.leonid.base.DatabaseService;

public class MsgGetUserId extends MsgToDb {
        String name;
    
	public MsgGetUserId(Address from, Address to, String name) {
		super(from, to);
                this.name = name;
	}

	public void exec(DatabaseService accountService) {
                Integer id = accountService.getUserId(name);                
		accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), name, id));
	}
}
