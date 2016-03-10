/*
 * Служба в отдельном потоке
 */

package ru.leonid.databaseService;

import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import ru.leonid.base.Address;
import ru.leonid.base.MessageSystem;
import ru.leonid.utils.TimeHelper;
import ru.leonid.base.DatabaseService;

public class DatabaseServiceImpl implements DatabaseService, Runnable{
    private Address address = Address.getNew();
    private MessageSystem messageSystem;

    private Map<String, Integer> accounter = new HashMap<String, Integer>();

    public DatabaseServiceImpl(MessageSystem messageSystem){
        this.messageSystem = messageSystem;
        this.accounter.put("Tully", 1);
        this.accounter.put("Sully", 2);
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

    public void run(){
        while(true){
            messageSystem.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Integer getUserId(String name){
        TimeHelper.sleep(5000);
        if(!accounter.containsKey(name))
            accounter.put(name, Math.round((float)Math.random() * 100));
        return accounter.get(name);        
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem(){
        return messageSystem;
    }
}
