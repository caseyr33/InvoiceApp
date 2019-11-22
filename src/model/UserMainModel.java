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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class UserMainModel {
	private Connection connection;

	public UserMainModel() {
		this.connection = SQLiteConnection.Connector();
		if (this.connection == null) {
			System.exit(1);
		}
	}

	public Connection getConn() {
		return this.connection;
	}

	public boolean isVerified(final String user, final String pass) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		final String query = "select * from users where username = ? and password = ?";
		try {
			preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user);
			String pwd = generateHash(pass);
			preparedStatement.setString(2, pwd);
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			preparedStatement.close();
			resultSet.close();
		}
	}

	public boolean addNewUser(final String name, final String username, final String password, final String isAdmin){
		// First check if record exists
		final String checkQuery = "select count(*) as found from users where username=?";
		try (Connection conn = SQLiteConnection.Connector()) {
			PreparedStatement preparedStatement = conn.prepareStatement(checkQuery);
			preparedStatement.setString(1, username);
			ResultSet rs = preparedStatement.executeQuery();
			boolean found = rs.getBoolean(1);
			if (found) {
				// Record exists already
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Duplicate Username found");
				alert.setHeaderText(null);
				alert.show();
			} else {
				final String query = "insert into users (Name, username, password, isAdmin) values(?,?,?,?)";
				preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, username);
				// Encrypt password
				String pwd = generateHash(password);
				preparedStatement.setString(3, pwd);
				preparedStatement.setString(4, isAdmin);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateUser(String name, String oldUsername, String newUsername, String isAdmin) {
		try {
			String query = "update users set Name=?, username=?, isAdmin=? where username=?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, newUsername);
			preparedStatement.setString(3, isAdmin);
			preparedStatement.setString(4, oldUsername);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Username " + newUsername + " already taken");
			alert.setHeaderText(null);
			alert.show();
		}
		return false;
	}

	public void updatePassword(String username, String newPassword){
		try {
			String query = "update users set password=? where username=?";
			// hash password string...
			String pwd = generateHash(newPassword);
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, pwd);
			preparedStatement.setString(2, username);
			preparedStatement.executeUpdate();
			preparedStatement.close();	
		}catch(Exception e) {
			System.out.println("Password Update Error");
		}		
	}

	public boolean remove(String username) throws SQLException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Confirm Remove " + username + "?");
		Optional<ButtonType> answer = alert.showAndWait();
		if (answer.get() == ButtonType.OK) {
			PreparedStatement preparedStatement = null;
			try {
				String query = "delete from users where username=?";
				preparedStatement = this.connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.executeUpdate();
				System.out.println(username + " removed");
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
			root = FXMLLoader.load(getClass().getResource("/view/AddUser.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Add New User");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
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
			stage.setResizable(false);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void goToView(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/ViewUsers.fxml"));
			Stage stage = new Stage();
			stage.setTitle("View Users");
			stage.setScene(new Scene(root));
			stage.setResizable(false);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isAdmin(String user) {
		String sql = "SELECT username, isAdmin FROM users";
		try (Connection conn = this.connection;
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				if (rs.getString("username").equals(user)) {
					return rs.getBoolean("isAdmin");
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String generateHash(String in) throws NoSuchAlgorithmException {
		String salt = "TheSauceBawss";
		String str = in + salt;
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] bytes = sha.digest(str.getBytes());
		BigInteger no = new BigInteger(1, bytes);
		String hash = no.toString(16);
		while (hash.length() < 32) {
			hash = "0" + hash;
		}
		return hash;
	}

}