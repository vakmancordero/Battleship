package battleship.rmi.impl;

import battleship.beans.Player;
import battleship.beans.Position;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author VakSF
 */
public interface Environment extends Remote {
    
    public boolean wantToPlay(Player player) throws RemoteException;
    public boolean isReady() throws RemoteException;
    public Player getPlayer1() throws RemoteException;
    public void setPlayingPlayer1(boolean value) throws RemoteException;
    public void setPlayingPlayer2(boolean value) throws RemoteException;
    public boolean isPlayingPlayer1() throws RemoteException;
    public boolean isPlayingPlayer2() throws RemoteException;
    public boolean shoot(Player player, Position position) throws RemoteException;
    
    public void setLastShootP1(Position position) throws RemoteException;
    public void setLastShootP2(Position position) throws RemoteException;
    public Position gettLastShootP1() throws RemoteException;
    public Position gettLastShootP2() throws RemoteException;
    public void giveUp() throws RemoteException;
    public boolean getgiveUp() throws RemoteException;
    
}
