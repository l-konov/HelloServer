package ru.leonid.accountService;

import java.util.HashMap;
import java.util.Map;
import ru.leonid.base.AccountService;
import ru.leonid.base.Address;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;

public class AccountServiceImpl implements AccountService, Runnable{
	private Address address = Address.getNew();
	private MessageSystem messageSystem;
        
        private Map<String, Integer> fakeAccounter = new HashMap<String, Integer>();
	
	public AccountServiceImpl(MessageSystem messageSystem){
		this.messageSystem = messageSystem;
                this.fakeAccounter.put("Tully", 1);
		this.fakeAccounter.put("Sully", 2);
	}
	
	public void run(){
		while(true){
                        messageSystem.execForAbonent(this);
                        TimeHelper.sleep(10);
		}
	}
        
        public Integer getUserId(String name){
            TimeHelper.sleep(5000);
            return fakeAccounter.get(name);
        }

	public Address getAddress() {
		return address;
	}
	
	public MessageSystem getMessageSystem(){
		return messageSystem;
	}
}
