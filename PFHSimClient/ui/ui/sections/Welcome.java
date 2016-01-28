package ui.sections;

import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import sim.EnterpriseException;
import ui.abstraction.Container;

public class Welcome extends Container<HBox> {
	
	private @FXML TableView<EnterpriseException> tableMessages;
	
	private @FXML TableColumn<EnterpriseException, String> colType;
	private @FXML TableColumn<EnterpriseException, String> colText;
	private @FXML TableColumn<EnterpriseException, Object> colOrigin;
	
	public Welcome() {
		load("/ui/fxml/Welcome.fxml");
	}
	
	public void initialize(){
		colType.setCellValueFactory(df -> {
			return new ReadOnlyStringWrapper("" + df.getValue().getCategorie());
		});
		colText.setCellValueFactory(df -> {
			return new ReadOnlyStringWrapper("" + df.getValue().getMessage());
		});
		colOrigin.setCellValueFactory(df -> {
			return new ReadOnlyObjectWrapper<Object>(df.getValue().getSource());
		});
		tableMessages.setVisible(false);
	}
	
	public void setMessages(List<EnterpriseException> msg){
		tableMessages.setItems(FXCollections.observableArrayList(msg));
		tableMessages.setVisible(!msg.isEmpty());
	}
	
}
