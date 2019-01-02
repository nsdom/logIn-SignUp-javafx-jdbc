package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainViewController implements Initializable {

	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField password;

	@FXML
	private Button btLogIn;
	
	@FXML
	private Button btSignUp;
	
	@FXML
	private Label labelError;
	
	
	@FXML
	public void onBtLogIn() {
		System.out.println("Loged In");
	}
	
	@FXML
	public void onBtSignUp() {
		System.out.println("Sign Up");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	

}
