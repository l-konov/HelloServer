package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;

public class GameMechanicsImpl implements GameMechanics,Runnable{
    private MessageSystem messageSystem;
    private Address address = Address.getNew();
    
    class GameSession{
        
    }
    
    
    
    public GameMechanicsImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }
    
    private void startGameSession(int id1, int id2){
        int winnerId;
        TimeHelper.sleep(5000);
        if(Math.random() < 0.5) 
            winnerId = id1;
        else 
            winnerId = id2;
        
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
