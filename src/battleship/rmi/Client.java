package battleship.rmi;

import battleship.beans.Player;
import battleship.beans.Position;
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
            
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            this.environment = (Environment) registry.lookup("server");
            
            System.out.println("Connected to server");
            
        } catch (RemoteException | NotBoundException ex) {
            
            System.out.println("Error: " + ex.toString());
            
            System.out.println("No hay servidor en ejecución...");
            
            System.exit(0);
            
        }
        
    }
    
    public boolean wantToPlay(Player player) throws RemoteException {
        return this.environment.wantToPlay(player);
    }
    
    public boolean isReady() throws RemoteException {
        return this.environment.isReady();
    }
    
    public Player getPlayer1() throws RemoteException {
        return this.environment.getPlayer1();
    }
    
    public void setPlayingPlayer1(boolean value) throws RemoteException {
        this.environment.setPlayingPlayer1(value);
    }
    
    public void setPlayingPlayer2(boolean value) throws RemoteException {
        this.environment.setPlayingPlayer2(value);
    }
    
    public boolean isPlayingPlayer1() throws RemoteException {
        return this.environment.isPlayingPlayer1();
    }

    public boolean isPlayingPlayer2() throws RemoteException {
        return this.environment.isPlayingPlayer2();
    }
    
    public boolean shoot(Player player, Position position) throws RemoteException {
        return this.environment.shoot(player, position);
    }
    
    public void setLastShootP1(Position position) throws RemoteException {
        this.environment.setLastShootP1(position);
    }

    public void setLastShootP2(Position position) throws RemoteException {
        this.environment.setLastShootP2(position);
    }

    public Position gettLastShootP1() throws RemoteException {
        return this.environment.gettLastShootP1();
    }

    public Position gettLastShootP2() throws RemoteException {
        return this.environment.gettLastShootP2();
    }
    
    public void giveUp() throws RemoteException {
        this.environment.giveUp();
    }
    
    public boolean getgiveUp() throws RemoteException {
        return this.environment.getgiveUp();
    }
    
}
