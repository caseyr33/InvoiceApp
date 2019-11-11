package controller;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserMainModel;
import model.ViewUserModel;

public class ViewUsersController implements Initializable{

	@FXML
	private TableView<ViewUserModel> users;
	
	@FXML
	private TableColumn<ViewUserModel, String> colName;
	
	@FXML
	private TableColumn<ViewUserModel, String> colUsername;

	@FXML
	private TableColumn<ViewUserModel, String> colAdmin;
	
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAddNew;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnHome;

	ObservableList<ViewUserModel> listview = FXCollections.observableArrayList();
	
	private UserMainModel userModel = new UserMainModel();	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		colAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
		try {
			String sql = "SELECT * FROM USERS";
			Statement stmt = this.userModel.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				listview.add(new ViewUserModel(rs.getString("Name"), rs.getString("username"), rs.getBoolean("isAdmin")));				
			}
			users.setItems(listview);				
		}catch(Exception e) {
			
		}
	}
	
	@FXML
	public void addNewUser(ActionEvent event) {
		this.userModel.goToAddNew(event);
	}

	@FXML
	public void removeUser() throws SQLException {
		ViewUserModel selectedUser = users.getSelectionModel().getSelectedItem();
		if(selectedUser != null) {
			String username = selectedUser.getUsername();		
			if(this.userModel.remove(username)) {
				ObservableList<ViewUserModel> all, single;
				all = users.getItems();
				single = users.getSelectionModel().getSelectedItems();
				single.forEach(all::remove);
			}
		}				
	}
	
	@FXML
	public void editUser(ActionEvent event) throws IOException {
		ViewUserModel selectedUser = users.getSelectionModel().getSelectedItem();
		if(selectedUser != null) {			
			Stage stage = new Stage();
			FXMLLoader loader =  new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/view/EditUser.fxml").openStream());
			Scene scene = new Scene(root);
			EditUserController editController = (EditUserController)loader.getController();			
			editController.getUser(selectedUser);
			stage.setScene(scene);
			stage.show();
			((Node)(event.getSource())).getScene().getWindow().hide();
		}
	}
		
	@FXML
	public void toHome(ActionEvent e) {
		((Node)(e.getSource())).getScene().getWindow().hide();
		
	}
	
}