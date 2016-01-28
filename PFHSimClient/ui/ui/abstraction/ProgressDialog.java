package ui.abstraction;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressDialog extends Stage {
	
	private ProgressIndicator indi;
	
	public ProgressDialog() {
		super(StageStyle.UTILITY);
		indi = new ProgressIndicator(-1);
		setScene(new Scene(indi));
	}
	
	public void setProgress(double p){
		indi.setProgress(p);
	}
	
	public double getProgress(){
		return indi.getProgress();
	}
	
	public DoubleProperty progressProperty(){
		return indi.progressProperty();
	}
	
}
