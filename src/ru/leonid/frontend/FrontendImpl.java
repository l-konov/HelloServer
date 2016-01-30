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
    
    private Map<Integer, User> users = new ConcurrentHashMap<>();  
    private Map<String, Integer> nameToId = new ConcurrentHashMap<>();
    
    private List<User> waitingList = Collections.synchronizedList(new ArrayList<>());
    
    /**
     * NONE - пользователь не авторизован
     * WAITING_PLAYER - ожидание второго игрока
     * PLAYING - в процессе игры
     * RESULT - вывод результатов
     */
    private static enum State{NONE, WAITING_PLAYER, PLAYING, RESULT};
    
    int winnerId;
    
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
        Address addressAS = messageSystem.getAddressService().getAddressAS();
        Address addressGM = messageSystem.getAddressService().getAddressGM();
        PrintWriter responsePW = response.getWriter();
        
        // проверка, каким методом к нам пришёл запрос
        if(!request.getMethod().equals("POST")){
            responsePW.println(pageGenerator.getInputNamePage());
            return;
        }
            
        // пользователь авторизован
        if(request.getParameter("id") != null){
            int id = Integer.parseInt(request.getParameter("id"));
            User user = users.get(id);
            int opponentId;
            User user2;
            switch(user.getState()){
                case NONE:
                    responsePW.println(pageGenerator.getStartingGamePage(id, user.getName(), false));
                    if(waitingList.isEmpty()) {
                        waitingList.add(user);
                        user.setState(State.WAITING_PLAYER);
                    } else{
                        for(User u2 : waitingList)
                            if(u2.getId() != id) {
                                messageSystem.sendMessage(new MsgStartGameSession(getAddress(), addressGM, id, u2.getId()));
                                waitingList.remove(u2);
                            }
                    }
                    break;
                case WAITING_PLAYER:
                    responsePW.println(pageGenerator.getStartingGamePage(id, user.getName(), true));
                    if(waitingList.size() > 1){
                        for(User u2 : waitingList)
                            if(u2.getId() != id) {
                                messageSystem.sendMessage(new MsgStartGameSession(getAddress(), addressGM, id, u2.getId())); // отсылаем сообщение о начале игровой сессии
                                waitingList.remove(u2); // удаляем пользоватей из очереди
                                waitingList.remove(users.get(id));
                            }
                    } 
                    break;
                case PLAYING:
                    opponentId = user.getOpponentId();
                    user2 = users.get(opponentId);
                    messageSystem.sendMessage(new MsgIncrement(getAddress(), addressGM, id)); // отсылаем сообщение о клике по кнопке
                    responsePW.println(pageGenerator.getClickPage(id, user.getName(), opponentId, user2.getName()));
                    break;
                case RESULT:
                    opponentId = user.getOpponentId();
                    user2 = users.get(opponentId);
                    responsePW.println(pageGenerator.getResultPage(id, user.getName(), opponentId, user2.getName(), winnerId));
                    break;       
            }
        } else { // пользователь не авторизован
            if(request.getParameter("name") != null){
                String userName = request.getParameter("name");
                Integer id = nameToId.get(userName);
                if(id == null){
                    responsePW.println(pageGenerator.getAuthorizationPage(userName));
                    messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName)); // отсылаем сообщение с запросом о получении id для пользователя
                }
            } else {
                responsePW.println(pageGenerator.getInputNamePage());
            }        
        }       
    }

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public void createUser(String name, Integer id) {
        nameToId.put(name, id);
        users.put(id, new User(name, id));
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
