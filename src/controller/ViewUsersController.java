package controller;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SQLiteConnection;
import model.ViewUserModel;

public class ViewUsersController implements Initializable{

	@FXML
	private TableView<ViewUserModel> users;
	
	@FXML
	private TableColumn<ViewUserModel, String> colName;
	
	@FXML
	private TableColumn<ViewUserModel, String> colUsername;

	@FXML
	private TableColumn<ViewUserModel, String> colAdmin;

	ObservableList<ViewUserModel> listview = FXCollections.observableArrayList();

	private Connection connection;
	
	public ViewUsersController() {
		 this.connection = SQLiteConnection.Connector();
	        if (this.connection == null) {
	            System.exit(1);
	        }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		colAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
		try {
			String sql = "SELECT * FROM USERS";
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				listview.add(new ViewUserModel(rs.getString("Name"), rs.getString("username"), rs.getBoolean("isAdmin")));				
			}
			users.setItems(listview);
		}catch(Exception e) {
			
		}
	}
	
}