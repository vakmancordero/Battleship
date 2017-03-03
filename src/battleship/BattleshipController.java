package battleship;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import battleship.beans.ElementButton;
import battleship.beans.Player;
import battleship.rmi.Client;
import java.rmi.RemoteException;

import java.util.Optional;

/**
 *
 * @author VakSF
 */
public class BattleshipController implements Initializable {
    
    @FXML
    private GridPane gameBoardGrid;
    
    public static final int MAX_ELEMENTS = 8;
    
    private int counter;
    
    private Client client;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initGameBoard();
        this.client = new Client();
        this.counter = 0;
    }
    
    private void initGameBoard() {
        
        ObservableList<ColumnConstraints> columns = 
                this.gameBoardGrid.getColumnConstraints();
        
        ObservableList<RowConstraints> rows = 
                this.gameBoardGrid.getRowConstraints();
        
        for (int i = 0; i < columns.size(); i++) {
            
            for (int j = 0; j < rows.size(); j++) {
                
                ElementButton element = new ElementButton("empty");
                
                this.gameBoardGrid.add(element, i, j);
                
                element.getElementButton().setOnAction((event) -> {
                    
                    try {
                        
                        this.selectSpace(event);
                        
                    } catch (RemoteException ex) {
                        
                        System.out.println("Error: " + ex.toString());
                        
                    }
                });
                
            }
            
        }
        
    }
    
    private void selectSpace(ActionEvent event) throws RemoteException {
        
        ElementButton element = (ElementButton) ((Button) event.getSource()).getParent();
        
        Integer rowIndex = GridPane.getRowIndex(element);
        Integer columnIndex = GridPane.getColumnIndex(element);
        
        Button button = element.getElementButton();
        
        if (element.isSelected()) {
            
            button.setText("empty");
            button.setStyle("");
            button.setFont(Font.font("System", FontWeight.NORMAL, 12));
            button.setTextFill(Paint.valueOf("black"));
            button.setOpacity(0.4);
            element.setSelected(false);
            
            this.counter--;
            
        } else {
            
            if (this.counter < MAX_ELEMENTS) {
                
                button.setText("ready");
                button.setStyle("-fx-background-color: #43A853; ");
                button.setFont(Font.font("System", FontWeight.BOLD, 12));
                button.setTextFill(Paint.valueOf("white"));
                button.setOpacity(0.8);
                element.setSelected(true);
                
                this.counter++;
                
            } else {
                
                Optional<ButtonType> option = new Alert(
                        Alert.AlertType.CONFIRMATION, 
                        "Juego listo! Desea comenzar ahora?"
                ).showAndWait();
                
                if (option.get() == ButtonType.OK) {
                    
                    System.out.println("Comenzando el juego");
                    
                    this.client.send(new Player("Arturo"));
                    
                }
                
            }
            
        }
        
        System.out.println(rowIndex + " : " + columnIndex);
        
    }
    
    private void blockGameBoard(boolean value) {
        this.gameBoardGrid.setDisable(value);
    }
    
}