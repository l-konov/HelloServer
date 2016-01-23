package ru.leonid.frontend;

import ru.leonid.messageSystem.MsgToFrontend;
import ru.leonid.base.Address;
import ru.leonid.base.Frontend;

public class MsgUpdateUserId extends MsgToFrontend {
        String name;
        Integer id;

	public MsgUpdateUserId(Address from, Address to, String name, Integer id) {
		super(from, to);
                this.name = name;
                this.id = id;
	}

	public void exec(Frontend frontend) {
                frontend.setId(name, id);		
	}

}
