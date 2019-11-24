package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Product;
import model.ProductMainModel;

public class EditProductController implements Initializable {
	@FXML
	private TextField txtDesc;
	@FXML
	private TextField txtRate;
	@FXML
	private TextField txtId;
	@FXML
	private Label lablMissing;
	@FXML
	private Button submit;	

	private ProductMainModel mainModel = new ProductMainModel();
	private Product modProduct;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// ID suggested, but is also editable
		int suggestedId = this.mainModel.getLastId() + 1;
		this.txtId.setText(String.valueOf(suggestedId));
	}

	public void addNew(ActionEvent event) {
		try {
			String desc = this.txtDesc.getText();
			String txtRate = this.txtRate.getText();
			if (desc.trim().isEmpty() || txtRate.trim().isEmpty()) {
				this.lablMissing.setText("Missing Entry");
			} else {
				Double rate = Double.parseDouble(txtRate);
				int id = Integer.parseInt(this.txtId.getText());
				if (this.mainModel.insertNew(desc, rate, id)) {
					this.mainModel.goToView(event);
					System.out.println("insert success");
				} else {
					System.out.println("insert fail");
				}
			}
		} catch (NumberFormatException e) {
			this.lablMissing.setText("Rate Invalid");
		}
	}

	public void editProduct(ActionEvent e) {
		String desc = this.txtDesc.getText();
		double rate = Double.valueOf(this.txtRate.getText());
		int id = Integer.valueOf(this.txtId.getText());	
		if(this.mainModel.updateProduct(desc, rate, this.modProduct.getProductID(), id)) {
			this.mainModel.goToView(e);
			System.out.println("Product Update success");
		}else {
			System.out.println("Product Update failed");
		}
	}
	
	public void getProduct(Product pdt) {
		this.modProduct = pdt;
		this.txtDesc.setText(this.modProduct.getDescription());
		this.txtRate.setText(String.valueOf(this.modProduct.getRate()));
		this.txtId.setText(String.valueOf(this.modProduct.getProductID()));
	}
	
	public void toView(ActionEvent e) {
		this.mainModel.goToView(e);
	}
	
}