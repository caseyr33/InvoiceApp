package controller;


import java.util.ResourceBundle;
import java.net.URL;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.LoginModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

public class LoginController implements Initializable
{
    public LoginModel loginModel;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    
    public LoginController() {
        this.loginModel = new LoginModel();
    }
    
    public void login(final ActionEvent event) {
        try {
            if (this.loginModel.isLoggedIn(this.txtUsername.getText(), this.txtPassword.getText())) {
                System.out.println("Logged in successfully!");
                final Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("/view/Home.fxml"));
                final Stage stage = new Stage();
                stage.setTitle("Admin Home");
                stage.setScene(new Scene(root));
                stage.show();
                ((Node)event.getSource()).getScene().getWindow().hide();
            }
            else {
                System.out.println("Password Incorrect...");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        if (this.loginModel.isDbConnected()) {
            System.out.println("We connected baby!!");
        }
        else {
            System.out.println("Not connected :(");
        }
    }
}
