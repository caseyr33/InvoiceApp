package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserMainModel;
import model.ViewUserModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class EditUserController implements Initializable {

	@FXML
	private Button submit;
	@FXML
	private Button cancel;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtUsername;
	@FXML
	private Pane panePwd;
	@FXML
	private Hyperlink linkChangePwd;	
	@FXML
	private PasswordField txtNew;
	@FXML
	private PasswordField txtConfirm;
	@FXML
	private Label errorMessage;
	@FXML
	private ToggleGroup user_privilege;
	@FXML
	private RadioButton isAdmin;
	@FXML
	private RadioButton isDriver;
	
	private UserMainModel userModel = new UserMainModel();
	private ViewUserModel currentUser;

	@FXML
	public void changePwd(ActionEvent event) {
		this.panePwd.setDisable(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

	public void getUser(ViewUserModel user) {
		this.currentUser = user;
		this.txtName.setText(this.currentUser.getName());
		this.txtUsername.setText(this.currentUser.getUsername());
		if (this.currentUser.getAdmin().equals("Yes")) {
			this.isAdmin.setSelected(true);
		} else {
			this.isDriver.setSelected(true);
		}
	}

	@FXML
	public void updateUserInfo(ActionEvent event) throws SQLException {
		final String name = this.txtName.getText();
		final String newUsername = this.txtUsername.getText();
		final RadioButton selectedBtn = (RadioButton) this.user_privilege.getSelectedToggle();
		final String selectedValue = selectedBtn.getText();
		final String isAdmin = selectedValue.equals("Administrator") ? "1" : "0";
		if(!this.panePwd.isDisabled()) {
			//Change password pane opened
			String newPwd = this.txtNew.getText();
			String conf = this.txtConfirm.getText();
			if(!newPwd.trim().isEmpty()) {				
				if(newPwd.equals(conf)) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Change Confirmation");
					alert.setHeaderText(null);
					alert.setContentText("Confirm Password Change?");
					Optional<ButtonType> answer = alert.showAndWait();
					if (answer.get() == ButtonType.OK) {
						this.userModel.updatePassword(newUsername, newPwd);	
						System.out.println("Password changed");
					}
				}else {
					this.errorMessage.setText("Passwords do not match");
					return;
				}					
			}			
		}		
		if (this.userModel.updateUser(name, this.currentUser.getUsername(), newUsername, isAdmin)) {
			this.userModel.goToView(event);
			System.out.println("UDPATE SUCCESS");
		} else {
			System.out.println("UPDATE FAILED");
		}
	}

	@FXML
	public void close(ActionEvent event) {
		Stage stage = (Stage) this.cancel.getScene().getWindow();
		stage.close();
		this.userModel.goToView(event);
	}

}