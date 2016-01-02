/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author Julia
 */
public class Frontend extends AbstractHandler implements Runnable{

    public Frontend() {
        this.handleCount = new AtomicInteger(0);
    }
    
    private AtomicInteger handleCount;

    @Override
    public void handle(String target, 
            Request rqst, 
            javax.servlet.http.HttpServletRequest request, 
            javax.servlet.http.HttpServletResponse response) 
            throws IOException, javax.servlet.ServletException 
    {
        // обрабатываем входные параметры запроса
        int id = 0;
        try{
            String idS = request.getParameter("id");
            if(idS != null){
                id = Integer.parseInt(idS);
            }
            else{
                id = handleCount.incrementAndGet();
            }
        } catch(Exception e){ }
        // формируем страницу
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        rqst.setHandled(true);
        PageGenerator pg = new PageGenerator(id);
        response.getWriter().println(pg.getPageHtml());
    }

    @Override
    public void run() {
        while(true){
            System.out.println("handleCount = " + handleCount);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println("Interrupted");
            }
        }
    }
    
}
