/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.leonid.databaseService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author leo
 */
public class UsersDataSetDAO {
    private SessionFactory sessionFactory;
    
    public UsersDataSetDAO(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;        
    }
    
    public void save(UsersDataSet dataSet){
        Session session = sessionFactory.openSession();
        Transaction txn = session.beginTransaction();
        session.save(dataSet);
        txn.commit();
        session.close();
    }
    
    public UsersDataSet read(int id){
        Session session = sessionFactory.openSession();
        UsersDataSet dataSet = session.load(UsersDataSet.class, id);
        return dataSet;
    }
    
    public UsersDataSet readByName(String name){
        Session s = sessionFactory.openSession();
        UsersDataSet dataSet = s.load(UsersDataSet.class, name);
        return dataSet;
    }
}
