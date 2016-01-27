package ru.leonid.frontend;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
    
    Map<Integer, User> users = new ConcurrentHashMap<>();
    boolean gameStarted = false;
    boolean gameFinished = false;
    
    int id1, id2;
    int winnerId;
    
    int result1, result2;
    
    class User{
        private int id;
        private int score;

        public User(int id) {
            this.id = id;
            score = 0;
        }
        

        
        public int getScore(){
            return score;
        }
        
        public int getId(){
            return id;
        }
    }
    
    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

    public FrontendImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public Address getAddress() {
        return address;
    }    
    
    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PageGenerator pageGenerator = new PageGenerator();
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);   
        Address addressAS = messageSystem.getAddressService().getAddressAS();
        Address addressGM = messageSystem.getAddressService().getAddressGM();
        
        if(!gameStarted && !gameFinished){
            if(!request.getMethod().equals("POST")){
                // первый запрос от пользователя. Выводим страницу ввода имени
                if(request.getParameter("name") == null) 
                    response.getWriter().println(pageGenerator.getInputNamePage());
            }
            else{
                if(request.getParameter("name") != null){
                    // Обработка ответа пользователя, если в ответе присутствует имя
                    String userName = request.getParameter("name");
                    Integer ID = nameToId.get(userName);
                    if(ID == null){
                        response.getWriter().println(pageGenerator.getAuthorizationPage(userName));
                        messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
                    } else {
                        response.getWriter().println(pageGenerator.getTextPage("User name: " + userName + "\t Id: " + ID, ID));
                        users.put(ID, new User(ID));
                        if(users.size() > 1) {
                            messageSystem.sendMessage(new MsgStartGameSession( getAddress(), addressGM, users.get(0).getId(), users.get(1).getId() ));
                        }

                    }                
                }
            }
        } else if(gameStarted && !gameFinished){
            // если игра началась
            if(request.getParameter("id") != null){
                int id = Integer.parseInt(request.getParameter("id"));
                response.getWriter().println(pageGenerator.getTextPage("", id));
                messageSystem.sendMessage(new MsgIncrement(getAddress(), addressGM, id));
            }        
        }
        else if(!gameStarted && gameFinished){
            // вывод результатов игры
            
        }
    }

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            //removeDeadUsers();
            TimeHelper.sleep(10);
        }
    }
    
//    private void removeDeadUsers(){
//        // TODO implement
//    }

    @Override
    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }
    
    public void refresh(int id1, int id2, int result1, int result2){
        this.result1 = result1;
        this.result2 = result2;
    }
    
    // старт игры
    public void start(int id1, int id2){
        gameStarted = true;
    }
    
    // окончание игры
    public void finish(int id1, int id2, int result1, int result2, int winnerId){
        
        gameFinished = true;
        gameStarted = false;
    }
    
}
