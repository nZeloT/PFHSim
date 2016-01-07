package ui.abstraction;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public abstract class Container<T extends Node> {
	
	private T container;
	
	protected final void load(String path){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setController(this);
        
        try{
            container = fxmlLoader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
	}
	
	public T getContainer() {
		return container;
	}

}
