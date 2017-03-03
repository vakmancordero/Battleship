package battleship.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author VakSF
 */
public class Player implements Serializable {
    
    private String name;
    private ArrayList<Position> positions, targetPositions;
    
    private boolean playing = false;
    private boolean alive = false;
    private boolean lose = false;
    
    private int point=0;

    public Player() {
        
    }
    
    public Player(String name, ArrayList<Position> positions) {
        this.name = name;
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Position> getPositions() {
        return positions;
    }
    
    public void setPositions(ArrayList<Position> positions) {
        this.positions = positions;
    }

    public ArrayList<Position> getTargetPositions() {
        return targetPositions;
    }

    public void setTargetPositions(ArrayList<Position> targetPositions) {
        this.targetPositions = targetPositions;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    
    public void addPoint(){
        this.point++;
    }
    
    public int getPoint(){
        return this.point;
    }
    
    public void setLoser(){
        this.lose=lose;
    }
    
    public boolean getLose(){
        return this.lose;
    }
    
    @Override
    public String toString() {
        return this.name + " : " + this.positions.toString();
    }
    
}
