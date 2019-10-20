import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InvoiceApp extends Application {

      @Override
      public void start(Stage primaryStage) {
            try {                     
                  Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));      
                  primaryStage.setTitle("Hank Sauce - Administrator View");
                  primaryStage.setScene(new Scene(root));
                  primaryStage.show();        
              } catch(Exception e) {
                  e.printStackTrace();
              }             
      }
      public static void main(String[] args) {
           launch(args);
      }
}
 
