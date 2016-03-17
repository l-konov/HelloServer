package ru.leonid.frontend;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import ru.leonid.base.Address;
import ru.leonid.base.Frontend;
import ru.leonid.base.MessageSystem;

import ru.leonid.utils.TimeHelper;

public class FrontendImpl extends AbstractHandler implements Frontend, Runnable{
    private Address address = Address.getNew();
    private MessageSystem messageSystem;
    
    private Map<String, User> usersStr = new ConcurrentHashMap<>(); // карта соответствий имя пользователя - id
    private Map<Integer, User> users = new ConcurrentHashMap<>(); // карта соответствий id - объект класса пользователь 
    
    private User waitingUser;

    int winnerId;
    
    /**
     * NONE - пользователь не авторизован
     * WAITING_PLAYER - ожидание второго игрока
     * PLAYING - в процессе игры
     * RESULT - вывод результатов
     */
    private static enum State{NONE, WAITING_PLAYER, PLAYING, RESULT};
    
    class User{
        private String name;
        private int id;
        private int score;
        private State state;
        private int opponentId;

        public User(String name, int id) {
            this.name = name;
            this.id = id;
            score = 0;
            state = State.NONE;
        }
        public int getScore(){
            return score;
        }
        public void setScore(int result){
            score = result;
        }
        public int getId(){
            return id;
        }
        public String getName(){
            return name;
        }
        public State getState() {
            return state;
        }
        public void setState(State state) {
            this.state = state;
        }
        public int getOpponentId() {
            return opponentId;
        }
        public void setOpponentId(int opponentId) {
            this.opponentId = opponentId;
        }
    }

    public FrontendImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
    {
        PageGenerator pageGenerator = new PageGenerator();
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);   
        Address addressAS = messageSystem.getAddressService().getAddressDB();
        Address addressGM = messageSystem.getAddressService().getAddressGM();
        PrintWriter responsePW = response.getWriter();
        
        // проверка, каким методом к нам пришёл запрос
        if(!request.getMethod().equals("POST")){
            responsePW.println(pageGenerator.getInputNamePage());
            return;
        }
        
        // пользователь ещё не авторизован
        if(request.getParameter("id") == null){
            if(request.getParameter("name") == null){
                responsePW.println(pageGenerator.getInputNamePage()); // окно ввода имени пользователя                
            } else {
                String userName = request.getParameter("name");
                User user = usersStr.get(userName);                
                if(user == null){ // авторизация не прошла
                    responsePW.println(pageGenerator.getAuthorizationPage(userName));
                    messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName)); // отсылаем сообщение с запросом о получении id для пользователя                                        
                } else { // авторизация прошла
                    int id = user.getId();
                    responsePW.println(pageGenerator.getStartingGamePage(id, user.getName(), false));
                    if(waitingUser == null) {
                        waitingUser = user;
                        user.setState(State.WAITING_PLAYER);
                    }                   
                } 
            }              
        }
        
        // пользователь авторизован
        if(request.getParameter("id") != null){
            int opponentId;
            User user2;
            int id = Integer.parseInt(request.getParameter("id"));
            User user = users.get(id);
            switch(user.getState()){
                case NONE:
                case WAITING_PLAYER:
                    boolean waiting = user.getState().equals(State.WAITING_PLAYER);
                    responsePW.println(pageGenerator.getStartingGamePage(id, user.getName(), waiting));
                    if(waitingUser == null) {
                        waitingUser = user;
                        user.setState(State.WAITING_PLAYER);
                    } else{
                        if(waitingUser.getId() != id) {
                            messageSystem.sendMessage(new MsgStartGameSession(getAddress(), addressGM, id, waitingUser.getId()));
                            waitingUser = null;
                        } 
                    }
                    break;
                case PLAYING:
                    opponentId = user.getOpponentId();
                    user2 = users.get(opponentId);
                    messageSystem.sendMessage(new MsgIncrement(getAddress(), addressGM, id)); // отсылаем сообщение о клике по кнопке
                    responsePW.println(pageGenerator.getClickPage(id, user.getName(), user.getScore(), opponentId, user2.getName(), user2.getScore()));
                    break;
                case RESULT:
                    opponentId = user.getOpponentId();
                    user2 = users.get(opponentId);
                    User winner = users.get(winnerId);
                    responsePW.println(pageGenerator.getResultPage(id, user.getName(), user.getScore(), 
                            opponentId, user2.getName(), user2.getScore(), 
                            winner.getName()));
                    break;       
            }
        }       
    }
       
    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public void setUserId(String name, Integer id) {
        if(!usersStr.containsKey(name)){
            User u = new User(name, id);
            usersStr.put(name, u);
            users.put(id, u);
        }
    }
    
    public void refresh(int id1, int id2, int result1, int result2){
        User u1 = users.get(id1);
        u1.setScore(result1);
        User u2 = users.get(id2);
        u2.setScore(result2);
    }
    
    // старт игры
    public void start(int id1, int id2){
        User u1 = users.get(id1);
        u1.setState(State.PLAYING);
        User u2 = users.get(id2);
        u2.setState(State.PLAYING);
        u1.setOpponentId(u2.getId());
        u2.setOpponentId(u1.getId());
    }
    
    // окончание игры
    public void finish(int id1, int id2, int result1, int result2, int winnerId){
        User u1 = users.get(id1);
        u1.setScore(result1);
        u1.setState(State.RESULT);
        User u2 = users.get(id2);
        u2.setScore(result2);
        u2.setState(State.RESULT);
        this.winnerId = winnerId;
    }
    
    public Address getAddress() { return address; }    
    
    public MessageSystem getMessageSystem(){ return messageSystem; }    
    
}
