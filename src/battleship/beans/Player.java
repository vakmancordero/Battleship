package battleship.beans;

import javafx.scene.layout.GridPane;

/**
 *
 * @author VakSF
 */
public class Player {
    
    private String name;
    private GridPane gameBoard;
    
    public Player() {
        
    }
    
    public Player(String name, GridPane gameBoard) {
        this.name = name;
        this.gameBoard = gameBoard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GridPane getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GridPane gameBoard) {
        this.gameBoard = gameBoard;
    }
    
    @Override
    public String toString() {
        return this.name + "\n\n" + this.gameBoard.getChildren().toString();
    }
    
}
