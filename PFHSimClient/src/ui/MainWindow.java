package ui;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import sim.Enterprise;
import ui.abstraction.Container;
import ui.sections.OfferOverviewController;
import ui.sections.Procurement;
import ui.sections.RnD;
import ui.sections.Warehouse;
import ui.sections.Welcome;
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
	private @FXML StackPane root;

	private @FXML ProgressIndicator roundTripProgress;

	private Enterprise ent;
	private int currentPage;

	private Runnable roundTripProcessor;

	public MainWindow(Enterprise e, Runnable roundTripProcessor){
		this.ent = e;
		this.roundTripProcessor = roundTripProcessor;
		load("/ui/fxml/MainWindow.fxml");
	}

	public void initialize() {
		stack.getChildren().add(new Welcome().getContainer());
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

		root.getChildren().get(1).setVisible(false);
		roundTripProgress.setProgress(-1);
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
		root.getChildren().get(1).setVisible(true);

		MainWindow w = this;
		//to prevent UI freezes utilise a new thread :D
		new Thread(
				() -> {
					roundTripProcessor.run();
					//make sure all the UI stuff is then done on the javafx application thread
					Platform.runLater(w::prepareNextRound);
				}
		).start();

	}

	private void prepareNextRound(){
		root.getChildren().get(1).setVisible(false);
		switchStackPage(STACK_WELCOME);
	}

}
