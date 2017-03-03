package battleship.rmi;

import battleship.beans.Player;
import battleship.rmi.impl.Environment;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author VakSF
 */
public class Client {
    
    private Environment environment;
    
    public Client() {
        this.connectServer();
    }
    
    private void connectServer() {
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
            
            this.environment = (Environment) registry.lookup("server");
            
            System.out.println("Connected to server");
            
        } catch (RemoteException | NotBoundException ex) {
            
        }
        
    }
    
    public void send(Player player) throws RemoteException {
        this.environment.setCurrentPlayer(player);
    }
    
}
