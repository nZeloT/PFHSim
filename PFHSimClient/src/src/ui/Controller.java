package src.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Controller implements Initializable{
	@FXML
	VBox MoneyMenu;
	
	@FXML
	Rectangle Account;
	@FXML
	Rectangle Balance;
	public Controller(){
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Account.widthProperty().bind(MoneyMenu.widthProperty().subtract(40));
		Balance.widthProperty().bind(MoneyMenu.widthProperty().subtract(40));
		// TODO Auto-generated method stub
		
	}
	

}
