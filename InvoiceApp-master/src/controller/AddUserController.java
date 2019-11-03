package controller;


import java.io.IOException;
import java.sql.SQLException;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import model.UserMainModel;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class AddUserController
{
    private UserMainModel userModel;
    
    @FXML
    private Button submit;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ToggleGroup user_privilege;
    @FXML
    private AnchorPane paneAddUser;
    
    public AddUserController() {
        this.userModel = new UserMainModel();
    }
    
    @FXML
    public void addUser() throws SQLException, IOException {
        final String name = this.txtName.getText();
        final String username = this.txtUsername.getText();
        final String password = this.txtPassword.getText();
        final RadioButton selectedBtn = (RadioButton)this.user_privilege.getSelectedToggle();
        final String selectedValue = selectedBtn.getText();
        final String isAdmin = selectedValue.equals("Administrator") ? "1" : "0";
        if (this.userModel.addNewUser(name, username, password, isAdmin)) {
            System.out.println("INSERT SUCCESS");
        }
        else {
            System.out.println("INSERT FAILED");
        }
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/ViewUsers.fxml"));
        paneAddUser.getChildren().setAll(pane);
    }
}