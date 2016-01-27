package ru.leonid.frontend;


import java.io.IOException;
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
    
    int winnerId;
    
    class User{
        private String name;
        private int id;
        private int score;
        private enum State {NONE, WAITING, PLAYING};

        public User(String name, int id) {
            this.name = name;
            this.id = id;
            score = 0;
            isPlaying = false;
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
        public boolean isIsPlaying() {
            return isPlaying;
        }
        public void setIsPlaying(boolean isPlaying) {
            this.isPlaying = isPlaying;
        }
      
    }

    public FrontendImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public Address getAddress() { return address; }    
    
    public MessageSystem getMessageSystem(){ return messageSystem; }

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
        
        if(!request.getMethod().equals("POST")){
            // первый запрос от пользователя. Выводим страницу ввода имени
            if(request.getParameter("name") == null) 
                response.getWriter().println(pageGenerator.getInputNamePage());
            return;
        }
        if(request.getParameter("name") != null){
            String userName = request.getParameter("name");
            Integer id = nameToId.get(userName);
            if(id == null){
                response.getWriter().println(pageGenerator.getAuthorizationPage(userName));
                messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
                return;
            }
            // вывести вопрос пользователю: вы готовы играть?
            response.getWriter().println(pageGenerator.getTextPage("User name: " + userName + "\t Id: " + id, id));
            return;
        }
        if(request.getParameter("id") != null){
            int id = Integer.parseInt(request.getParameter("id"));
            
        }
        
        
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
        u1.setIsPlaying(true);
        User u2 = users.get(id2);
        u2.setIsPlaying(true);
    }
    
    // окончание игры
    public void finish(int id1, int id2, int result1, int result2, int winnerId){
        User u1 = users.get(id1);
        u1.setScore(result1);
        u1.setIsPlaying(false);
        User u2 = users.get(id2);
        u2.setScore(result2);
        u2.setIsPlaying(false);
        this.winnerId = winnerId;
    }
    
}
