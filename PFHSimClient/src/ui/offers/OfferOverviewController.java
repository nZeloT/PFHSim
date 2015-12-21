package ui.offers;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import sim.Enterprise;
import sim.TestUtils;
import sim.abstraction.Tupel;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;

public class OfferOverviewController {

	private @FXML Label title;

	private @FXML Label label_ncwall1;
	private @FXML Label label_ncwall2;
	private @FXML Label req_cwall;
	private @FXML Label req_ncwall1;
	private @FXML Label req_ncwall2;
	private @FXML Label req_rooftile;
	private @FXML Label req_wood;
	private @FXML Label req_employees;

	private @FXML Label available_lightweight;
	private @FXML Label available_lightweightplus;
	private @FXML Label available_massive;
	private @FXML Label available_massiveplus;
	private @FXML Label available_panorama;
	private @FXML Label available_ncwall1;
	private @FXML Label available_ncwall2;
	private @FXML Label available_rooftile;
	private @FXML Label available_wood;
	private @FXML Label available_employees;

	private @FXML Label duration;
	private @FXML Label varcosts;
	private @FXML Label fixcosts;
	private @FXML Label totalsum;
	private @FXML Label quality;
	private @FXML Label maxquality;
	private @FXML Label maxproducable;

	private @FXML TextField profit;

	private @FXML RadioButton rb_lightweight;
	private @FXML RadioButton rb_lightweightplus;
	private @FXML RadioButton rb_massive;
	private @FXML RadioButton rb_massiveplus;
	private @FXML RadioButton rb_panorama;
	private @FXML ChoiceBox<String> choosehousetype;

	private @FXML Button button_create;

	private @FXML ListView<String> offerlist;

	private Offer selectedOffer = null;
	private List<Offer> offers = null;

	public OfferOverviewController() {
	}

	public void initialize() {

		// General initialization for test purposes
		Enterprise e = TestUtils.initializeEnterprise();
		e.addOffer(new Offer(5000, 2, PFHouseType.COMFORT_HOUSE, 5,
				new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));
		e.addOffer(new Offer(5000, 2, PFHouseType.BUNGALOW, 5,
				new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, 5)));

		// Initialize the offer-viewlist.
		offers = e.getOffers();

		ObservableList<String> offerstrings = FXCollections.observableArrayList();
		for (Offer offer : offers) {
			offerstrings.add(offer.getHousetype().toString());
		}

		offerlist.setItems(offerstrings);

		// Initialize Offer Detail Screen:

		choosehousetype.setDisable(true);
		offerlist.getSelectionModel().select(0);

		selectedOffer = offers.get(offerlist.getSelectionModel().getSelectedIndex());
		ObservableList<String> housetypestring = FXCollections.observableArrayList();

		housetypestring.add(selectedOffer.getHousetype().toString());
		choosehousetype.setItems(housetypestring);
		choosehousetype.getSelectionModel().select(0);

		WallType[] walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
		int[] walltypes_count = selectedOffer.getHousetype().getWallCounts();

		label_ncwall1.setVisible(false);
		req_ncwall1.setVisible(false);
		available_ncwall1.setVisible(false);
		label_ncwall2.setVisible(false);
		req_ncwall2.setVisible(false);
		available_ncwall2.setVisible(false);

		// set the required and available number of walls.
		int non_customizable_position = 0;
		for (int i = 0; i < walltypes.length; i++) {
			if (walltypes[i] == WallType.GENERAL) {
				req_cwall.setText(walltypes_count[i] + "");
			} else {
				if (non_customizable_position == 0) {
					label_ncwall1.setVisible(true);
					req_ncwall1.setVisible(true);
					available_ncwall1.setVisible(true);
					label_ncwall1.setText(walltypes[i].toString());
					req_ncwall1.setText(walltypes_count[i] + "");
					available_ncwall1.setText("" + e.getWarehouse().getStoredAmount(walltypes[i]));
					non_customizable_position++;
				} else if (non_customizable_position == 1) {
					label_ncwall2.setVisible(true);
					req_ncwall2.setVisible(true);
					available_ncwall2.setVisible(true);
					label_ncwall2.setText(walltypes[i].toString());
					req_ncwall2.setText(walltypes_count[i] + "");
					available_ncwall2.setText("" + e.getWarehouse().getStoredAmount(walltypes[i]));
					non_customizable_position++;
				}
			}
		}

		// the available walls for the specification of general-walls need to be
		// set.
		available_lightweight.setText("" + e.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
		available_lightweightplus
				.setText("" + e.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));
		available_massive.setText("" + e.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
		available_massiveplus.setText("" + e.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));
		available_panorama.setText("" + e.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));

		
		
		
		// now set the required and available number of resources.

		ResourceType[] resourcetypes = selectedOffer.getHousetype().getRequiredResourceTypes();
		int[] resourcetypes_count = selectedOffer.getHousetype().getResourceCounts();

		// label_ncwall1.setVisible(false);
		req_rooftile.setVisible(false);
		available_rooftile.setVisible(false);
		// label_ncwall2.setVisible(false);
		req_wood.setVisible(false);
		available_wood.setVisible(false);

		non_customizable_position = 0;
		for (int i = 0; i < resourcetypes.length; i++) {
			if (non_customizable_position == 0) {
				// label_ncwall1.setVisible(true);
				req_rooftile.setVisible(true);
				available_rooftile.setVisible(true);
				// label_ncwall1.setText(walltypes[i].toString());
				req_rooftile.setText(resourcetypes_count[i] + "");
				available_rooftile.setText("" + e.getWarehouse().getStoredAmount(resourcetypes[i]));
				non_customizable_position++;
			} else if (non_customizable_position == 1) {
				// label_ncwall2.setVisible(true);
				req_wood.setVisible(true);
				available_wood.setVisible(true);
				// label_ncwall2.setText(walltypes[i].toString());
				req_wood.setText(resourcetypes_count[i] + "");
				available_wood.setText("" + e.getWarehouse().getStoredAmount(resourcetypes[i]));
				non_customizable_position++;
			}
		}

		
		
		
		
		// End of Offer Detail Screen initialization.

	}

	@FXML
	private void loadofferdetails(ActionEvent e) {
		System.out.println("loading");
	}

}
