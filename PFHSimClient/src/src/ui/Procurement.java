package src.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Procurement{
//	@FXML
//	Line Line1;
	
	@FXML
	private TextField textWood;

	public Procurement() {
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		System.out.println(Line1);
		//Line1.endXProperty().bind(Line1.getScene().widthProperty().subtract(20));
		// TODO Auto-generated method stub
		
//	}
	
	@FXML
	private void handleBuyButton(ActionEvent event) {
		System.out.println("button Pressed" + textWood.getText());
	}

}
