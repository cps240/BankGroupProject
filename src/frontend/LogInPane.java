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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LogInPane extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");

	public Label Header = new Label("Log In");

	public TextField usernameField = new TextField();
	public PasswordField passwordField = new PasswordField();

	public AnchorPane logInContainer = new AnchorPane();
	public AnchorPane newUserContainer = new AnchorPane(); 

	public Button Btn_LogIn = new Button("Log In");
	public Button Btn_NewUser = new Button("New User");

	public void setHeaderAttributes() {
		this.Header.setFont(new Font("Tahoma", 28));
		LogInPane.setMargin(this.Header, new Insets(0, 0, -15, 120));
		this.Header.setTextFill(Color.CADETBLUE);
	}

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

		this.Btn_NewUser.setOnAction(new newUser());
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
					//add screen change here
					
					
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



	}


	protected class newUser implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//Add the file change here
			System.out.println("btn good");
		}

	}





	//-------
	public LogInPane() {
		this.setNewUserAttributes();
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setUsernameAttributes();
		this.setPasswordAttributes();
		this.setlogInbtn();
		this.setHeaderAttributes();

		this.add(this.Header, 0, 0,2,1);
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
