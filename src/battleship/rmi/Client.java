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
        
        System.out.println("Connecting");
        
        this.connectServer();
    }
    
    private void connectServer() {
        
        try {
            
            Registry registry = LocateRegistry.getRegistry("192.168.0.8", 1099);
            
            this.environment = (Environment) registry.lookup("server");
            
            System.out.println("Connected to server");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Error: " + ex.toString());
            
        }
        
    }
    
    public void send(Player player) throws RemoteException {
        
        System.out.println("Sending");
        
        this.environment.setCurrentPlayer(player);
    }
    
}
