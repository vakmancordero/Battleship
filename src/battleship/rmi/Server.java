package battleship.rmi;

import battleship.beans.Player;
import battleship.beans.Position;
import battleship.rmi.impl.Environment;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 *
 * @author VakSF
 */
public class Server extends UnicastRemoteObject implements Environment {
    
    private HashMap<String, Player> players;
    
    private Position lastShootP1, lastShootP2;
    
    public Server() throws RemoteException {
        super();
        
        this.players = new HashMap<>();
        
        this.players.put("player1", new Player());
        this.players.put("player2", new Player());
    }

    @Override
    public boolean wantToPlay(Player player) throws RemoteException {
        
        Player player1 = this.players.get("player1");
        
        if (!player1.isAlive()) {
            
            this.players.remove("player1");
            
            this.players.put("player1", player);
            
            this.players.get("player1").setAlive(true);
            
        } else {
            
            Player player2 = this.players.get("player2");
            
            if (!player2.isAlive()) {
            
                this.players.remove("player2");

                this.players.put("player2", player);
                
                this.players.get("player2").setAlive(true);
                
            } else {
                
                return false;
                
            }
            
        }
        
        return true;
    }

    @Override
    public boolean isReady() throws RemoteException {
        return this.players.get("player1").isAlive() && this.players.get("player2").isAlive();
    }

    @Override
    public Player getPlayer1() throws RemoteException {
        return this.players.get("player1");
    }

    @Override
    public void setPlayingPlayer1(boolean value) throws RemoteException {
        this.players.get("player1").setPlaying(value);
    }

    @Override
    public void setPlayingPlayer2(boolean value) throws RemoteException {
        this.players.get("player2").setPlaying(value);
    }

    @Override
    public boolean isPlayingPlayer1() throws RemoteException {
        return this.players.get("player1").isPlaying();
    }

    @Override
    public boolean isPlayingPlayer2() throws RemoteException {
        return this.players.get("player2").isPlaying();
    }

    @Override
    public boolean shoot(Player player, Position position) throws RemoteException {
        
        System.out.println(position.toString() + " << atacando");
        
        if (this.players.get("player1").getName().equals(player.getName())) {
            
            for (Position pos : this.players.get("player2").getPositions()) {
                
                System.out.println(pos.toString() + " << buscando");
                
                if (pos.getPosX() == position.getPosX() && pos.getPosY() == position.getPosY()) {
                    
                    return true;
                    
                }
            }
            
            this.setLastShootP1(position);
            
        } else {
            
            for (Position pos : this.players.get("player1").getPositions()) {
                
                System.out.println(pos.toString() + " << buscando");
                
                if (pos.getPosX() == position.getPosX() && pos.getPosY() == position.getPosY()) {
                    
                    return true;
                    
                }
            }
            
            this.setLastShootP2(position);
            
        }
        
        return false;
        
    }

    @Override
    public void setLastShootP1(Position position) throws RemoteException {
        this.lastShootP1 = position;
    }

    @Override
    public void setLastShootP2(Position position) throws RemoteException {
        this.lastShootP2 = position;
    }

    @Override
    public Position gettLastShootP1() throws RemoteException {
        return this.lastShootP1;
    }

    @Override
    public Position gettLastShootP2() throws RemoteException {
        return this.lastShootP2;
    }
    
}