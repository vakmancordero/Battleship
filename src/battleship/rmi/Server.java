package battleship.rmi;

import battleship.beans.Player;
import battleship.rmi.impl.Environment;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author VakSF
 */
public class Server extends UnicastRemoteObject implements Environment {
    
    private Player currentPlayer;
    
    public Server() throws RemoteException {
        super();
    }

    @Override
    public void setCurrentPlayer(Player player) throws RemoteException {
        this.currentPlayer = player;
    }

    @Override
    public Player getCurrentPlayer() throws RemoteException {
        return this.currentPlayer;
    }
    
}
