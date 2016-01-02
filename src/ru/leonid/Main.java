
package ru.leonid;

import org.eclipse.jetty.server.Server;

public class Main {
    
    public static void main(String[] args) throws Exception {
        //debug
        ThreadPool tp = new ThreadPool(5);
        tp.start();
        
        
//        // запускаем jetty
//        Server jettyServer = new Server(8090);
//        Frontend fe = new Frontend();
//        jettyServer.setHandler(fe);
//        try {
//            jettyServer.start();
//            jettyServer.join();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        // запускаем службу фронтенда
//        fe.start();
    }
    
}
