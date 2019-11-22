package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Product;
import model.ProductMainModel;
import model.UserMainModel;
import model.ViewUserModel;

public class ViewProductsController implements Initializable {

	@FXML
	private Label title;
	@FXML
	private TableView<Product> products;
	@FXML
	private TableColumn<Product, String> colProductId;
	@FXML
	private TableColumn<Product, String> colProductName;
	@FXML
	private TableColumn<Product, Double> colRate;

	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAddNew;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnHome;
	@FXML
	private Label lblSelect;

	ObservableList<Product> listview = FXCollections.observableArrayList();

	private ProductMainModel mainModel = new ProductMainModel();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
		colProductName.setCellValueFactory(new PropertyValueFactory<>("Description"));
		colRate.setCellValueFactory(new PropertyValueFactory<>("Rate"));
		try {
			String sql = "SELECT * FROM PRODUCTS";
			Statement stmt = this.mainModel.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				listview.add(new Product(rs.getInt("productID"), rs.getString("description"), rs.getDouble("rate")));
			}
			products.setItems(listview);
		} catch (Exception e) {

		}
	}

	@FXML
	public void addNewProduct(ActionEvent event) {
		this.mainModel.goToAddNew(event);
	}

	@FXML
	public void removeProduct() throws SQLException {
		Product selectedProduct = products.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			int productId = selectedProduct.getProductID();
			if (this.mainModel.remove(productId)) {
				ObservableList<Product> all, single;
				all = products.getItems();
				single = products.getSelectionModel().getSelectedItems();
				single.forEach(all::remove);
				this.lblSelect.setText("");
			}
		} else {
			this.lblSelect.setText("Please Select Product");
		}
	}

	@FXML
	public void editProduct(ActionEvent event) throws IOException {
		Product selectedProduct = products.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			// Processing selected entry in different window
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/view/EditProduct.fxml").openStream());
			Scene scene = new Scene(root);
			EditProductController editController = (EditProductController) loader.getController();
			editController.getProduct(selectedProduct);
			stage.setScene(scene);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} else {
			this.lblSelect.setText("Please Select Product");
		}
	}

	@FXML
	public void toHome(ActionEvent event) {
		this.mainModel.goToHome(event);
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

}