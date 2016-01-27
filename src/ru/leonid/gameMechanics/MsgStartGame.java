/*
 * CСообщение о начале игры на фронтенд
 */
package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.Frontend;
import ru.leonid.messageSystem.MsgToFrontend;

public class MsgStartGame extends MsgToFrontend{
    int id1, id2;
    
    public MsgStartGame(Address from, Address to, int id1, int id2) {
        super(from, to);
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public void exec(Frontend frontend) {
        frontend.start(id1, id2);
    }
    
}
