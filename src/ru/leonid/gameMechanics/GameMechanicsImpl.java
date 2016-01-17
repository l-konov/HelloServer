/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;

/**
 *
 * @author Julia
 */
public class GameMechanicsImpl implements GameMechanics,Runnable{
    private MessageSystem messageSystem;
    private Address address = Address.getNew();
    
    public GameMechanicsImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }
        
    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while(true){
        messageSystem.execForAbonent(this);
        TimeHelper.sleep(10);
        }
    }
    
}
