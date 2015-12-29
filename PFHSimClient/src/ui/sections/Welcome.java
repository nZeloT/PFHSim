package ui.sections;

import javafx.scene.layout.HBox;
import ui.abstraction.Container;

public class Welcome extends Container<HBox> {
	
	public Welcome() {
		load("/ui/fxml/Welcome.fxml");
	}
	
}
