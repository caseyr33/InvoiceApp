
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewHistoryController {
	@FXML
	private ListView<HBox> invoiceListView;
	@FXML
	private Button closeButton;
	
	private ObservableList<Invoice> invoices = FXCollections.observableArrayList();
	private ObservableList<Product> products = FXCollections.observableArrayList();

	
	public void initialize() {
		initProducts();
		String sql = "SELECT invoiceID, customerID, date, total, products, paidOnDelivery FROM invoices";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			while (rs.next()) {
				Invoice x = new Invoice();
				x.setInvoiceID(rs.getInt("invoiceID"));
				x.setCustomerID(rs.getString("customerID"));
				x.setDate(rs.getDate("date"));
				x.setTotal(rs.getDouble("total"));
				x.setProducts(rs.getString("products"));
				x.setPaidOnDelivery(rs.getBoolean("paidOnDelivery"));
				invoices.add(x);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		for(Invoice inv :invoices) {
			ArrayList<String> prods = splitString(inv.getProducts());
			for(int i = 0; i < prods.size()+1; i++) {
				HBox row = new HBox();
				if(i == 0) {
					Label invID = new Label(String.valueOf(inv.getInvoiceID()));
					Label custID = new Label(inv.getCustomerID());
					Date d = inv.getDate();  
		            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");  
		            String strDate = dateFormat.format(d);  
					Label date = new Label(strDate);
					Label total = new Label(String.format("$%.2f", inv.getTotal()));
					Label grandTotal = new Label("Grand Total: ");
					Label paidOnDelivery = new Label();
					if(inv.getPaidOnDelivery()) {
						paidOnDelivery.setText("Paid on Delivery");
					}else {
						paidOnDelivery.setText("Net 15");
					}
					invID.setMinWidth(30);
					custID.setMinWidth(300);
					date.setMinWidth(100);
					total.setMinWidth(100);
					grandTotal.setMinWidth(75);
					paidOnDelivery.setMinWidth(50);
					row.getChildren().addAll(invID, date, custID, grandTotal, total, paidOnDelivery);
				}else {
					String prodName = "";
					double r = 0;
					String[] arr = prods.get(i-1).split(",");
					for(Product p: products) {
						if(p.getProductID() == Integer.parseInt(arr[0])) {
							prodName = p.getDescription();
							r = p.getRate();
						}
					}
					Label padding = new Label();
					Label product = new Label(prodName);
					Label quantity = new Label(arr[1]);
					Label rate = new Label(String.format("@ $%.2f", r));
					Label total = new Label(String.format("$%.2f", r*Integer.parseInt(arr[1])));
					padding.setMinWidth(300);
					product.setMinWidth(200);
					quantity.setMinWidth(30);
					rate.setMinWidth(100);
					total.setMinWidth(50);
					row.getChildren().addAll(padding, quantity, product, rate, total);
				}
				invoiceListView.getItems().add(row);

			}
			
			
		}
	}
	
	//splits product String from database into array list of strings
	public ArrayList<String> splitString(String str){
		String[] split = str.split(";");
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(Arrays.asList(split));
		return ret;
	}
	
	//initializes the products list
	public void initProducts() {
		String sql = "SELECT productID, description, rate FROM products";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			// loop through the result set
			while (rs.next()) {
				Product x = new Product(rs.getInt("productID"), rs.getString("description"), rs.getDouble("rate"));
				products.add(x);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	// Event Listener on Button[#closeButton].onAction
	@FXML
	public void closeButtonClicked(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Home.fxml"));
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
	
	
}
