package ru.leonid.messageSystem;

import ru.leonid.base.*;

public abstract class MsgToGM extends Msg{

	public MsgToGM(Address from, Address to) {
		super(from, to);		
	}

	public void exec(Abonent abonent) {
		if(abonent instanceof GameMechanics){
			exec((GameMechanics) abonent);
		}
	}

	public abstract void exec(GameMechanics gameMechanics);
}
