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
import javafx.scene.Node;

import battleship.beans.ElementButton;
import battleship.beans.Player;
import battleship.beans.Position;

import battleship.rmi.Client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import javafx.scene.control.Label;

/**
 *
 * @author VakSF
 */
public class BattleshipController implements Initializable {
    
    @FXML
    private GridPane gameBoardGrid, gameBoardTarget;
    
    @FXML
    public Label lblEstado, lblPlayer, lblScore;
    
    public static final int MAX_ELEMENTS = 8;
    private int counter;
    private Client client;
    private Player player;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initGameBoard();
        this.client = new Client();
        this.counter = 0;
        
        this.player = new Player();
        this.player.setName(new Date().toString());
        lblScore.setText("0");
    }
    
    private void initGameBoard() {
        
        ObservableList<ColumnConstraints> columns = 
                this.gameBoardGrid.getColumnConstraints();
        
        ObservableList<RowConstraints> rows = 
                this.gameBoardGrid.getRowConstraints();
        
        for (int i = 0; i < columns.size(); i++) {
            
            for (int j = 0; j < rows.size(); j++) {
                
                ElementButton element = new ElementButton("empty");
                
                ElementButton elementTarget = new ElementButton("empty");
                
                this.gameBoardGrid.add(element, i, j);
                this.gameBoardTarget.add(elementTarget, i, j);
                
                elementTarget.getElementButton().setOnAction((event) -> {
                    
                    try {
                        
                        this.shootSpace(event);
                        
                    } catch (RemoteException ex) {
                        
                    }
                    
                });
                
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
    
    private void shootSpace(ActionEvent event) throws RemoteException {
        
        ElementButton element = (ElementButton) ((Button) event.getSource()).getParent();
        
        Integer rowIndex = GridPane.getRowIndex(element);
        Integer columnIndex = GridPane.getColumnIndex(element);
        
        Position position = new Position(rowIndex, columnIndex);
        
        boolean shoot = this.client.shoot(player, position);
        
        if (shoot) {
            
                //Aumento de puntos
                player.addPoint();
                lblScore.setText(""+player.getPoint());

                //Consulta de puntos para saber si se rinde

                if(player.getPoint()==8){
                    client.giveUp();
                    player.setLoser();
                }
                
                            
            element.getElementButton().setStyle("-fx-background-color: yellow;");
            
        } else {
            
            element.getElementButton().setStyle("-fx-background-color: red;");
            
        }
        
        if (player.getName().equals(client.getPlayer1().getName())) {   
                    //Importante.. cambio de contexto
                    System.out.println("Le toca jugar aljugador 2");
                    client.setPlayingPlayer1(false);
                    client.setPlayingPlayer2(true);
                    this.gameBoardTarget.setDisable(true);
                    lblEstado.setText("Esperando");
                    
                }else{
            
                    System.out.println("Le toca jugar al jugador 1");
                    client.setPlayingPlayer1(true);
                    client.setPlayingPlayer2(false);
                    this.gameBoardTarget.setDisable(true);
                    lblEstado.setText("Esperando");
                }
        
    }
    
    private void selectSpace(ActionEvent event) throws RemoteException {
        
        ElementButton element = (ElementButton) ((Button) event.getSource()).getParent();
        
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
                    
                    ObservableList<Node> children = this.gameBoardGrid.getChildren();
                    
                    ArrayList<Position> positions = new ArrayList<>();
                    
                    for (Node node : children) {
                        
                        String simpleName = node.getClass().getSimpleName();
                        
                        if (simpleName.equals("ElementButton")) {
                            
                            ElementButton elementButton = (ElementButton) node;
                            
                            if (elementButton.isSelected()) {
                                
                                Integer rowIndex = GridPane.getRowIndex(node);
                                Integer columnIndex = GridPane.getColumnIndex(node);

                                positions.add(new Position(rowIndex, columnIndex));
                                
                            }
                            
                            
                        }
                        
                    }
                    
                    System.out.println("Comenzando el juego");
                    
                    this.player.setPositions(positions);
                    
                    boolean wantToPlay = this.client.wantToPlay(player);
                    
                    if (wantToPlay) {
                        
                        System.out.println("Has sido logeado");
                        
                        new Thread(new Listener()).start();
                        
                    } else {
                        
                        System.out.println("El servidor está ocupado");
                        
                    }
                    
                }
                
            }
            
        }
        
    }
    
    private void blockGameBoard(boolean value) {
        this.gameBoardGrid.setDisable(value);
    }
    
    private void start() throws RemoteException {
        
        Player player1 = this.client.getPlayer1();
        
        if (player1.getName().equals(this.player.getName())) {
            
            System.out.println("Soy el primero: " + this.player.getName());
            lblEstado.setText("Jugando");
            lblPlayer.setText(this.player.getName());
            
            this.blockGameBoard(true);
            
            this.client.setPlayingPlayer1(true);
            
        }else{
            lblEstado.setText("Esperando");
            lblPlayer.setText(this.player.getName());
        }
        
        new Thread(new TurnListener()).start();
        
        new Thread(new Repaint()).start();
        
    }
    
    private void shoot() throws RemoteException {
        this.gameBoardTarget.setDisable(false);
    }
    
    class Listener implements Runnable {
        
        private boolean running = true;
        
        @Override
        public void run() {
            
            while (this.running) {
                
                try {
                    
                    if (client.isReady()) {
                        
                        start();
                        
                        break;
                        
                    }
                    
                    Thread.sleep(1000);
                    
                } catch (InterruptedException | RemoteException ex) {
                    
                }
                
            }
            
        }

        public void setIsRunning(boolean value) {
            this.running = value;
        }
        
    }
    
    class TurnListener implements Runnable {

        private boolean running = true;
        
        @Override
        public void run() {
            
            while (this.running) {
                
                try {
                    
                    //System.out.println("Tengo " + player.getPoint() + " puntos");
                    
                    if (player.getName().equals(client.getPlayer1().getName())) {
                        
                        if (client.isPlayingPlayer1()) {
                            System.out.println("Estoy jugando 111");
                            lblEstado.setText("Jugando");
                            client.setPlayingPlayer1(false);
                            
                            shoot();
                            
                            if(client.getgiveUp()){
                                System.out.println("Gano player 1");
                            }
                            
                        }
                        
                    } else {
                        System.out.println("¿Puedo jugar player 2?" + client.isPlayingPlayer2());
                        
                        if (client.isPlayingPlayer2()) {
                            System.out.println("Estoy jugando 222");
                            client.setPlayingPlayer2(false);
                            lblEstado.setText("Jugando");
                            
                            shoot();
                            
                            if(client.getgiveUp()){
                                System.out.println("Gano player 2");
                            }
                            
                        }
                        
                    }
                    
                    Thread.sleep(1000);
                    
                } catch (InterruptedException | RemoteException ex) {
                    
                }
                
            }
            
        }

        public void setIsRunning(boolean value) {
            this.running = value;
        }
        
    }
    
    class Repaint implements Runnable {

        private boolean running = true;
        
        @Override
        public void run() {
            
            while (this.running) {
                
                try {
                    
                    try {
                            
                        if (player.getName().equals(client.getPlayer1().getName())) {    

                            Position positionP2 = client.gettLastShootP2();

                            Node anchorPane = this.getNodeFromGridPane(
                                    gameBoardGrid, 
                                    positionP2.getPosX(), positionP2.getPosY()
                            );

                            ElementButton elementButton = (ElementButton) anchorPane;

                            Button button = elementButton.getElementButton();

                            button.setStyle("-fx-background-color: orange;");
                            

                        } else {
                               
                            Position positionP1 = client.gettLastShootP1();

                            Node anchorPane = this.getNodeFromGridPane(
                                    gameBoardGrid, 
                                    positionP1.getPosX(), positionP1.getPosY()
                            );

                            ElementButton elementButton = (ElementButton) anchorPane;

                            Button button = elementButton.getElementButton();

                            button.setStyle("-fx-background-color: sky;");

                        }

                        Thread.sleep(1000);
                        
                    } catch (NullPointerException e) {
                        
                    }
                    
                } catch (InterruptedException | RemoteException ex) {
                    
                }
                
            }
            
        }
        
        private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                    return node;
                }
            }
            return null;
        }
        
        public void setIsRunning(boolean value) {
            this.running = value;
        }
        
    }
    
}