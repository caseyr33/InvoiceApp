package controller;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.UserMainModel;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class AddUserController {
	private UserMainModel userModel;

	@FXML
	private Button submit;
	@FXML
	private Button cancel;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtConfirm;
	@FXML
	private ToggleGroup user_privilege;
	@FXML
	private Label lablMessage;
	@FXML
	private Label pwdErrorMsg;

	public AddUserController() {
		this.userModel = new UserMainModel();
	}

	@FXML
	public void addUser(ActionEvent event) {
		this.lablMessage.setText("");
		final String name = this.txtName.getText();
		final String username = this.txtUsername.getText();
		final String password = this.txtPassword.getText();
		final String confirm = this.txtConfirm.getText();
		if(!(name.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty() || confirm.trim().isEmpty())) {
			try {
				final RadioButton selectedBtn = (RadioButton) this.user_privilege.getSelectedToggle();
				final String selectedValue = selectedBtn.getText();
				final String isAdmin = selectedValue.equals("Administrator") ? "1" : "0";
				if (password.equals(confirm)) {
					if (this.userModel.addNewUser(name, username, password, isAdmin)) {
						this.userModel.goToView(event);
						System.out.println("DB INSERT SUCCESS");
					} else {
						System.out.println("DB INSERT FAILED");
					}
				}
				this.pwdErrorMsg.setText("Passwords do not match");
			} catch (NullPointerException e) {
				this.lablMessage.setText("Please Select User Privilege");
			}	
		}else {
			this.lablMessage.setText("Please Fill ALL Fields before Submit");
		}		
	}

	public boolean passwordConfirmed() throws NullPointerException {

		return false;
	}

	public void toView(ActionEvent event) {
		this.userModel.goToView(event);
	}

}