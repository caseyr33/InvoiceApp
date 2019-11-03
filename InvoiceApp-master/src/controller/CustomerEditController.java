package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class CustomerEditController{

	@FXML
	private TextField streetField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField zipField;
	@FXML
	private TextField stateField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneField;
	@FXML
	private Button submitButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField idField;
	@FXML
	private Text warningLabel;

	
	@FXML
	public void cancelEdit(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Customer View");
			stage.setScene(new Scene(root));
			stage.show();
			// Hide this current window (if this is what you want)
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void submitButtonClicked(ActionEvent event) {

		try {

			// handles inserting customer info into customers table

			String custSql = "INSERT INTO customers (customerID, phoneNum, customerEmail,"

					+ "streetAddress, city, state, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(custSql)) {

				pstmt.setString(1, idField.getText());				
				pstmt.setString(2, phoneField.getText());			
				pstmt.setString(3, emailField.getText());				
				pstmt.setString(4, streetField.getText());				
				pstmt.setString(5, cityField.getText());				
				pstmt.setString(6, stateField.getText());				
				pstmt.setString(7, zipField.getText());
				pstmt.executeUpdate();

			} catch (SQLException e) {
				
				warningLabel.setText("Warning: all fields must be filled in");
				System.out.println(e.getMessage());
			}

			Parent root;

			try {

				root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
				Stage stage = new Stage();
				stage.setTitle("Customers");
				stage.setScene(new Scene(root));
				stage.show();
				// Hide this current window (if this is what you want)
				((Node) (event.getSource())).getScene().getWindow().hide();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private Connection connect() {

		// SQLite connection string

		String url = "jdbc:sqlite:C:hankdb.db";

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(url);

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return conn;

	}

	

}