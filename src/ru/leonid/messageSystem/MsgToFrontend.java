package ru.leonid.messageSystem;

import ru.leonid.base.Abonent;
import ru.leonid.base.Address;
import ru.leonid.base.Frontend;
import ru.leonid.base.Msg;

public abstract class MsgToFrontend extends Msg{

	public MsgToFrontend(Address from, Address to) {
		super(from, to);
	}

	public void exec(Abonent abonent) {
		if(abonent instanceof Frontend){
			exec((Frontend)abonent);
		}
	}
	
	public abstract void exec(Frontend frontend);
}
