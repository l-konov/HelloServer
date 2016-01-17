/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julia
 */
public class ThreadPool {
    
    private Set<Thread> pool;
    private static long currentMinId = 0;
    
    // конструктор
    public ThreadPool(int N) {
        pool = new HashSet<>(N);
        System.out.println("List of threads ids:");
        for(int i = 0; i < N; i++){
            ElementaryThread et = new ElementaryThread(); 
            System.out.println(et.getId());
            pool.add(et);  
        }
        System.out.println("----------------------------");
        currentMinId = getCurrentMinId(0);
    }
    
    // запустить ThreadPool
    public void start(){
        Iterator<Thread> it = pool.iterator();
        while(it.hasNext())
            it.next().start();
    }
    
    // вычисление минимального id не меньше min
    private long getCurrentMinId(long min){
        Iterator<Thread> it = pool.iterator();
        long currentMin = Long.MAX_VALUE;
        while(it.hasNext()){
            long id = it.next().getId();
            if(id > min && id < currentMin)
                currentMin = id;
        }
        System.out.println("currentMinId = " + currentMin);
        return currentMin;
    }
    
    /**
     * Класс элементарного потока
     */
    private class ElementaryThread extends Thread{
        
        private Object waitObject;
        
        //конструктор
        public ElementaryThread() {
            this.waitObject = new Object();
        }
        
        @Override
        public void run(){
            long id = Thread.currentThread().getId();
            synchronized(waitObject){
                // если id текущего потока больше чем минимальный - ждём
                while(id > currentMinId){
                    try {
                        waitObject.wait(3000);
                    } catch (InterruptedException ex) { }
                }
                // изменяем минимальный id
                //currentMinId = getCurrentMinId(currentMinId);
                currentMinId++;
                // выводим текущий id
                System.out.println(id + ", " + currentMinId);
                // запускаем остальных для проверки
                waitObject.notifyAll();
            }
        }    
    }
}
    
