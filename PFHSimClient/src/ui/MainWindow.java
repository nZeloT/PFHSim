package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
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
import ui.sections.production.Production;

public class MainWindow extends Container<SplitPane>{

	private @FXML LineChart<Integer, Integer> moneyChart;
	private @FXML Label lblMoney;
	
	private @FXML Label lblProduction;
	private @FXML Label lblHR;
	private @FXML Label lblWarehouse;
	private @FXML Label lblInterests;

	private @FXML Button btnGo;

	private @FXML TabPane stack;
	private @FXML StackPane root;

	private @FXML ProgressIndicator roundTripProgress;

	private Enterprise ent;

	private List<UISection> sections;
	
	private XYChart.Series<Integer, Integer> series;
	private List<Integer> cashBuffer;

	private Callback<List<EnterpriseException>, Boolean> roundTripProcessor;
	private Welcome welcomePage;
	
	private Timer timer;

	public MainWindow(Enterprise e, Callback<List<EnterpriseException>, Boolean> roundTripProcessor){
		this.ent = e;
		this.ent.getBankAccount().setCashChanged(this::onMoneyChanged);
		this.roundTripProcessor = roundTripProcessor;
		this.timer = new Timer("Timer");
		this.series = new XYChart.Series<Integer, Integer>();
		this.cashBuffer = new ArrayList<Integer>();
		load("/ui/fxml/MainWindow.fxml");
	}

	public void initialize() {
		Production pro = new Production(ent, this::updateMoneyLabels);
		Procurement p = new Procurement(ent);
		HRPane hrp = new HRPane(ent, this::updateMoneyLabels);
		Warehouse w = new Warehouse(ent);
		RnD r = new RnD(ent);
		OfferOverviewController o = new OfferOverviewController(ent);

		welcomePage = new Welcome();

		sections = new ArrayList<>();
		sections.add(p);
		sections.add(pro);
		sections.add(hrp);
		sections.add(w);
		sections.add(r);
		sections.add(o);

		stack.getTabs().add(new Tab("Welcome", welcomePage.getContainer()));
		stack.getTabs().add(new Tab("Procurement", p.getContainer()));
		stack.getTabs().add(new Tab("Production", pro.getContainer()));
		stack.getTabs().add(new Tab("HR", hrp));
		stack.getTabs().add(new Tab("Warehouse", w.getContainer()));
		stack.getTabs().add(new Tab("Research", r.getContainer()));
		stack.getTabs().add(new Tab("Offer Catalog", o.getContainer()));

		stack.getSelectionModel().select(0);
		stack.getSelectionModel().selectedIndexProperty().addListener(this::switchStackPage);

		root.getChildren().get(1).setVisible(false);
		root.getChildren().get(2).setVisible(false);
		roundTripProgress.setProgress(-1);
		
		moneyChart.getData().add(series);

		onMoneyChanged(0, ent.getBankAccount().getCash());
		sheduleTimer(true);
	}

	private void switchStackPage(ObservableValue<? extends Number> obs, Number oldValue, Number newValue){
		int newInt = newValue.intValue();
		if(newInt > 0) // not the welcome page
			sections.get(newInt - 1).update();
	}

	@FXML
	private void nextRound(ActionEvent event){
		root.getChildren().get(1).setVisible(true);
		timer.cancel();
		timer = new Timer("Timer");
		
		//to prevent UI freezes utilise a new thread :D
		new Thread(
				() -> {
					Platform.runLater(() -> btnGo.setText("Waiting"));
					
					List<EnterpriseException> msgStore = new ArrayList<>();
					boolean gameEnded = roundTripProcessor.call(msgStore);

					//make sure all the UI stuff is then done on the javafx application thread
					Platform.runLater(() -> prepareNextRound(new ArrayList<>(msgStore), gameEnded));
				}
		).start();
		
	}

	private void onMoneyChanged(int oldValue, int newValue){
		Platform.runLater(this::updateMoneyLabels);
	}
	
	private void updateMoneyLabels(){
		lblMoney.setText("" + ent.getBankAccount().getCash());
		
		lblHR.setText("-" + ent.getHR().getOverallEmployeeCosts() + " €");
		lblProduction.setText("-" + (ent.getProductionHouse().getCosts() + ent.getProductionHouse().getMachineCosts()) + " €");
		lblWarehouse.setText("-" + ent.getWarehouse().getCosts() + " €");
		lblInterests.setText("" + ent.getBankAccount().getExpectedInterests() + " €");
	}

	private void prepareNextRound(List<EnterpriseException> msg, boolean gameEnded){
		if(!gameEnded){
			cashBuffer.add(ent.getBankAccount().getCash());
			if(cashBuffer.size() == 11)
				cashBuffer.remove(0);
			
			series.getData().clear();
			for (int i = 0; i < cashBuffer.size(); i++) {
				series.getData().add(new XYChart.Data<Integer, Integer>(i, cashBuffer.get(i)));
			}
			
			welcomePage.setMessages(msg);
			sections.forEach(s -> s.update());
			stack.getSelectionModel().select(0);
			root.getChildren().get(1).setVisible(false);
			
			//start a timer which forces a end of the round after 2 minutes
			sheduleTimer(false);
		}else{
			root.getChildren().get(0).setVisible(false);
			root.getChildren().get(1).setVisible(false);
			root.getChildren().get(2).setVisible(true);
		}
	}
	
	private void sheduleTimer(boolean first){
		timer.scheduleAtFixedRate(new TimerTask() {
			private long time = TimeUnit.MINUTES.toMillis( first ? 4 : 2);
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
		}, TimeUnit.MINUTES.toMillis(first ? 4 : 2));
	}
	
	public void cancleTimer(){
		timer.cancel();
		timer = null;
	}

}
