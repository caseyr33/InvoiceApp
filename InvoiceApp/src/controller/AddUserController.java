package controller;


import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.UserMainModel;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AddUserController
{
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
    private TextField txtPassword;
    @FXML
    private ToggleGroup user_privilege;
    @FXML
    private Label lablMessage;
    
    public AddUserController() {
        this.userModel = new UserMainModel();
    }
    
    @FXML
    public void addUser(ActionEvent event) throws SQLException, IOException {
        final String name = this.txtName.getText();
        final String username = this.txtUsername.getText();
        final String password = this.txtPassword.getText();
        final RadioButton selectedBtn = (RadioButton)this.user_privilege.getSelectedToggle();
        final String selectedValue = selectedBtn != null ? selectedBtn.getText() : "";         
        if(selectedValue.equals("")){
        	//No radio button selected
        	this.lablMessage.setText("Please Fill ALL Fields before Submit");        	
        }else {
        	//All fields ready for insert
        	final String isAdmin = selectedValue.equals("Administrator") ? "1" : "0";
        	if (this.userModel.addNewUser(name, username, password, isAdmin)) {
        		this.userModel.goToView(event);
                System.out.println("INSERT SUCCESS");
            }else {            	
                System.out.println("INSERT FAILED");
            }
        }
    }
    
    public void toHome(ActionEvent event) {
    	this.userModel.goToHome(event);     
    }
}