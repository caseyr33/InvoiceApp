package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SQLiteConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

public class CustomerEditController extends StyleController implements Initializable {

	@FXML
	private TextField streetField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField zipField;
	@FXML
	private ComboBox<STATE> stateBox;
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
	Connection connection;

	@FXML
	public void cancelEdit(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Customer View");
			stage.setScene(new Scene(root));
			root.getStylesheets().add("darkmode.css");
			stage.show();
			// Hide this current window (if this is what you want)
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String custSql = "";

	public void setSql(int i) {
		if (i == 1) {
			custSql = "INSERT INTO customers (customerID, phoneNum, customerEmail,"

					+ "streetAddress, city, state, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
		}
		if (i == 2) {
			custSql = "EDIT customers SET (phoneNum, customerEmail, streetAddress, city, state, zipCode) "
					+ " WHERE customerID = ?  VALUES (?, ?, ?, ?, ?, ?)";
		}
	}

	@FXML
	private Text custEditWarning;

	@FXML
	public void submitButtonClicked(ActionEvent event) {
		if (idField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty()
				|| streetField.getText().isEmpty() || cityField.getText().isEmpty() || stateBox.getValue() == null
				|| zipField.getText().isEmpty()) {
			custEditWarning.setText("Warning: All fields must be entered to continue");
		} else {
			try {
				// handles inserting customer info into customers table
				try (PreparedStatement pstmt = connection.prepareStatement(custSql)) {
					pstmt.setString(1, idField.getText());
					pstmt.setString(2, phoneField.getText());
					pstmt.setString(3, emailField.getText());
					pstmt.setString(4, streetField.getText());
					pstmt.setString(5, cityField.getText());
					pstmt.setString(6, stateBox.getValue().toString());
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
					root.getStylesheets().add("darkmode.css");
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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stateBox.setItems(FXCollections.observableArrayList(STATE.values()));
		connection = SQLiteConnection.Connector();
		setSql(1);

	}

	private enum STATE {
		AK, AL, AR, AS, AZ, CA, CO, CT, DC, DE, FL, GA, GU, HI, IA, ID, IL, IN, KS, KY, LA, MA, MD, ME, MI, MN, MO, MP, MS, MT, NC, ND, NE, NH, NJ, NM, NV, NY, OH, OK, OR, PA, PR, RI, SC, SD, TN, TX, UM, UT, VA, VI, VT, WA, WI, WV, WY;
	}

}
