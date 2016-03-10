/*
 * Служба в отдельном потоке
 */

package ru.leonid.databaseService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.leonid.base.Address;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;
import ru.leonid.base.DatabaseService;

public class DatabaseServiceImpl implements DatabaseService, Runnable{
    private Address address = Address.getNew();
    private MessageSystem messageSystem;

    private UsersDAO usersDAO;
    private ResultsDAO resultsDAO;

    public DatabaseServiceImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
        Connection connection = getConnection();
        TExecutor exec = new TExecutor();
        String updates = "CREATE TABLE Users (id bigint, name varchar(256), primary key (id))";
        try {
            exec.execUpdate(connection, updates);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String updates1 = "CREATE TABLE Results (sessionId bigint, "
                + "id1 bigint, id2 int, score1 int, "
                + "score2 bigint, winnerId bigint, primary key (sessionId))";
        try {
            exec.execUpdate(connection, updates1);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        usersDAO = new UsersDAO(connection);
        resultsDAO = new ResultsDAO(connection);
        UsersDataSet tully = new UsersDataSet(1, "Tully");
        UsersDataSet sully = new UsersDataSet(2, "Sully");
        try {            
            usersDAO.add(tully);
            usersDAO.add(sully);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Integer getUserId(String name){
        try {
            TimeHelper.sleep(5000);
            UsersDataSet dataSet = usersDAO.getByName(name);
            if(dataSet == null){
                UsersDataSet ds = new UsersDataSet(0, name);
                usersDAO.add(ds);
            }        
            return dataSet.getId();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setGameResult(int id1, int id2, int result1, int result2, int winnerId){
        try {
            ResultsDataSet dataSet = new ResultsDataSet(0, id1, id2, result1, result2, winnerId);
            resultsDAO.add(dataSet);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }
    
    public static Connection getConnection() {
        try{
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
            StringBuilder url = new StringBuilder();
            url.
            append("jdbc:mysql://").		//db type
            append("192.168.0.110:"). 			//host name
            append("3306/").				//port
            append("leonid?").			//db name
            append("user=leonid&").			//login
            append("password=leonid");		//password
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }      
}
