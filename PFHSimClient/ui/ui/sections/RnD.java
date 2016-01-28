package ui.sections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.hr.EmployeeType;
import sim.production.PFHouseType;
import ui.abstraction.Container;
import ui.abstraction.UISection;

public class RnD extends Container<VBox> implements UISection {

	private @FXML VBox root;
	private @FXML ProgressBar barbung;
	private @FXML ProgressBar barblock;
	private @FXML ProgressBar bareffic;
	private @FXML ProgressBar barmulti;
	private @FXML ProgressBar barcomf;
	private @FXML ProgressBar barvilla;
	private @FXML ProgressBar bartrend;

	private HashMap<PFHouseType, Button> buttons;
	private @FXML Button btnbungalow;
	private @FXML Button btnblock;
	private @FXML Button btneffic;
	private @FXML Button btnmulti;
	private @FXML Button btncomf;
	private @FXML Button btnvilla;
	private @FXML Button btntrend;

	private Label[] labels = new Label[14];
	private @FXML Label lblbungdur;
	private @FXML Label lblbungcost;
	private @FXML Label lblblockdur;
	private @FXML Label lblblockcost;
	private @FXML Label lblefficdur;
	private @FXML Label lblefficcost;
	private @FXML Label lblmultidur;
	private @FXML Label lblmulticost;
	private @FXML Label lblcomfdur;
	private @FXML Label lblcomfcost;
	private @FXML Label lblvilladur;
	private @FXML Label lblvillacost;
	private @FXML Label lbltrenddur;
	private @FXML Label lbltrendcost;
	private HashMap<PFHouseType, ProgressBar> map;

	private PFHouseType typeInResearch = null;
	private Enterprise e;

	public RnD(Enterprise ent) {
		this.e = ent;
		load("/ui/fxml/RnD.fxml");
		map = new HashMap<>();
		map.put(PFHouseType.BUNGALOW, barbung);
		map.put(PFHouseType.BLOCK_HOUSE, barblock);
		map.put(PFHouseType.EFFICIENCY_HOUSE, bareffic);
		map.put(PFHouseType.MULTI_FAMILY_HOUSE, barmulti);
		map.put(PFHouseType.COMFORT_HOUSE, barcomf);
		map.put(PFHouseType.CITY_VILLA, barvilla);
		map.put(PFHouseType.TRENDHOUSE, bartrend);
	}

	public void initialize() {
		labels[0] = lblbungdur;
		labels[1] = lblbungcost;
		labels[2] = lblblockdur;
		labels[3] = lblblockcost;
		labels[4] = lblefficdur;
		labels[5] = lblefficcost;
		labels[6] = lblmultidur;
		labels[7] = lblmulticost;
		labels[8] = lblcomfdur;
		labels[9] = lblcomfcost;
		labels[10] = lblvilladur;
		labels[11] = lblvillacost;
		labels[12] = lbltrenddur;
		labels[13] = lbltrendcost;

		buttons = new HashMap<>();
		buttons.put(PFHouseType.BUNGALOW, btnbungalow);
		buttons.put(PFHouseType.BLOCK_HOUSE, btnblock);
		buttons.put(PFHouseType.EFFICIENCY_HOUSE, btneffic);
		buttons.put(PFHouseType.MULTI_FAMILY_HOUSE, btnmulti);
		buttons.put(PFHouseType.COMFORT_HOUSE, btncomf);
		buttons.put(PFHouseType.CITY_VILLA, btnvilla);
		buttons.put(PFHouseType.TRENDHOUSE, btntrend);

		PFHouseType[] types = PFHouseType.values();
		for (int i = 0; i < types.length; i++) {
			labels[i * 2].setText("" + types[i].getResearchDuration());
			labels[i * 2 + 1].setText("" + types[i].getResearchCosts() + " €");
		}
	}

	@FXML
	private void researchHouse(ActionEvent e) {
		Button btn = (Button) e.getSource();

		switch (btn.getId()) {
		case "btnbungalow":
			startReseachProject(PFHouseType.BUNGALOW, btn);
			break;
		case "btnblock":
			startReseachProject(PFHouseType.BLOCK_HOUSE, btn);
			break;
		case "btneffic":
			startReseachProject(PFHouseType.EFFICIENCY_HOUSE, btn);
			break;
		case "btnmulti":
			startReseachProject(PFHouseType.MULTI_FAMILY_HOUSE, btn);
			break;
		case "btncomf":
			startReseachProject(PFHouseType.COMFORT_HOUSE, btn);
			break;
		case "btnvilla":
			startReseachProject(PFHouseType.CITY_VILLA, btn);
			break;
		case "btntrend":
			startReseachProject(PFHouseType.TRENDHOUSE, btn);
			break;
		default:
			// should never happen
			break;
		}
	}

	private void startReseachProject(PFHouseType type, Button b) {
		try {
			e.startResearchProject(type);
			b.setDisable(true);
			typeInResearch = type;
			adjustbuttons();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("P&D");
			alert.setHeaderText(null);
			alert.setContentText(
					"Your Housetype is now beeing explored. This will take " + type.getResearchDuration() + " rounds");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("House can't be explored!" + e.getMessage());
			alert.showAndWait();
		}

	}

	private void adjustbuttons() {
		if (typeInResearch != null || e.getHR().getNumberOfUnassignedEmployees(EmployeeType.ARCHITECT) == 0) {
			// already research project going on or no Architect, disable all
			// buttons
			for (Map.Entry<PFHouseType, Button> entry : buttons.entrySet()) {
				entry.getValue().setDisable(true);
			}
		} else {
			List<PFHouseType> list = e.getResearchedHouseTypes();
			for (Map.Entry<PFHouseType, Button> entry : buttons.entrySet()) {
				if (list.contains(entry.getKey())) {
					// disable all buttons which are already explored
					entry.getValue().setDisable(true);
				} else {
					// enable all buttons you have the money for
					if (e.getBankAccount().canBeCharged(entry.getKey().getResearchCosts())) {
						entry.getValue().setDisable(false);
					}
					entry.getValue().setDisable(false);
				}
			}
		}
	}

	/**
	 * @param type
	 *            the housetype you want to update the bar for
	 */
	private void adjustbar(PFHouseType type) {
		if (type == null) {
			return;
		}
		double adjust = 1.0 / type.getResearchDuration();
		ProgressBar bar = map.get(type);
		double amount = bar.getProgress() + adjust;
		if (amount > 0.99) { // double never gets to 1.00..
			amount = 1;
			typeInResearch = null;
		}
		bar.setProgress(amount);
	}

	@Override
	/**
	 * update the bar and the buttons
	 */
	public void update() {
		adjustbar(typeInResearch);
	}

	@Override
	public void changeTab() {
		adjustbuttons();
	}
}
