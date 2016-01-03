package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
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
import sim.EnterpriseException;
import ui.abstraction.Container;
import ui.abstraction.UISection;
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

	private List<UISection> sections;

	private Callback<List<EnterpriseException>, Boolean> roundTripProcessor;
	private Welcome welcomePage;
	
	private Timer timer;

	public MainWindow(Enterprise e, Callback<List<EnterpriseException>, Boolean> roundTripProcessor){
		this.ent = e;
		this.ent.getBankAccount().setCashChanged(this::onMoneyChanged);
		this.roundTripProcessor = roundTripProcessor;
		this.timer = new Timer("Timer");
		load("/ui/fxml/MainWindow.fxml");
	}

	public void initialize() {
		Procurement p = new Procurement(ent);
		HRPane hrp = new HRPane(ent);
		Warehouse w = new Warehouse(ent);
		RnD r = new RnD(ent);
		OfferOverviewController o = new OfferOverviewController(ent);

		welcomePage = new Welcome();

		sections = new ArrayList<>();
		sections.add(p);
		//		sections.add(pro); TODO: add the production screen
		sections.add(hrp);
		sections.add(w);
		sections.add(r);
		sections.add(o);

		stack.getChildren().add(welcomePage.getContainer());
		stack.getChildren().add(p.getContainer());
		stack.getChildren().add(new Rectangle(150, 150)); //TODO: make production screen
		stack.getChildren().add(hrp);
		stack.getChildren().add(w.getContainer());
		stack.getChildren().add(r.getContainer());
		stack.getChildren().add(o.getContainer());

		for (Node n : stack.getChildren()) {
			n.setVisible(false);
		}
		stack.getChildren().get(0).setVisible(true);
		currentPage = STACK_WELCOME;

		root.getChildren().get(1).setVisible(false);
		root.getChildren().get(2).setVisible(false);
		roundTripProgress.setProgress(-1);

		onMoneyChanged(0, ent.getBankAccount().getCash());
		sheduleTimer();
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
			System.err.println("Error - did not recognize Stack Page");
			break;
		}
	}

	@FXML
	private void nextRound(ActionEvent event){
		root.getChildren().get(1).setVisible(true);
		timer.cancel();
		timer = new Timer("Timer");

		//to prevent UI freezes utilise a new thread :D
		new Thread(
				() -> {
					List<EnterpriseException> msgStore = new ArrayList<>();
					boolean gameEnded = roundTripProcessor.call(msgStore);

					//make sure all the UI stuff is then done on the javafx application thread
					Platform.runLater(() -> prepareNextRound(new ArrayList<>(msgStore), gameEnded));
				}
		).start();
		
	}

	private void onMoneyChanged(int oldValue, int newValue){
		Platform.runLater(() -> lblMoney.setText("" + newValue));
	}

	private void prepareNextRound(List<EnterpriseException> msg, boolean gameEnded){
		if(!gameEnded){
			welcomePage.setMessages(msg);
			switchStackPage(STACK_WELCOME);
			sections.forEach(s -> s.update());
			root.getChildren().get(1).setVisible(false);
			
			//start a timer which forces a end of the round after 2 minutes
			sheduleTimer();
		}else{
			root.getChildren().get(0).setVisible(false);
			root.getChildren().get(1).setVisible(false);
			root.getChildren().get(2).setVisible(true);
		}
	}
	
	private void sheduleTimer(){
		timer.scheduleAtFixedRate(new TimerTask() {
			private long time = TimeUnit.MINUTES.toMillis(2);
			@Override
			public void run() {
				time -= TimeUnit.SECONDS.toMillis(1);
				long second = (time / 1000) % 60;
				long minute = (time / (1000 * 60)) % 60;

				String format = String.format("%02d:%02d", minute, second);
				Platform.runLater(() -> btnGo.setText(format));
			}
		}, 0, TimeUnit.SECONDS.toMillis(1));
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				nextRound(null);
			}
		}, TimeUnit.MINUTES.toMillis(2));
	}
	
	public void cancleTimer(){
		timer.cancel();
		timer = null;
	}

}
