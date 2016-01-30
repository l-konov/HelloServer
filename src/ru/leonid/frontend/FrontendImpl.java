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
import ru.leonid.gameMechanics.MsgIncrement;

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
            switch(user.getState()){
                case NONE:
                    
                    responsePW.println(pageGenerator.getStartingGamePage(id));
                    //getTextPage("User name: " + user.getName() + "\t Id: " + id, id));                   
                    break;
                case PLAYING:
                    responsePW.println(pageGenerator.getClickPage(id));
                    break;
                case RESULT:
                    responsePW.println(pageGenerator.getResultPage(id));
                    break;
                    
            }
        } else { // пользователь не авторизован
            if(request.getParameter("name") != null){
                String userName = request.getParameter("name");
                Integer id = nameToId.get(userName);
                if(id == null){
                    responsePW.println(pageGenerator.getAuthorizationPage(userName));
                    messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
                    return;
                }
            } else {
                responsePW.println(pageGenerator.getInputNamePage());
                return;
            }        
        }       
        
        
        
        
        
        
//        if(!request.getMethod().equals("POST")){
//            // первый запрос от пользователя. Выводим страницу ввода имени
//            if(request.getParameter("name") == null) 
//                
//            return;
//        }
//        if(request.getParameter("name") != null){
//            String userName = request.getParameter("name");
//            Integer id = nameToId.get(userName);
//            if(id == null){
//                response.getWriter().println(pageGenerator.getAuthorizationPage(userName));
//                messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
//                return;
//            }
//            // вывести вопрос пользователю: вы готовы играть?
//            response.getWriter().println(pageGenerator.getTextPage("User name: " + userName + "\t Id: " + id, id));
//            return;
//        }
//        
//        
//
//        
//        
//        if(!gameStarted && !gameFinished){
//            if(!request.getMethod().equals("POST")){
//                // первый запрос от пользователя. Выводим страницу ввода имени
//                if(request.getParameter("name") == null) 
//                    response.getWriter().println(pageGenerator.getInputNamePage());
//            }
//            else{
//                if(request.getParameter("name") != null){
//                    // Обработка ответа пользователя, если в ответе присутствует имя
//                    String userName = request.getParameter("name");
//                    Integer ID = nameToId.get(userName);
//                    if(ID == null){
//                        response.getWriter().println(pageGenerator.getAuthorizationPage(userName));
//                        messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
//                    } else {
//                        response.getWriter().println(pageGenerator.getTextPage("User name: " + userName + "\t Id: " + ID, ID));
//                        if(users.size() > 1) {
//                            messageSystem.sendMessage(new MsgStartGameSession( getAddress(), addressGM, users.get(0).getId(), users.get(1).getId() ));
//                        }
//
//                    }                
//                }
//            }
//        } else if(gameStarted && !gameFinished){
//            // если игра началась
//            if(request.getParameter("id") != null){
//                int id = Integer.parseInt(request.getParameter("id"));
//                response.getWriter().println(pageGenerator.getTextPage("", id));
//                messageSystem.sendMessage(new MsgIncrement(getAddress(), addressGM, id));
//            }        
//        }
//        else if(!gameStarted && gameFinished){
//            // вывод результатов игры
//            
//        }
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
