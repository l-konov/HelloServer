/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.DatabaseService;
import ru.leonid.messageSystem.MsgToDb;

/**
 *
 * @author leo
 */
public class MsgSetResultToDb extends MsgToDb{

    int id1, id2, winnerId;
    int result1, result2;    
    
    public MsgSetResultToDb(Address from, Address to, int id1, int id2, int result1, int result2, int winnerId) {
        super(from, to);
        this.id1 = id1;
        this.result1 = result1;
        this.id2 = id2;
        this.result2 = result2;
        this.winnerId = winnerId;
    }

    @Override
    public void exec(DatabaseService dbService) {
        dbService.setGameResult(id1, id2, result1, result2, winnerId);
    }
    
}
