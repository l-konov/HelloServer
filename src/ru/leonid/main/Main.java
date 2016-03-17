package ru.leonid.main;

import ru.leonid.messageSystem.MessageSystemImpl;
import org.eclipse.jetty.server.Server;
import ru.leonid.databaseService.DatabaseServiceImpl;
import ru.leonid.base.Abonent;
import ru.leonid.frontend.FrontendImpl;
import ru.leonid.gameMechanics.GameMechanicsImpl;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageSystemImpl messageSystem = new MessageSystemImpl();

        FrontendImpl frontend = new FrontendImpl(messageSystem);
        messageSystem.addFrontend((Abonent) frontend);

        DatabaseServiceImpl accountService = new DatabaseServiceImpl(messageSystem);
        messageSystem.addAccountService(accountService);
        
        GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
        messageSystem.addGameMechanics(gameMechanics);
        
        (new Thread(frontend)).start();
        (new Thread(accountService)).start();
        (new Thread(gameMechanics)).start();
        
        Server server = new Server(8081);
        server.setHandler(frontend);

        server.start();
        server.join();
    }

    protected static void test(){
    }
	
}
