/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid;

import ru.leonid.frontend.PageGenerator;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author Julia
 */
public class Frontend extends AbstractHandler implements Runnable{
    
    private AtomicInteger handleCount;
    
    public Frontend() {
        this.handleCount = new AtomicInteger(0);
    }

    @Override
    public void handle(String target, 
            Request rqst, 
            javax.servlet.http.HttpServletRequest request, 
            javax.servlet.http.HttpServletResponse response) 
            throws IOException, javax.servlet.ServletException 
    {
        // обрабатываем входные параметры запроса
        int id = 1;

        if(!request.getMethod().toLowerCase().equals("post") || request.getParameter("id") == null) {
            // формируем страницу
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            rqst.setHandled(true);
            PageGenerator pg = new PageGenerator("Your id is ", handleCount.incrementAndGet());
            response.getWriter().println(pg.getPageHtml());
        } else {
            String idParameter = request.getParameter("id");
            id = Integer.parseInt(idParameter);
            // формируем страницу
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            rqst.setHandled(true);
            PageGenerator pg = new PageGenerator("Hello user! Your id is ", id);
            response.getWriter().println(pg.getPageHtml());
        }
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
