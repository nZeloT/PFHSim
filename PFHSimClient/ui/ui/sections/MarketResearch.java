package ui.sections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import sim.simulation.sales.Offer;
import ui.abstraction.Container;
import ui.abstraction.UISection;

public class MarketResearch extends Container<VBox> implements UISection {

	private @FXML ListView<String> lvent;
	private @FXML TableView<Offer> offertable;
	private @FXML TableColumn<Offer, String> colht;
	private @FXML TableColumn<Offer, Number> colprice;
	private @FXML TableColumn<Offer, Number> colsales;

	private HashMap<String, List<Offer>> offermap;

	public MarketResearch() {

		offermap = new HashMap<>();
		load("/ui/fxml/MarketResearch.fxml");
	}

	public void initialize() {
		ObservableList<String> ents = FXCollections.observableArrayList(offermap.keySet());
		lvent.setItems(ents);
		for (int i = 0; i < offermap.size(); i++) {
			lvent.getSelectionModel().select(i);
			lvent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					displayOffers(newValue);
				}
			});

		}

	}

	public void displayOffers(String ename) {
		List<Offer> offerlist = offermap.get(ename);
		if (offerlist==null) {
			offerlist = new ArrayList<Offer>();
		}
		ObservableList<Offer> data = FXCollections.observableArrayList(offerlist);

		colht.setCellValueFactory(df -> {
			return new ReadOnlyStringWrapper((String) (df.getValue().getHousetype().name()));
		});
		colprice.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getPrice());
		});
		colsales.setCellValueFactory(df -> {
			return new ReadOnlyIntegerWrapper(df.getValue().getNumberOfPurchases());
		});
		offertable.setItems(data);
		offertable.refresh();
	}

	@Override
	public void update() {
		initialize();
	}

	@Override
	public void changeTab() {
		//nothing changed on tab switch
	}

	public void updateOfferMap(HashMap<String, List<Offer>> map) {
		this.offermap = map;
	}

}
