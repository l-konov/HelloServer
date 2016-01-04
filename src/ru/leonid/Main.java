
package ru.leonid;

import org.eclipse.jetty.server.Server;

public class Main {
    
    public static void main(String[] args) throws Exception {
//        //проверка рботы ThreadPool
//        ThreadPool tp = new ThreadPool(5);
//        tp.start();
        // запускаем фронтенд
        Frontend fe = new Frontend();
        new Thread(fe).start();
        // запускаем jetty
        Server jettyServer = new Server(8080);
        jettyServer.setHandler(fe);
        try {
            jettyServer.start();
            jettyServer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } 
    }
    
}
