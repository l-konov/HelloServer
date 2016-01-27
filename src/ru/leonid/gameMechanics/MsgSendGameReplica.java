/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.Frontend;
import ru.leonid.messageSystem.MsgToFrontend;


public class MsgSendGameReplica extends MsgToFrontend{

    int id1, id2;
    int result1, result2;
    
    public MsgSendGameReplica(Address from, Address to, int id1, int id2, int result1, int result2) {
        super(from, to);
        this.id1 = id1;
        this.id2 = id2;
        this.result1 = result1;
        this.result2 = result2;
    }

    @Override
    public void exec(Frontend frontend) {
        // функция на фронтенде для вывода промежуточных значений
        frontend.refresh(id1, id2, result1, result2);
    }
    
}
