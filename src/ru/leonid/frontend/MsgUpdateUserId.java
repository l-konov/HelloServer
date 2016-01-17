package ru.leonid.frontend;

import ru.leonid.messageSystem.MsgToFrontend;
import ru.leonid.base.Address;
import ru.leonid.base.Frontend;

public class MsgUpdateUserId extends MsgToFrontend {

	public MsgUpdateUserId(Address from, Address to) {
		super(from, to);
	}

	public void exec(Frontend frontend) {
		System.out.print("Address of Frontend: " + frontend.getAddress().getAbonentId() + '\n');		
	}

}
