package battleship.beans;

import java.io.Serializable;
import javafx.scene.layout.GridPane;

/**
 *
 * @author VakSF
 */
public class Player implements Serializable {
    
    private String name;
//    private GridPane gameBoard;
    
    public Player() {
        
    }
    
    public Player(String name) {
        this.name = name;
//        this.gameBoard = gameBoard;
    }
    
//    public Player(String name) {
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public GridPane getGameBoard() {
//        return gameBoard;
//    }

//    public void setGameBoard(GridPane gameBoard) {
//        this.gameBoard = gameBoard;
//    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
}
