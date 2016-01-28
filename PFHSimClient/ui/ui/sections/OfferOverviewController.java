package ui.sections;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.abstraction.Tupel;
import sim.hr.EmployeeType;
import sim.procurement.ResourceType;
import sim.production.PFHouseType;
import sim.production.WallType;
import sim.simulation.sales.Offer;
import ui.abstraction.Container;
import ui.abstraction.UISection;
import ui.abstraction.Utils;

public class OfferOverviewController extends Container<VBox> implements UISection {

	private @FXML HBox offerdetails1;
	private @FXML VBox offerdetails2;

	private @FXML Label title;
	private @FXML Label choosehousetypetext;

	private @FXML TextField selection_lightweight;
	private @FXML TextField selection_lightweightplus;
	private @FXML TextField selection_massive;
	private @FXML TextField selection_massiveplus;
	private @FXML TextField selection_panorama;

	private @FXML Label label_ncwall1;
	private @FXML Label label_ncwall2;
	private @FXML Label label_resource1;
	private @FXML Label label_resource2;
	private @FXML Label req_cwall;
	private @FXML Label req_ncwall1;
	private @FXML Label req_ncwall2;
	private @FXML Label req_resource1;
	private @FXML Label req_resource2;
	private @FXML Label req_employees;

	private @FXML Label available_lightweight;
	private @FXML Label available_lightweightplus;
	private @FXML Label available_massive;
	private @FXML Label available_massiveplus;
	private @FXML Label available_panorama;
	private @FXML Label available_ncwall1;
	private @FXML Label available_ncwall2;
	private @FXML Label available_resource1;
	private @FXML Label available_resource2;
	private @FXML Label available_employees;

	private @FXML Label duration;
	private @FXML Label varcost;
	private @FXML Label fixcost;
	private @FXML Label contributionmargin;
	private @FXML Label quality;
	private @FXML Label maxquality;
	private @FXML Label maxproducable;

	private @FXML TextField productionlimit;
	private @FXML TextField sum;

	private @FXML Label rb_lightweight;
	private @FXML Label rb_lightweightplus;
	private @FXML Label rb_massive;
	private @FXML Label rb_massiveplus;
	private @FXML Label rb_panorama;
	private @FXML ChoiceBox<String> choosehousetype;

	private @FXML Button btn_save;
	private @FXML Button btn_createnewoffer;
	private @FXML Button btn_loadofferdetails;
	private @FXML Button btn_deleteoffer;

	private @FXML ListView<String> offerlist;
	
	private @FXML ScrollPane scrollpane;

	private Offer selectedOffer = null;
	private List<Offer> offers = null;
	PFHouseType selectedType = null;

	private boolean showingExistingOffer = false;
	private Enterprise ent;

	public OfferOverviewController(Enterprise e) {
		this.ent = e;
		load("/ui/fxml/OfferOverview.fxml");
	}

	public void initialize() {

		btn_deleteoffer.setDisable(true);

		/*
		 * offerlist.getSelectionModel().selectedItemProperty().addListener(new
		 * ChangeListener<String>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends String>
		 * observable, String oldValue, String newValue) { load(); } });
		 */
		offerlist.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				load();
			}
		});

		// Initialize the offer-viewlist.
		refreshOfferList();

		offerlist.getSelectionModel().select(0);

		load();

		choosehousetype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null)
					return;
				if (newValue.equals(""))
					return;

				offerdetails1.setVisible(true);
				offerdetails2.setVisible(true);

				PFHouseType[] types = PFHouseType.values();
				for (PFHouseType tmp : types) {
					if (tmp.toString().equals(newValue)) {
						selectedType = tmp;
						break;
					}
				}

				WallType[] walltypes = selectedType.getRequiredWallTypes();
				int[] walltypes_count = selectedType.getWallCounts();

				label_ncwall1.setVisible(false);
				req_ncwall1.setVisible(false);
				available_ncwall1.setVisible(false);
				label_ncwall2.setVisible(false);
				req_ncwall2.setVisible(false);
				available_ncwall2.setVisible(false);
				req_cwall.setText("0");

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
							available_ncwall1.setText("" + ent.getWarehouse().getStoredAmount(walltypes[i]));
							non_customizable_position++;
						} else if (non_customizable_position == 1) {
							label_ncwall2.setVisible(true);
							req_ncwall2.setVisible(true);
							available_ncwall2.setVisible(true);
							label_ncwall2.setText(walltypes[i].toString());
							req_ncwall2.setText(walltypes_count[i] + "");
							available_ncwall2.setText("" + ent.getWarehouse().getStoredAmount(walltypes[i]));
							non_customizable_position++;
						}
					}
				}

				// the wall-values for the specification of general-walls need
				// to be
				// set.
				available_lightweight
						.setText("" + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
				available_lightweightplus
						.setText("" + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));
				available_massive.setText("" + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
				available_massiveplus
						.setText("" + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION));
				available_panorama.setText("" + ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));

				selection_lightweight.setText("");
				selection_lightweightplus.setText("");
				selection_massive.setText("");
				selection_massiveplus.setText("");
				selection_panorama.setText("");
				// now set the required and available number of resources.

				ResourceType[] resourcetypes = selectedType.getRequiredResourceTypes();
				int[] resourcetypes_count = selectedType.getResourceCounts();

				// label_ncwall1.setVisible(false);
				req_resource1.setVisible(false);
				available_resource1.setVisible(false);
				// label_ncwall2.setVisible(false);
				req_resource2.setVisible(false);
				available_resource2.setVisible(false);

				non_customizable_position = 0;
				for (int i = 0; i < resourcetypes.length; i++) {
					if (non_customizable_position == 0) {
						label_resource1.setVisible(true);
						req_resource1.setVisible(true);
						available_resource1.setVisible(true);
						label_resource1.setText(resourcetypes[i].toString());
						req_resource1.setText(resourcetypes_count[i] + "");
						available_resource1.setText("" + ent.getWarehouse().getStoredAmount(resourcetypes[i]));
						non_customizable_position++;
					} else if (non_customizable_position == 1) {
						label_resource2.setVisible(true);
						req_resource2.setVisible(true);
						available_resource2.setVisible(true);
						label_resource2.setText(resourcetypes[i].toString());
						req_resource2.setText(resourcetypes_count[i] + "");
						available_resource2.setText("" + ent.getWarehouse().getStoredAmount(resourcetypes[i]));
						non_customizable_position++;
					}
				}

				// now set the required and available number of employees.
				req_employees.setText("" + selectedType.getEmployeeCount());
				int available_emps = ent.getHR().getNumberOfUnassignedEmployees(EmployeeType.ASSEMBLER);
				available_employees.setText("" + available_emps);

				// set duration
				duration.setText("" + selectedType.getConstructionDuration());

				
				WallType[] wt = selectedType.getRequiredWallTypes();
				int[] wc = selectedType.getWallCounts();
				ArrayList<Tupel<WallType>> tmp_a = new ArrayList<>();
				for (int i = 0; i < wt.length; i++) {
					if (wt[i] != WallType.GENERAL) {
						tmp_a.add(new Tupel<WallType>(wt[i], wc[i]));
					}
				}
				@SuppressWarnings("unchecked")
				Tupel<WallType>[] tmp_wt = new Tupel[tmp_a.size()];
				for (int i = 0; i < tmp_wt.length; i++) {
					tmp_wt[i] = tmp_a.get(i);
				}
				Offer tmp_o = new Offer(0, selectedType, 0, tmp_wt);
				
				// set cost calculation figures.
				int var = ent.calculateVariableCosts(tmp_o);
				int fix = ent.calculateFixedCosts();
				varcost.setText("" + var);
				fixcost.setText("" + fix);
				sum.setText("0");
				contributionmargin.setText("" + (-var));

				// set quality.
				quality.setText("0");
				int maxqualityval = 0;
				walltypes = selectedType.getRequiredWallTypes();
				walltypes_count = selectedType.getWallCounts(); 
				for (int i = 0; i < walltypes.length; i++) {
					if (walltypes[i] == WallType.GENERAL) {
						maxqualityval += ent.getProductionHouse().getWallTypeWithMaxQuality().getQualityFactor()
								* walltypes_count[i];
					} else {
						maxqualityval += walltypes[i].getQualityFactor() * walltypes_count[i];
					}
				}
				maxqualityval += selectedType.getBaseQuality();

				maxquality.setText("(" + maxqualityval + ")");

				productionlimit.setText("" + ent.getMaxProducibleHouses(selectedType));
				maxproducable.setText("(" + ent.getMaxProducibleHouses(selectedType) + ")");

				// End of Offer Detail Screen initialization.
			}
		});
		btn_save.setDisable(true);
	}

	private void load() {

		// Initialize Offer Detail Screen:
		try {

			
			if (offerlist.getSelectionModel().getSelectedIndex()<0)
				return;
			
			btn_deleteoffer.setDisable(offers.size() == 0);
			selectedOffer = offers.get(offerlist.getSelectionModel().getSelectedIndex());

			System.out.println("" + showingExistingOffer);
			showingExistingOffer = true;

			title.setText("Offer details");

			btn_save.setDisable(true);

			choosehousetype.setDisable(true);

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
			req_cwall.setText("0");

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
						available_ncwall1.setText("" + ent.getWarehouse().getStoredAmount(walltypes[i]));
						non_customizable_position++;
					} else if (non_customizable_position == 1) {
						label_ncwall2.setVisible(true);
						req_ncwall2.setVisible(true);
						available_ncwall2.setVisible(true);
						label_ncwall2.setText(walltypes[i].toString());
						req_ncwall2.setText(walltypes_count[i] + "");
						available_ncwall2.setText("" + ent.getWarehouse().getStoredAmount(walltypes[i]));
						non_customizable_position++;
					}
				}
			}

			// the wall-values for the specification of general-walls need to be
			// set.
			available_lightweight.setText("" + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION));
			available_lightweightplus
					.setText("" + ent.getWarehouse().getStoredAmount(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS));
			available_massive.setText("" + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_LIGHT_CONSTRUCTION));
			available_massiveplus.setText("" + ent.getWarehouse().getStoredAmount(WallType.MASSIVE_PLUS_CONSTUCTION));
			available_panorama.setText("" + ent.getWarehouse().getStoredAmount(WallType.PANORAMA_WALL));

			selection_lightweight.setText("");
			selection_lightweightplus.setText("");
			selection_massive.setText("");
			selection_massiveplus.setText("");
			selection_panorama.setText("");
			Tupel<WallType>[] tmp = selectedOffer.getWalltype();
			for (Tupel<WallType> tupel : tmp) {
				if (tupel != null) {
					if (tupel.type == WallType.LIGHT_WEIGHT_CONSTRUCTION) {
						int var = 0;
						walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
						walltypes_count = selectedOffer.getHousetype().getWallCounts();
						for (int i = 0; i < walltypes.length; i++) {
							if (tupel.type == walltypes[i]) {
								var = walltypes_count[i];
							}
						}
						if ((tupel.count - var) == 0) {
							selection_lightweight.setText("");
						} else {
							selection_lightweight.setText("" + (tupel.count - var));
						}
					} else if (tupel.type == WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS) {
						int var = 0;
						walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
						walltypes_count = selectedOffer.getHousetype().getWallCounts();
						for (int i = 0; i < walltypes.length; i++) {
							if (tupel.type == walltypes[i]) {
								var = walltypes_count[i];
							}
						}
						if ((tupel.count - var) == 0) {
							selection_lightweightplus.setText("");
						} else {
							selection_lightweightplus.setText("" + (tupel.count - var));
						}
					} else if (tupel.type == WallType.MASSIVE_LIGHT_CONSTRUCTION) {
						int var = 0;
						walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
						walltypes_count = selectedOffer.getHousetype().getWallCounts();
						for (int i = 0; i < walltypes.length; i++) {
							if (tupel.type == walltypes[i]) {
								var = walltypes_count[i];
							}
						}
						if ((tupel.count - var) == 0) {
							selection_massive.setText("");
						} else {
							selection_massive.setText("" + (tupel.count - var));
						}
					} else if (tupel.type == WallType.MASSIVE_PLUS_CONSTUCTION) {
						int var = 0;
						walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
						walltypes_count = selectedOffer.getHousetype().getWallCounts();
						for (int i = 0; i < walltypes.length; i++) {
							if (tupel.type == walltypes[i]) {
								var = walltypes_count[i];
							}
						}
						if ((tupel.count - var) == 0) {
							selection_massiveplus.setText("");
						} else {
							selection_massiveplus.setText("" + (tupel.count - var));
						}
					} else if (tupel.type == WallType.PANORAMA_WALL) {
						int var = 0;
						walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
						walltypes_count = selectedOffer.getHousetype().getWallCounts();
						for (int i = 0; i < walltypes.length; i++) {
							if (tupel.type == walltypes[i]) {
								var = walltypes_count[i];
							}
						}
						if ((tupel.count - var) == 0) {
							selection_panorama.setText("");
						} else {
							selection_panorama.setText("" + (tupel.count - var));
						}
					}
				}

			}

			// now set the required and available number of resources.

			ResourceType[] resourcetypes = selectedOffer.getHousetype().getRequiredResourceTypes();
			int[] resourcetypes_count = selectedOffer.getHousetype().getResourceCounts();

			// label_ncwall1.setVisible(false);
			req_resource1.setVisible(false);
			available_resource1.setVisible(false);
			// label_ncwall2.setVisible(false);
			req_resource2.setVisible(false);
			available_resource2.setVisible(false);

			non_customizable_position = 0;
			for (int i = 0; i < resourcetypes.length; i++) {
				if (non_customizable_position == 0) {
					label_resource1.setVisible(true);
					req_resource1.setVisible(true);
					available_resource1.setVisible(true);
					label_resource1.setText(resourcetypes[i].toString());
					req_resource1.setText(resourcetypes_count[i] + "");
					available_resource1.setText("" + ent.getWarehouse().getStoredAmount(resourcetypes[i]));
					non_customizable_position++;
				} else if (non_customizable_position == 1) {
					label_resource2.setVisible(true);
					req_resource2.setVisible(true);
					available_resource2.setVisible(true);
					label_resource2.setText(resourcetypes[i].toString());
					req_resource2.setText(resourcetypes_count[i] + "");
					available_resource2.setText("" + ent.getWarehouse().getStoredAmount(resourcetypes[i]));
					non_customizable_position++;
				}
			}

			// now set the required and available number of employees.
			req_employees.setText("" + selectedOffer.getHousetype().getEmployeeCount());
			int available_emps = ent.getHR().getNumberOfUnassignedEmployees(EmployeeType.ASSEMBLER);
			available_employees.setText("" + available_emps);

			// set duration
			duration.setText("" + selectedOffer.getHousetype().getConstructionDuration());

			// set cost calculation figures.
			varcost.setText("" + ent.calculateVariableCosts(selectedOffer));
			fixcost.setText("" + ent.calculateFixedCosts());
			sum.setText("" + selectedOffer.getPrice());
			contributionmargin.setText("" + (selectedOffer.getPrice() - ent.calculateVariableCosts(selectedOffer)));

			// set quality.
			quality.setText("" + selectedOffer.getQuality());
			int maxqualityval = 0;
			walltypes = selectedOffer.getHousetype().getRequiredWallTypes();
			walltypes_count = selectedOffer.getHousetype().getWallCounts();
			for (int i = 0; i < walltypes.length; i++) {
				if (walltypes[i] == WallType.GENERAL) {
					maxqualityval += ent.getProductionHouse().getWallTypeWithMaxQuality().getQualityFactor()
						* walltypes_count[i];
				} else {
					maxqualityval += walltypes[i].getQualityFactor() * walltypes_count[i];
				}
			}
			maxqualityval += selectedOffer.getHousetype().getBaseQuality();
			maxquality.setText("(" + maxqualityval + ")");

			productionlimit.setText("" + selectedOffer.getProductionLimit());
			maxproducable.setText("(" + ent.getMaxProducibleHouses(selectedOffer) + ")");

		} catch (ArrayIndexOutOfBoundsException e4) {
			System.out.println("nothing selected.");
			offerdetails1.setVisible(false);
			offerdetails2.setVisible(false);
		}

		// End of Offer Detail Screen initialization.
	}

	private void refreshOfferList() {
		offers = ent.getSales().getOffers();

		ObservableList<String> offerstrings = FXCollections.observableArrayList();
		for (int i = 1; i <= offers.size(); i++) {
			offerstrings.add(i + ") " + offers.get(i - 1).getHousetype().toString());
		}

		offerlist.setItems(offerstrings);

	}

	@FXML
	private void refreshSum(KeyEvent e) {
		refreshSum();
	}

	private void refreshSum() {
		try {
			if (Integer.parseInt(sum.getText()) < 0) {
				throw new NumberFormatException();
			}
			int noOfSpecifiedWalls = 0;
			int tmp_quality = 0;

			List<Tupel<WallType>> walltype = new ArrayList<>();
			if (!selection_lightweight.getText().equals("")) {
				int tmp = Integer.parseInt(selection_lightweight.getText());
				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION, tmp));
				noOfSpecifiedWalls += tmp;
				tmp_quality += tmp * WallType.LIGHT_WEIGHT_CONSTRUCTION.getQualityFactor();
			}
			if (!selection_lightweightplus.getText().equals("")) {
				int tmp = Integer.parseInt(selection_lightweightplus.getText());
				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS, tmp));
				noOfSpecifiedWalls += tmp;
				tmp_quality += tmp * WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS.getQualityFactor();

			}
			if (!selection_massive.getText().equals("")) {
				int tmp = Integer.parseInt(selection_massive.getText());
				walltype.add(new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION, tmp));
				noOfSpecifiedWalls += tmp;
				tmp_quality += tmp * WallType.MASSIVE_LIGHT_CONSTRUCTION.getQualityFactor();
			}
			if (!selection_massiveplus.getText().equals("")) {
				int tmp = Integer.parseInt(selection_massiveplus.getText());
				walltype.add(new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION, tmp));
				noOfSpecifiedWalls += tmp;
				tmp_quality += tmp * WallType.MASSIVE_PLUS_CONSTUCTION.getQualityFactor();

			}
			if (!selection_panorama.getText().equals("")) {
				int tmp = Integer.parseInt(selection_panorama.getText());
				walltype.add(new Tupel<WallType>(WallType.PANORAMA_WALL, tmp));
				noOfSpecifiedWalls += tmp;
				tmp_quality += tmp * WallType.PANORAMA_WALL.getQualityFactor();
			}
			
			@SuppressWarnings("unused")
			PFHouseType tmp_type = null; 
			if (!showingExistingOffer) {
				tmp_type = selectedType;
			} else {
				tmp_type = selectedOffer.getHousetype();
			}
			
			WallType[] wt = selectedType.getRequiredWallTypes();
			int[] wc = selectedType.getWallCounts();
			for (int i = 0; i < wt.length; i++) {
				if (wt[i] != WallType.GENERAL) {
					walltype.add(new Tupel<WallType>(wt[i], wc[i]));
					tmp_quality += wt[i].getQualityFactor() * wc[i];
				}
			}
			
		
			@SuppressWarnings("unchecked")
			Tupel<WallType>[] tupelarray = new Tupel[walltype.size()];
			for (int i = 0; i < walltype.size(); i++) {
				tupelarray[i] = walltype.get(i);
			}

			if (!showingExistingOffer) {
				System.out.println("new offer selection" + "");
				selectedOffer = new Offer(Integer.parseInt(sum.getText()), selectedType, 1, tupelarray);
			}

			if (noOfSpecifiedWalls == selectedOffer.getHousetype().getNoOfWalls(WallType.GENERAL)) {
				btn_save.setDisable(false);
			} else {
				btn_save.setDisable(true);
			}

			contributionmargin
					.setText("" + (Integer.parseInt(sum.getText()) - ent.calculateVariableCosts(selectedOffer)));

			tmp_quality += selectedOffer.getHousetype().getBaseQuality();
			quality.setText("" + tmp_quality);

			maxproducable.setText("(" + ent.getMaxProducibleHouses(selectedOffer) + ")");

		} catch (

		NumberFormatException e2)

		{

		}

	}

	@FXML
	private void wallselectionChanged(KeyEvent e) {
		TextField src = (TextField) e.getSource();
		try {
			if (Integer.parseInt(src.getText()) < 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e2) {
			System.out.println("wrong number format");
		}
		btn_save.setDisable(false);

		varcost.setText("" + calculateVariableCost());
		this.refreshSum();
	}

	private int calculateVariableCost() {
		try {
			List<Tupel<WallType>> walltype = new ArrayList<>();
			if (!selection_lightweight.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION,
						Integer.parseInt(selection_lightweight.getText())));

			}
			if (!selection_lightweightplus.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS,
						Integer.parseInt(selection_lightweightplus.getText())));

			}
			if (!selection_massive.getText().equals("")) {
				walltype.add(new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION,
						Integer.parseInt(selection_massive.getText())));
			}
			if (!selection_massiveplus.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION,
						Integer.parseInt(selection_massiveplus.getText())));

			}
			if (!selection_panorama.getText().equals("")) {
				walltype.add(
						new Tupel<WallType>(WallType.PANORAMA_WALL, Integer.parseInt(selection_panorama.getText())));
			}
			
			@SuppressWarnings("unused")
			PFHouseType tmp_type = null; 
			if (!showingExistingOffer) {
				tmp_type = selectedType;
			} else {
				tmp_type = selectedOffer.getHousetype();
			}
			
			WallType[] wt = selectedType.getRequiredWallTypes();
			int[] wc = selectedType.getWallCounts();
			for (int i = 0; i < wt.length; i++) {
				if (wt[i] != WallType.GENERAL) {
					walltype.add(new Tupel<WallType>(wt[i], wc[i]));
				}
			}
			
			@SuppressWarnings("unchecked")
			Tupel<WallType>[] walltypearray = new Tupel[walltype.size()];
			for (int i = 0; i < walltype.size(); i++) {
				walltypearray[i] = walltype.get(i);
			}
			if (!showingExistingOffer) {
				selectedOffer = new Offer(Integer.parseInt(sum.getText()), selectedType, 1, walltypearray);

			}
			Offer pseudo_offer = new Offer(0, selectedOffer.getHousetype(), 0, walltypearray);
			return ent.calculateVariableCosts(pseudo_offer);
		} catch (NumberFormatException e) {
			this.btn_save.setDisable(true);
			return 0;
		}
	}

	@FXML
	private void onSave(ActionEvent e) {
		btn_save.setDisable(true);
		try {

			int noOfSpecifiedWalls = 0;

			List<Tupel<WallType>> walltype = new ArrayList<>();
			if (!selection_lightweight.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION,
						Integer.parseInt(selection_lightweight.getText())));
				noOfSpecifiedWalls += Integer.parseInt(selection_lightweight.getText());

			}
			if (!selection_lightweightplus.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.LIGHT_WEIGHT_CONSTRUCTION_PLUS,
						Integer.parseInt(selection_lightweightplus.getText())));
				noOfSpecifiedWalls += Integer.parseInt(selection_lightweightplus.getText());

			}
			if (!selection_massive.getText().equals("")) {
				walltype.add(new Tupel<WallType>(WallType.MASSIVE_LIGHT_CONSTRUCTION,
						Integer.parseInt(selection_massive.getText())));
				noOfSpecifiedWalls += Integer.parseInt(selection_massive.getText());
			}
			if (!selection_massiveplus.getText().equals("")) {

				walltype.add(new Tupel<WallType>(WallType.MASSIVE_PLUS_CONSTUCTION,
						Integer.parseInt(selection_massiveplus.getText())));
				noOfSpecifiedWalls += Integer.parseInt(selection_massiveplus.getText());

			}
			if (!selection_panorama.getText().equals("")) {
				walltype.add(
						new Tupel<WallType>(WallType.PANORAMA_WALL, Integer.parseInt(selection_panorama.getText())));
				noOfSpecifiedWalls += Integer.parseInt(selection_panorama.getText());
			}
			WallType[] wt = null;
			int[] wc = null;
			if (showingExistingOffer) {
				wt = selectedOffer.getHousetype().getRequiredWallTypes();
				wc = selectedOffer.getHousetype().getWallCounts();
			} else {
				wt = selectedType.getRequiredWallTypes();
				wc = selectedType.getWallCounts();
			}
			for (int i = 0; i < wc.length; i++) {
				if (wt[i] != WallType.GENERAL) {
					boolean found = false;
					for (int k = 0; k < walltype.size(); k++) {
						Tupel<WallType> tupel = walltype.get(k);
						if (tupel.type == wt[i]) {
							tupel.count += wc[i];
							found = true;
							walltype.set(k, tupel);
						}
					}
					if (found == false) {
						walltype.add(new Tupel<WallType>(wt[i], wc[i]));
					}
				}
			}
			// Just casting stuff for providing an array to the offer-method
			// setspecifiedwalltypes(...)
			@SuppressWarnings("unchecked")
			Tupel<WallType>[] tupelarray = new Tupel[walltype.size()];
			for (int i = 0; i < walltype.size(); i++) {
				tupelarray[i] = walltype.get(i);
			}
			if (showingExistingOffer) {
				if (noOfSpecifiedWalls == selectedOffer.getHousetype().getNoOfWalls(WallType.GENERAL)) {

					selectedOffer.setPrice(Integer.parseInt(sum.getText()));
					selectedOffer.setWalltypes(tupelarray);
					selectedOffer.setProductionLimit(Integer.parseInt(productionlimit.getText()));
					refreshOfferList();
					updateX();
					System.out.println("great, offer saved.");

				} else if (noOfSpecifiedWalls < selectedOffer.getHousetype().getNoOfWalls(WallType.GENERAL)) {

					Utils.showError("Please specify enough walls in your offer.");

				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("You cannot specify more walls than actually needed for this house type.");
					alert.showAndWait();

				}
			} else {
				if (noOfSpecifiedWalls == selectedType.getNoOfWalls(WallType.GENERAL)) {
					Offer o = new Offer(Integer.parseInt(sum.getText()), selectedType,
							Integer.parseInt(productionlimit.getText()), tupelarray);
					ent.getSales().addOffer(o);
					refreshOfferList();
					updateX();
					System.out.println("great, offer saved.");
				} else if (noOfSpecifiedWalls < selectedOffer.getHousetype().getNoOfWalls(WallType.GENERAL)) {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Please specify enough walls in your offer.");
					alert.showAndWait();

				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("You cannot specify more walls than actually needed for this house type.");
					alert.showAndWait();

				}
			}

		} catch (NumberFormatException e2) {
			Utils.showError("Please type in numbers!");
		}

	}

	@FXML
	private void productionLimitChanged(KeyEvent e) {
		this.refreshSum();
	}

	@FXML
	private void onCreate(ActionEvent e) {

		if ((ent.getSales().getNumberOfAllowedOffers() - ent.getSales().getOfferCount()) > 0) {


            scrollpane.setVvalue(0.0); 
			 
			title.setVisible(true);
			choosehousetypetext.setVisible(true);
			choosehousetype.setVisible(true);

			title.setText("New offer");
			btn_save.setDisable(true);

			choosehousetype.setDisable(false);

			ObservableList<String> housetypestring = FXCollections.observableArrayList();

			List<PFHouseType> researchedHousetypes = ent.getResearchedHouseTypes();

			for (PFHouseType pfHouseType : researchedHousetypes) {
				housetypestring.add(pfHouseType.toString());
			}
			choosehousetype.setItems(housetypestring);

			offerdetails1.setVisible(false);
			offerdetails2.setVisible(false);

			showingExistingOffer = false;

		} else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("You are not permitted to create further offers. Hire more sales-employees!");
			alert.showAndWait();
		}
	}

	@FXML
	private void deleteoffer(ActionEvent e) {
		try {
			if (ent.getSales().removeOffer(offerlist.getSelectionModel().getSelectedIndex()) != null) {
				refreshOfferList();
				btn_save.setDisable(true);
				update();
				System.out.println("Great, offer deleted.");
			} else {
				Utils.showError("Kein Angebot ausgewählt!");
			}

		} catch (NullPointerException e2) {
			System.out.println("no offer selected.");
		}
	}

	@Override
	public void update() {

		offerlist.getSelectionModel().select(0);
		if (offers.size() == 0) {
			btn_save.setDisable(true);
			title.setVisible(false);
			choosehousetype.setVisible(false);
			choosehousetypetext.setVisible(false);
			offerdetails1.setVisible(false);
			offerdetails2.setVisible(false);
		} else {
			load();
		}
	}

	public void updateX() {

		if (!showingExistingOffer)
			offerlist.getSelectionModel().select(offers.size() - 1);

		if (offers.size() == 0) {
			btn_save.setDisable(true);
			title.setVisible(false);
			choosehousetype.setVisible(false);
			choosehousetypetext.setVisible(false);
		}

		load();
	}

	@Override
	public void changeTab() {
		update();
	}

}
