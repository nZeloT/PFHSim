package src.ui;

import java.io.IOException;

import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sim.Enterprise;
import sim.EnterpriseException;
import sim.procurement.ResourceMarket;
import sim.procurement.ResourceMarketException;
import sim.procurement.ResourceType;

public class Procurement {

	private @FXML Button btn_wood;
	private @FXML Button btn_concrete;
	private @FXML Button btn_insulation;
	private @FXML Button btn_roof;
	private @FXML Button btn_brick;

	private @FXML Label price_wood;
	private @FXML Label price_concrete;
	private @FXML Label price_insulation;
	private @FXML Label price_roof;
	private @FXML Label price_brick;

	private @FXML Label PLUS_wood;
	private @FXML Label PLUS_concrete;
	private @FXML Label PLUS_insulation;
	private @FXML Label PLUS_roof;
	private @FXML Label PLUS_brick;

	private @FXML Label sum_wood;
	private @FXML Label sum_concrete;
	private @FXML Label sum_insulation;
	private @FXML Label sum_roof;
	private @FXML Label sum_brick;

	private @FXML Label AM_wood;
	private @FXML Label AM_concrete;
	private @FXML Label AM_insulation;
	private @FXML Label AM_roof;
	private @FXML Label AM_brick;

	private @FXML TextField input_wood;
	private @FXML TextField input_concrete;
	private @FXML TextField input_insulation;
	private @FXML TextField input_roof;
	private @FXML TextField input_brick;

	private Enterprise ent;
	private ResourceMarket market;
	private @FXML VBox root;
	
	private int int_amount_wood;
	private int int_amount_concrete;
	private int int_amount_insulation;
	private int int_amount_roof;
	private int int_amount_brick;
	
	
	public Procurement(Enterprise enterprise) {
		
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/ui/fxml/Procurement.fxml"));
		fxml.setController(this);
		try {
			this.ent = enterprise; 
			market = ent.getMarket();
			fxml.load(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	} 
 
	public void initialize() {
		price_wood.setText(" " + market.getPrice(ResourceType.WOOD) );
		price_concrete.setText(" " + market.getPrice(ResourceType.CONCRETE));
		price_insulation.setText(" " + market.getPrice(ResourceType.INSULATION));
		price_roof.setText(" " + market.getPrice(ResourceType.ROOF_TILE));
		price_brick.setText(" " + market.getPrice(ResourceType.BRICK));
		
		AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
		AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));
		AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));
		AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
	}
	
	public void update(){
		
		price_wood.setText("" + market.getPrice(ResourceType.WOOD) );
		price_concrete.setText("" + market.getPrice(ResourceType.CONCRETE));
		price_insulation.setText("" + market.getPrice(ResourceType.INSULATION));
		price_roof.setText("" + market.getPrice(ResourceType.ROOF_TILE));
		price_brick.setText("" + market.getPrice(ResourceType.BRICK));
	}

	@FXML
	private void handleBuyButton(ActionEvent event) throws EnterpriseException, ResourceMarketException {
		Button btn = (Button) event.getSource();
		 
		if (btn.getId().equals("btn_wood")) {
			ent.buyResources(ResourceType.WOOD, int_amount_wood);
			AM_wood.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.WOOD));
			System.out.println(int_amount_wood);
		}

		if (btn.getId().equals("btn_insulation")) {
			ent.buyResources(ResourceType.INSULATION, int_amount_insulation);
			AM_insulation.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.INSULATION));
		}

		if (btn.getId().equals("btn_concrete")) {
			ent.buyResources(ResourceType.CONCRETE, int_amount_concrete);
			AM_concrete.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.CONCRETE));
		}
		if (btn.getId().equals("btn_roof")) {
			ent.buyResources(ResourceType.ROOF_TILE, int_amount_roof);
			AM_roof.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.ROOF_TILE));

		}
		if (btn.getId().equals("btn_brick")) {
			ent.buyResources(ResourceType.BRICK, int_amount_brick);
			AM_brick.setText(" " + ent.getWarehouse().getStoredAmount(ResourceType.BRICK));
		}

	}
	@FXML
	public void handleInputField(ActionEvent event){
		TextField text = (TextField) event.getSource();
		
		if (text.getId().equals("input_wood")) { 
			int_amount_wood = Integer.parseInt(text.getText());
			sum_wood.setText(" " + market.getPrice(ResourceType.WOOD) * int_amount_wood);
		} 

		if (text.getId().equals("input_insulation")) {
			int_amount_insulation = Integer.parseInt(text.getText());
			sum_insulation.setText(" " + market.getPrice(ResourceType.INSULATION)*int_amount_insulation);
		}

		if (text.getId().equals("input_concrete")) {
			int_amount_concrete = Integer.parseInt(text.getText());
			sum_concrete.setText(" " +  market.getPrice(ResourceType.CONCRETE)*int_amount_concrete); 
		}
		if (text.getId().equals("input_roof")) {
			int_amount_roof = Integer.parseInt(text.getText());
			sum_roof.setText(" " + market.getPrice(ResourceType.ROOF_TILE)*int_amount_roof);

		}
		if (text.getId().equals("input_brick")) {
			int_amount_brick = Integer.parseInt(text.getText());
			sum_brick.setText(" " + market.getPrice(ResourceType.BRICK)*int_amount_brick);
		}
		
	
		
	}
	
	public VBox getBox() {
		return root;
	}


}
