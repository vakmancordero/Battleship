package battleship.server;

import battleship.rmi.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author VakSF
 */
public class PlayServer {
    
    public static void main(String[] args) {
        
        try {
            
            Registry registry = LocateRegistry.createRegistry(1099);
            
            registry.rebind("server", new Server());
            
            System.out.println("Server started");
            
        } catch (RemoteException ex) {
            
            System.out.println("Error: " + ex.toString());
            
        }
        
    }
    
}
