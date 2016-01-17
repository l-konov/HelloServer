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

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Address getAddress() {
        return address;
    }

    public void handle(String target, Request baseRequest,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        String name = "Tully";
        Integer ID = nameToId.get(name);
        if(ID != null){
            response.getWriter().println("<h1>User name: " + name + " Id: " + ID +"</h1>");
        } else{
            response.getWriter().println("<h1>Wait for authorization</h1>");
            Address addressAS =  messageSystem.getAddressService().getAddressAS();
            messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
        }
        
        
        
        
        if(!request.getMethod().toLowerCase().equals("post") || request.getParameter("id") == null) {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            PageGenerator pg = new PageGenerator("Your id is ", handleCount.incrementAndGet());
            response.getWriter().println(pg.getPageHtml());
        } else {
            String idParameter = request.getParameter("id");
            id = Integer.parseInt(idParameter);
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            PageGenerator pg = new PageGenerator("Hello user! Your id is ", id);
            response.getWriter().println(pg.getPageHtml());
        }        
        
        Address addressAS =  messageSystem.getAddressService().getAddressAS();
        messageSystem.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }

    @Override
    public void setId(String name, Integer id) {
        nameToId.put(name, id);
    }
}
