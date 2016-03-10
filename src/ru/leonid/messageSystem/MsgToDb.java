package ru.leonid.messageSystem;

import ru.leonid.base.Abonent;
import ru.leonid.base.Address;
import ru.leonid.base.Msg;
import ru.leonid.base.DatabaseService;

public abstract class MsgToDb extends Msg{

	public MsgToDb(Address from, Address to) {
		super(from, to);		
	}

	public void exec(Abonent abonent) {
		if(abonent instanceof DatabaseService){
			exec((DatabaseService) abonent);
		}
	}

	public abstract void exec(DatabaseService accountService);
}
