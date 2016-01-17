package ru.leonid.frontend;

import ru.leonid.messageSystem.MsgToAS;
import ru.leonid.base.AccountService;
import ru.leonid.base.Address;

public class MsgGetUserId extends MsgToAS {
        String name;
    
	public MsgGetUserId(Address from, Address to, String name) {
		super(from, to);
                this.name = name;
	}

	public void exec(AccountService accountService) {
                Integer id = accountService.getUserId(name);                
		accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), name, id));
	}
}
