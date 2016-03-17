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
public class ResultsDataSetDAO {
    private SessionFactory sessionFactory;

    public ResultsDataSetDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void save(ResultsDataSet dataSet){
        Session session = sessionFactory.openSession();
        Transaction txn = session.beginTransaction();
        session.save(dataSet);
        txn.commit();
        session.close();
    }
    
    public ResultsDataSet read(int sessionId){
        Session session = sessionFactory.openSession();
        ResultsDataSet dataSet = session.load(ResultsDataSet.class, sessionId);
        return dataSet;
    }
}
