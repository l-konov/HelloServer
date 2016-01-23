package ru.leonid.frontend;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    
    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

    public FrontendImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
    }

    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        PageGenerator pageGenerator = new PageGenerator();
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);        
        // первый запрос от пользователя. Выводим страницу ввода имени
        if(!request.getMethod().equals("POST") || request.getParameter("name") == null) {
            response.getWriter().println(pageGenerator.getInputNamePage());
        }        
        // Обработка ответа пользователя, если в ответе присутствует имя
        else if(request.getMethod().equals("POST") && request.getParameter("name") != null){
            String userName = request.getParameter("name");
            Integer ID = nameToId.get(userName);
            if(ID != null){
                response.getWriter().println(pageGenerator.getTextPage("User name: " + userName + "\t Id: " + ID, ID));
            } else {
                response.getWriter().println(pageGenerator.getAuthorizationPage(userName));
                Address addressAS =  messageSystem.getAddressService().getAddressAS();
                messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, userName));
            }
        }
        
//        if(!request.getMethod().toLowerCase().equals("post") || request.getParameter("id") == null) {
//            response.setContentType("text/html; charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            baseRequest.setHandled(true);
//            PageGenerator pg = new PageGenerator("Your id is ", handleCount.incrementAndGet());
//            response.getWriter().println(pg.getPageHtml());
//        } else {
//            String idParameter = request.getParameter("id");
//            id = Integer.parseInt(idParameter);
//            response.setContentType("text/html; charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            baseRequest.setHandled(true);
//            PageGenerator pg = new PageGenerator("Hello user! Your id is ", id);
//            response.getWriter().println(pg.getPageHtml());
//        }        
    }

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            removeDeadUsers();
            TimeHelper.sleep(10);
        }
    }
    
    private void removeDeadUsers(){
        // TODO implement
    }

    public Address getAddress() {
        return address;
    }    
    
    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    @Override
    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }
}
