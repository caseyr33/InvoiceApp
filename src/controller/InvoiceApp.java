package controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InvoiceApp extends Application {

      @Override
      public void start(Stage primaryStage) {
            try {                     
                  Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                  primaryStage.setTitle("Hank Sauce - Login");
                  primaryStage.setScene(new Scene(root));
                  primaryStage.setResizable(false);
                  primaryStage.show();        
              } catch(Exception e) {
                  e.printStackTrace();
              }             
      }
      public static void main(String[] args) {
           launch(args);
      }
}
 
