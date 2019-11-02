package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Product;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ComboBox;

public class InvoiceCreatorController {
	@FXML
	private TextField nameCompany;
	@FXML
	private TextField streetAddress;
	@FXML
	private TextField city;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField zipCode;
	@FXML
	private TextField emailAddress;
	@FXML
	private DatePicker date;
	@FXML
	private ComboBox<STATE> stateBox;
	@FXML
	private RadioButton net15Button;
	@FXML
	private RadioButton paidOnDeliveryButton;
	@FXML
	private Label total;
	@FXML
	private Button submitButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ComboBox<String> product;
	@FXML
	private HBox HBox1;
	@FXML
	private Spinner<Integer> quantity;
	@FXML
	private ListView<HBox> productListView;
	@FXML
	private Button addProductButton;
	@FXML
	private GridPane grid;
	@FXML
	private Button removeProductButton;
	@FXML
	private Label warningLabel;

	private ObservableList<String> productNames = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();
	private SimpleDoubleProperty grandTotal = new SimpleDoubleProperty(0);
	ToggleGroup tg = new ToggleGroup();

	public void initialize() {
		// initializes statebox values
		stateBox.setItems(FXCollections.observableArrayList(STATE.values()));
		// initializes quantity spinner values
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		quantity.setValueFactory(valueFactory);
		// initializes products w/ info from database
		initProducts();
		product.setItems(productNames);
		// initialize grandTotal
		total.textProperty().bind(Bindings.format("$ %.2f", grandTotal));
		// sets net15button and paidOndeliveryButton to togglegroup
		net15Button.setToggleGroup(tg);
		paidOnDeliveryButton.setToggleGroup(tg);
	}


	// Event Listener on Button[#submitButton].onAction
	@FXML
	public void submitButtonClicked(ActionEvent event) {
		try {
			//turns product list into a string
			String prod = "";
			for(HBox hb :productListView.getItems()) {
				int prodID = 0;
				for(Product p: products) {
					Label match = (Label)hb.getChildren().get(1);
					if(p.getDescription().equals(match.getText())){
						prodID = p.getProductID();
					}
				}
				Label quan = (Label)hb.getChildren().get(2);
				prod = prod.concat(String.valueOf(prodID)+","+quan.getText()+";");
			}
			//handles paidondelivery and net15 radio button input
			boolean paidOnDelivery;
			if(tg.getSelectedToggle().equals(paidOnDeliveryButton)) {
				paidOnDelivery = true;
			}else {
				paidOnDelivery = false;
			}
			//handles inserting invoice info into invoices table
			String prodSql = "INSERT INTO invoices (customerID, date, total,"
					+ "products, paidOnDelivery) VALUES (?, ?, ?, ?, ?)";
			try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(prodSql)) {
				pstmt.setString(1, nameCompany.getText());
				pstmt.setDate(2, Date.valueOf(date.getValue()));
				pstmt.setDouble(3, Double.parseDouble(total.getText().substring(2,total.getText().length())));
				pstmt.setString(4, prod);
				pstmt.setBoolean(5, paidOnDelivery);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				warningLabel.setText("Warning: all fields must be filled in");
				System.out.println(e.getMessage());
			}
			//handles inserting customer info into customers table
			String custSql = "INSERT INTO customers (customerID, phoneNum, customerEmail,"
					+ "streetAddress, city, state, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try(Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(custSql)){
				pstmt.setString(1, nameCompany.getText());
				pstmt.setString(2, phoneNumber.getText());
				pstmt.setString(3, emailAddress.getText());
				pstmt.setString(4, streetAddress.getText());
				pstmt.setString(5, city.getText());
				pstmt.setString(6, stateBox.getValue().toString());
				pstmt.setString(7, zipCode.getText());
				pstmt.executeUpdate();
			}catch(SQLException e) {
				warningLabel.setText("Warning: all fields must be filled in");
				System.out.println(e.getMessage());
			}
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
				Stage stage = new Stage();
				stage.setTitle("Hank Sauce - Administrator View");
				stage.setScene(new Scene(root));
				stage.show();
				// Hide this current window (if this is what you want)
				((Node) (event.getSource())).getScene().getWindow().hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e) {
			warningLabel.setText("Warning: all fields must be filled in");
		}
		
	}

	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelButtonClicked(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Hank Sauce - Administrator View");
			stage.setScene(new Scene(root));
			stage.show();
			// Hide this current window (if this is what you want)
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void addProductButtonClicked(ActionEvent event) {
		HBox newRow = getListViewRow(product.getValue().toString(), quantity.getValue().toString());		
		productListView.getItems().add(newRow);
		productNames.remove(product.getValue());
		product.setItems(productNames);
		quantity.getValueFactory().setValue(1);
	}
	
	@FXML
	public void removeProductButtonClicked(ActionEvent event) {
		int selectedInd = productListView.getSelectionModel().getSelectedIndex();
		HBox row = productListView.getItems().get(selectedInd);
		Label prodName = (Label)row.getChildren().get(1);
		String t = (String)((Label) row.getChildren().get(3)).getText();
		t = t.substring(1, t.length());
		grandTotal.setValue(grandTotal.getValue() - Double.parseDouble(t));
		productNames.add(prodName.getText());
		productListView.getItems().remove(selectedInd);
	}

	public void initProducts() {
		String sql = "SELECT productID, description, rate FROM products";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			while (rs.next()) {
				productNames.add(rs.getString("description"));
				Product x = new Product(rs.getInt("productID"), rs.getString("description"), rs.getDouble("rate"));
				products.add(x);

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:C:/Users/ryanc/SQLite/db/hankdb.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public HBox getListViewRow(String p, String q) {
		HBox content = new HBox();
		Label rate = null;
		Label product = new Label(p);
		Label quantity = new Label(q);
		Label total = null;
		for (Product prod : products) {
			if (prod.getDescription().equals(p)) {
				double r = prod.getRate();
				double t = Integer.parseInt(q) * r;
				rate = new Label(String.format("$%.2f", r));
				total = new Label(String.format("$%.2f", t));
				grandTotal.setValue(grandTotal.getValue() + t);
				;
			}
		}
		rate.setMinWidth(70);
		product.setMinWidth(260);
		quantity.setMinWidth(60);
		total.setMinWidth(20);
		content.getChildren().addAll(rate, product, quantity, total);
		return content;
	}
	
	private enum STATE {
		AK, AL, AR, AS, AZ, CA, CO, CT, DC, DE, FL, GA, GU, HI, IA, ID, IL, IN, KS, KY, LA, MA, MD, ME, MI, MN, MO, MP,
		MS, MT, NC, ND, NE, NH, NJ, NM, NV, NY, OH, OK, OR, PA, PR, RI, SC, SD, TN, TX, UM, UT, VA, VI, VT, WA, WI, WV,
		WY;
	}
}
