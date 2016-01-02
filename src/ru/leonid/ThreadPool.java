/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Julia
 */
public class ThreadPool {
    
    Set<Thread> pool;
    
    public ThreadPool(int N) {
        pool = new HashSet<>(N);
        for(int i = 0; i < N; i++){
            pool.add(new ElementaryThread());
        }
    }
    
    public void start(){
        Iterator it = pool.iterator();
        while(it.hasNext()){
            Thread t = (Thread) it.next();
            t.start();
        }
    }
    
    protected class ElementaryThread extends Thread{
        @Override
        public void run(){
            System.out.println("ThreadName is " + Thread.currentThread().getName());
        }
    }
    
}
