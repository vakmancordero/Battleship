package battleship.rmi.impl;

import battleship.beans.Player;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public interface Environment extends Remote {
    
    public void setCurrentPlayer(Player player) throws RemoteException;
    
    public Player getCurrentPlayer() throws RemoteException;
    
}
