package frontend;

import main.App;
import backend.auth.Authentication;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LogInPane extends GridPane {
	
	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");
 
	
	public TextField usernameField = new TextField();
	public PasswordField passwordField = new PasswordField();

	public AnchorPane logInContainer = new AnchorPane();
	public AnchorPane newUserContainer = new AnchorPane(); 
	
	public Button Btn_LogIn = new Button("Log In");
	public Button Btn_NewUser = new Button("New User");
	
	public void setWarningAttributes() {
		this.warningContainer.getChildren().add(this.warning);
		AnchorPane.setRightAnchor(this.warning, 0.0);
		AnchorPane.setLeftAnchor(this.warning, 0.0);
		
		this.warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
		this.warningContainer.setVisible(false);
		this.warning.setAlignment(Pos.CENTER);
	}
	public void setUsernameAttributes() {
		this.usernameField.setPromptText("Username");
	}

	public void setPasswordAttributes() {
		this.passwordField.setPromptText("Password");
	}

	public void setNewUserAttributes() {
		this.logInContainer.getChildren().add(this.Btn_NewUser);
		
		AnchorPane.setRightAnchor(this.Btn_NewUser, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_NewUser, 0.0);
		
	}
	
	public void setlogInbtn() {
		
		this.newUserContainer.getChildren().add(this.Btn_LogIn);
		
		AnchorPane.setRightAnchor(this.Btn_LogIn, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_LogIn, 0.0);

		this.Btn_LogIn.setOnAction(new actionLogIn());
	}
	
	protected class actionLogIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String username = usernameField.getText();
			String password = passwordField.getText();

			if(!username.isEmpty() && !password.isEmpty())
			{
				try {
					System.out.println(1);
					Authentication.attemptLogin(username, password);
					System.out.println("hey");
					App.pane = new BasePane();
				} catch (UserNotFoundException e) {
					warning.setText("Could not find User");
					warningContainer.setVisible(true);
				} catch (PasswordMissmatchException e) {
					warning.setText("Password incorrect");
					warningContainer.setVisible(true);
				}
			}
			else{
				warningContainer.setVisible(true);
				warning.setText("One or both fields are empty");
			}
		}
		
		
		public void alertToWarning() {
			warningContainer.setVisible(false);
			warningContainer.setOpacity(1.0);
			warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
		}

		public void clearForm() {
			usernameField.setText("");
			passwordField.setText("");
		}

		public void clearBorders() {
			usernameField.setStyle("-fx-text-box-border: LightGray;");
			passwordField.setStyle("-fx-text-box-border: LightGray;");
		}

		public boolean isValid(String username, String password, String firstName,
				String lastName, String gender, String phoneNumber) {
			boolean valid = true;
			this.clearBorders();
			if (username.isEmpty()) {
				usernameField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			if (password.isEmpty()) {
				passwordField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}


			return valid;
		}

	}

	public LogInPane() {
		this.setNewUserAttributes();
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setUsernameAttributes();
		this.setPasswordAttributes();
		this.setlogInbtn();
		
		
		this.add(this.warningContainer, 0, 0, 2, 1);
		this.add(this.usernameField, 1, 2);
		this.add(this.passwordField, 1, 3);
		this.add(this.logInContainer, 1, 6);		
		this.add(this.newUserContainer, 1, 5);
	}

	public void setBaseAttributes() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
}
