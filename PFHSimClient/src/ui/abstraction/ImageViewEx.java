package ui.abstraction;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class ImageViewEx extends ImageView {
	
//	private Region r;
	
	public ImageViewEx(Region parent) {
//		this.r = parent;
		parent.widthProperty().addListener((ov, ol, ne) -> { setFitWidth(ne.doubleValue()); });
		parent.heightProperty().addListener((ov, old, ne) -> { setFitHeight(ne.doubleValue()); });
	}
}