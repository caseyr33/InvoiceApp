package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.ProductMainModel;

public class AddProductController implements Initializable {

	@FXML
	private TextField txtDesc;

	@FXML
	private TextField txtRate;

	@FXML
	private TextField txtId;

	@FXML
	private Label lablMissing;

	@FXML
	private Button BtnAddNew;	

	private ProductMainModel mainModel = new ProductMainModel();

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

	public void toView(ActionEvent e) {
		this.mainModel.goToView(e);
	}
	
}