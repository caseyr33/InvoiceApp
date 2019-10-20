

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class HomeController {
	@FXML
	private Button createAnInvoice;

	// Event Listener on Button[#createAnInvoice].onAction
	@FXML
	public void openInvoiceView(ActionEvent event) {
		Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("InvoiceCreator.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Invoice Creator");
            stage.setScene(new Scene(root));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}
}
