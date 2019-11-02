package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CustomerInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class CustomerViewController implements Initializable{

@FXML
private TableView<CustomerInfo> customerTable;
@FXML
private TableColumn<CustomerInfo, String>  nameTable;
@FXML
private TableColumn<CustomerInfo, String>  phoneTable;
@FXML
private TableColumn<CustomerInfo, String>  emailTable;
@FXML
private TableColumn<CustomerInfo, String>  addressTable;
@FXML
private TableColumn<CustomerInfo, String>  cityTable;
@FXML
private TableColumn<CustomerInfo, String>  stateTable;
@FXML
private TableColumn<CustomerInfo, String>  zipTable;

ObservableList<CustomerInfo> listview = FXCollections.observableArrayList();
private Connection connection;

	@FXML
	private Button cancelCust;

	@FXML
	public void cancelCustomerView(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hank Sauce - Administrator View");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

	@FXML
	public void addCustomer(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/CustomerInfoView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Customer Info");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nameTable.setCellValueFactory(new PropertyValueFactory<>("name"));
		phoneTable.setCellValueFactory(new PropertyValueFactory<>("phone"));
		emailTable.setCellValueFactory(new PropertyValueFactory<>("email"));
		addressTable.setCellValueFactory(new PropertyValueFactory<>("address"));
		cityTable.setCellValueFactory(new PropertyValueFactory<>("city"));
		stateTable.setCellValueFactory(new PropertyValueFactory<>("state"));
		zipTable.setCellValueFactory(new PropertyValueFactory<>("zip"));
		
		try {
			connection = connect();
			String sql = "SELECT * FROM customers";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				listview.add(new CustomerInfo(rs.getString("customerID"), rs.getString("phoneNum"), rs.getString("customerEmail"),
						rs.getString("streetAddress"),rs.getString("city"), rs.getString("state"), rs.getString("zipCode")));
				
			}
			customerTable.setItems(listview);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection connect() {

		// SQLite connection string

		String url = "jdbc:sqlite:hankdb.db";

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(url);

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return conn;

	}
}