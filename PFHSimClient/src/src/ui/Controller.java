package src.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable{
	@FXML
	VBox MoneyMenu;
	@FXML
	ToggleGroup MenuGroup;
	
	public Controller(){
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// TODO Auto-generated method stub
		
	}
	@FXML
	private void handleToggleButtonAction(ActionEvent event) {
		System.out.println(event.getSource());
	}
	
	

}
