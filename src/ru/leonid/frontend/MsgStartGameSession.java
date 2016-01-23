/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.frontend;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.messageSystem.MsgToGM;

/**
 *
 * @author Julia
 */
public class MsgStartGameSession extends MsgToGM{

    int id1, id2;
    
    public MsgStartGameSession(Address from, Address to, int id1, int id2) {
        super(from, to);
        this.id1 = id1;
        this.id2 = id2;
    }

    @Override
    public void exec(GameMechanics gameMechanics) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
