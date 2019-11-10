package controller;

import model.UserMainModel;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController extends StyleController {
	private UserMainModel userModel;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;

	public LoginController() {
		this.userModel = new UserMainModel();
	}

	public void login(final ActionEvent event) throws SQLException, IOException {
		if (this.userModel.isVerified(this.txtUsername.getText(), this.txtPassword.getText())) {
			System.out.println("Logged in successfully!");
			 if (userModel.isAdmin(this.txtUsername.getText())) {
					final Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/Home.fxml"));
					final Stage stage = new Stage();
					stage.setTitle("Hank Sauce - Administator View");
					stage.setScene(new Scene(root));
					root.getStylesheets().add("darkmode.css");
					stage.show();
					((Node) event.getSource()).getScene().getWindow().hide();
				}else {
					final Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/view/DriverHome.fxml"));
					final Stage stage = new Stage();
					stage.setTitle("Hank Sauce - Driver View");
					stage.setScene(new Scene(root));
					root.getStylesheets().add("carkmode.css");
					stage.show();
					((Node) event.getSource()).getScene().getWindow().hide();
				}
			//this.userModel.goToHome(event);
		} else {
			System.out.println("Password Incorrect...");
		}
	}
}