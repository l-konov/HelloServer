package ru.leonid.base;


public interface MessageSystem {
	
	void sendMessage(Msg message);
	
	void execForAbonent(Abonent abonent);
	
	AddressService getAddressService();
}
