package ru.leonid.gameMechanics;

import ru.leonid.base.Address;
import ru.leonid.base.GameMechanics;
import ru.leonid.base.GameSession;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;

public class GameMechanicsImpl implements GameMechanics,Runnable{
    private MessageSystem messageSystem;
    private Address address = Address.getNew();
    private GameSession gameSession;

    public void startGameSession(int id1, int id2) {
        gameSession = new GameSessionImpl(id1, id2);
        gameSession.startGame();
    }
    
    // все данные о партии игроков
    class GameSessionImpl implements Runnable, GameSession{
        int id1;
        int id2;
        int result1;
        int result2;
        long startTime;
        
        private final long GAMEPERIOD = 10000;

        public GameSessionImpl(int id1, int id2) {
            super();
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
            if(result1 >= result2) return id1;
            else return id2;
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

    public GameSession getGameSession() {
        return gameSession;
    }
    
    
}
