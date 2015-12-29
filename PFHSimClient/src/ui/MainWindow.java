package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import sim.Enterprise;
import ui.abstraction.Container;
import ui.abstraction.ImageViewEx;
import ui.sections.OfferOverviewController;
import ui.sections.Procurement;
import ui.sections.RnD;
import ui.sections.Warehouse;
import ui.sections.hr.HRPane;

public class MainWindow extends Container<SplitPane>{
	
	private static final int STACK_WELCOME = 0;
	private static final int STACK_PROCUREMENT = 1;
	private static final int STACK_PRODUCTION = 2;
	private static final int STACK_HR = 3;
	private static final int STACK_WAREHOUSE = 4;
	private static final int STACK_PND = 5;
	private static final int STACK_HOUSECATALOG = 6;
	
	private @FXML ToggleGroup menuGroup;
	
	private @FXML LineChart<Integer, Integer> moneyChart;
	private @FXML LineChart<Integer, Integer> costChart;
	
	private @FXML Label lblCosts;
	private @FXML Label lblMoney;
	
	private @FXML Button btnGo;
	
	private @FXML StackPane stack;
	
	private Enterprise ent;
	private int currentPage;
	
	public MainWindow(Enterprise e){
		this.ent = e;
		load("/ui/fxml/MainWindow.fxml");
	}
	
	public void initialize() {
		ImageViewEx welcome = new ImageViewEx(stack);
		welcome.setImage(new Image(getClass().getResourceAsStream("/ui/res/logopfh.JPG")));
		
		stack.getChildren().add(new Rectangle(50, 50));
		stack.getChildren().add(new Procurement(ent).getContainer());
		stack.getChildren().add(new Rectangle(150, 150)); //TODO: make production screen
		stack.getChildren().add(new HRPane(ent.getHR()));
		stack.getChildren().add(new Warehouse(ent).getContainer());
		stack.getChildren().add(new RnD(ent).getContainer());
		stack.getChildren().add(new OfferOverviewController(ent).getContainer());
		
		for (Node n : stack.getChildren()) {
			n.setVisible(false);
		}
		stack.getChildren().get(0).setVisible(true);
		currentPage = STACK_WELCOME;
	}
	
	private void switchStackPage(int newPage){
		stack.getChildren().get(currentPage).setVisible(false);
		stack.getChildren().get(newPage).setVisible(true);
		currentPage = newPage;
	}
	
	@FXML
	private void switchTab(ActionEvent event) {
		ToggleButton src = (ToggleButton) event.getSource();
		switch (src.getText()) {
		case "Procurement":
			switchStackPage(STACK_PROCUREMENT);
			break;
		case "Production":
			switchStackPage(STACK_PRODUCTION);
			break;
		case "HR":
			switchStackPage(STACK_HR);
			break;
		case "Warehouse":
			switchStackPage(STACK_WAREHOUSE);
			break;
		case "P&D":
			switchStackPage(STACK_PND);
			break;
		case "House Catalog":
			switchStackPage(STACK_HOUSECATALOG);
			break;
		default:
			System.out.println("Error - did not recognize Stack Page");
			break;
		}
	}
	
	@FXML
	private void nextRound(ActionEvent event){
		System.out.println(event);
	}

}
