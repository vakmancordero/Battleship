package battleship.beans;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author VakSF
 */
public class ElementButton extends AnchorPane {
    
    private final Button elementButton;
    
    private boolean selected;
    
    public ElementButton(String name) {
        
        super();
        
        this.elementButton = new Button(name);
        this.elementButton.setOpacity(0.5);
        AnchorPane.setTopAnchor(this.elementButton, 0.0);
        AnchorPane.setBottomAnchor(this.elementButton, 0.0);
        AnchorPane.setLeftAnchor(this.elementButton, 0.0);
        AnchorPane.setRightAnchor(this.elementButton, 0.0);
        
        super.getChildren().add(this.elementButton);
        
    }

    public Button getElementButton() {
        return this.elementButton;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
