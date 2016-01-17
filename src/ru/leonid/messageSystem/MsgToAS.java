package ru.leonid.messageSystem;

import ru.leonid.base.Abonent;
import ru.leonid.base.AccountService;
import ru.leonid.base.Address;
import ru.leonid.base.Msg;

public abstract class MsgToAS extends Msg{

	public MsgToAS(Address from, Address to) {
		super(from, to);		
	}

	public void exec(Abonent abonent) {
		if(abonent instanceof AccountService){
			exec((AccountService) abonent);
		}
	}

	public abstract void exec(AccountService accountService);
}
