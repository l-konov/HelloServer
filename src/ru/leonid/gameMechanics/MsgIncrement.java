
package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.messageSystem.MsgToGM;


public class MsgIncrement extends MsgToGM{
    
    int id;

    public MsgIncrement(Address from, Address to, int id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(GameMechanics gameMechanics) {
        gameMechanics.increment(id);
    }
    
}
