package model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;

public class ProductMainModel {
	private Connection connection;

	public ProductMainModel() {
		this.connection = SQLiteConnection.Connector();
		if (this.connection == null) {
			System.exit(1);
		}
	}

	public Connection getConn() {
		return this.connection;
	}

	public boolean insertNew(String desc, double rate, int id) {		
		// Check for duplicate IDs
		final String checkQuery = "select count(*) as found from products where productid=?";
		try (Connection conn = SQLiteConnection.Connector()) {
			PreparedStatement preparedStatement = conn.prepareStatement(checkQuery);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			boolean found = rs.getBoolean(1);
			if (found) {
				// Record exists already
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Duplicate ID Found");
				alert.setHeaderText(null);
				alert.show();
			} else {
				String query = "insert into products (productId, description, rate) values(?,?,?)";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, desc);
				preparedStatement.setDouble(3, rate);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getLastId() {
		try (Connection conn = SQLiteConnection.Connector()){
			String query = "select * from products order by productid desc limit 1";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs.getInt("PRODUCTID");
		} catch (SQLException e) {

		}
		return -1;
	}

	
	//STOPPED HERE.......
	public boolean updateProduct(String desc, double rate, int oldId, int newId) {
		boolean idModified = !(oldId == newId);
		try (Connection conn = SQLiteConnection.Connector()) {
			String query = "update products set description=?, rate=? where productID=?";
			if(idModified) query = "update products set description=?, rate=?, productID=? where productID=?";			
			PreparedStatement preparedStatement = conn.prepareStatement(query);			
			preparedStatement.setString(1, desc);
			preparedStatement.setDouble(2, rate);
			if(idModified) {
				preparedStatement.setInt(3, newId);
				preparedStatement.setInt(4, oldId);
			}else	preparedStatement.setInt(3, oldId);			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Product ID " + newId + " already taken");
			alert.setHeaderText(null);
			alert.show();
		}
		return false;
	}

	public boolean remove(int productID) throws SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Remove Product?");
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			PreparedStatement preparedStatement = null;
			try (Connection conn = SQLiteConnection.Connector()){
				String query = "delete from products where productID=?";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setInt(1, productID);
				preparedStatement.executeUpdate();
				System.out.println("Product removed");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				preparedStatement.close();
			}
		}
		return false;
	}

	public void goToAddNew(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
			Stage stage = new Stage();
			stage.setTitle("New Product");
			stage.setScene(new Scene(root));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void goToHome(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Hank Sauce Home");
			stage.setScene(new Scene(root));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void goToView(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/ViewProducts.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Products Main Menu");
			stage.setScene(new Scene(root));
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}