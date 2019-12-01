package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.event.ActionEvent;

public class HomeController {
	@FXML
	private Button createAnInvoice;
	@FXML
	private Button viewHistoryButton;
	@FXML
	private Button addUser;
	@FXML
	private Button editUsers;
	@FXML
	private Button productsMenu;
	// Event Listener on Button[#createAnInvoice].onAction
	@FXML
	public void openInvoiceView(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/InvoiceCreator.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Invoice Creator");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@FXML
	public void openViewHistory(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ViewHistory.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Invoices");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	@FXML
	public void openViewProducts(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ViewProducts.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Product Menu");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }		
	}
	@FXML
	public void openViewUsers(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/ViewUsers.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Invoice Creator");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }		
	}
	@FXML
	public void openCustomerView(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer View");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}