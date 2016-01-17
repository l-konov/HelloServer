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

    public MsgStartGameSession(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(GameMechanics gameMechanics) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
