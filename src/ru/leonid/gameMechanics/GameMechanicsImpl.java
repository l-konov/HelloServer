package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;

public class GameMechanicsImpl implements GameMechanics,Runnable{
    private MessageSystem messageSystem;
    private Address address = Address.getNew();
    private GameSession gameSession;
 
    // все данные о партии игроков
    class GameSession implements Runnable{
        int id1;
        int id2;
        int result1;
        int result2;
        long startTime;
        
        private final long GAMEPERIOD = 30000;

        public GameSession(int id1, int id2) {
            this.id1 = id1;
            this.id2 = id2;
            this.result1 = 0;
            this.result2 = 0;
            Address addressFrontend = messageSystem.getAddressService().getAddressFrontend();
            messageSystem.sendMessage(new MsgStartGame(getAddress(), addressFrontend, id1, id2));
        }
        
        public void startGame(){
            startTime = System.currentTimeMillis();
            new Thread(this).start();
        }
        
        public void increment(int id){
            if(id == id1) result1++;
            if(id == id2) result2++;
        }
        
        private int getWinner(){
            if(result1 >= result2) 
                return id1;
            else 
                return id2;
        }

        public void run() {
            Address addressFrontend = messageSystem.getAddressService().getAddressFrontend();
            while(System.currentTimeMillis() - startTime > GAMEPERIOD){
                // периодически посылаем реплику на фронтенд  
                messageSystem.sendMessage(new MsgSendGameReplica(getAddress(), addressFrontend, id1, id2, result1, result2));
                TimeHelper.sleep(100);
            }
            // посылаем на фронтент сообщение об окончании игры и победителе
            int winner = getWinner();
            messageSystem.sendMessage(new MsgFinishGame(getAddress(), addressFrontend, id1, id2, result1, result2, winner));
        }
         
    }
    
    public GameMechanicsImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }
    
    public MessageSystem getMessageSystem() { return messageSystem;  }
    public Address getAddress() { return address; }

    public void run() {
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }
    
    public void startGameSession(int id1, int id2) {
        gameSession = new GameSession(id1, id2);
        gameSession.startGame();
    }    
    
    public void increment(int id){
        getGameSession().increment(id);
    }

    public GameSession getGameSession() {
        return gameSession;
    }
    
    
}
