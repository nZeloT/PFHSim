package src.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Line;

public class Procurement implements Initializable{
	@FXML
	Line Line1;

	public Procurement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(Line1);
		//Line1.endXProperty().bind(Line1.getScene().widthProperty().subtract(20));
		// TODO Auto-generated method stub
		
	}
	@FXML
	private void handleBuyButton(ActionEvent event) {
		System.out.println("button Pressed");
	}

}
