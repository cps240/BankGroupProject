package frontend;

import java.io.IOException;

import backend.Settings;
import backend.auth.Authentication;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import main.App;

public class CreateUserForm extends GridPane {
	
	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("Sorry, could not add this.");

	public TextField firstNameField = new TextField();
	
	public TextField phoneField = new TextField();
	
	public AnchorPane genderContainer = new AnchorPane(); //contains the gender choice box.
	public ChoiceBox<String> genderField = new ChoiceBox<String>(FXCollections.observableArrayList("Male", "Female"));
	
	public TextField lastNameField = new TextField();
	
	public TextField usernameField = new TextField();
	
	public PasswordField passwordField = new PasswordField();
	public PasswordField passwordConfirmField = new PasswordField();

	public AnchorPane submitContainer = new AnchorPane(); //contains the submit button.
	public Button submitButton = new Button("Submit");
	
	public Label title = new Label("Register");
	
	public AnchorPane loginContainer = new AnchorPane(); //contains the login button.
	public Button loginButton = new Button("Login last created user");
	
	public void setTitleAttributes() {
		this.title.setFont(new Font("Tahoma", 28));
		this.setMargin(this.title, new Insets(0, 0, -15, 120));
		this.title.setTextFill(Color.CADETBLUE);
	}
	
	public void setWarningAttributes() {
		this.warningContainer.getChildren().add(this.warning);
		AnchorPane.setRightAnchor(this.warning, 0.0);
		AnchorPane.setLeftAnchor(this.warning, 0.0);
		
		this.warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
		this.warningContainer.setVisible(false);
		
		this.warning.setAlignment(Pos.CENTER);
	}
	
	public void setFirstNameAttributes() {
		this.firstNameField.setPromptText("FirstName");
	}
	
	public void setLastNameAttributes() {
		this.lastNameField.setPromptText("LastName");
	}
	
	public void setGenderAttributes() {
		this.genderContainer.getChildren().add(this.genderField);
		AnchorPane.setRightAnchor(this.genderField, 0.0);
		AnchorPane.setLeftAnchor(this.genderField, 0.0);
	}
	
	public void setPhoneAttributes() {
		this.phoneField.setPromptText("(111) 111 - 1111");
	}
	
	public void setUsernameAttributes() {
		this.usernameField.setPromptText("Username");
		
		this.usernameField.setOnKeyReleased(e -> {
			//check to see if the username is valid
			if (!this.usernameIsValid(this.usernameField.getText())) {
				this.warning.setText("Username contains invalid characters.");
				this.warningContainer.setVisible(true);
			} else if (!this.usernameExists(this.usernameField.getText())) {
				this.warning.setText("A user with this info already exists.");
				this.warningContainer.setVisible(true);
			} else {
				this.warningContainer.setVisible(false);
			}
		});
	}
	
	public boolean usernameExists(String username) {
		if (Authentication.userExists(username)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean usernameIsValid(String username) {
		for (Character c : username.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				continue;
			} else if (Character.isDigit(c)) {
				continue;
			} else if (c == '_' || c == '-' || c == '!' || c == '#' || c == '$' || c == '&') {
				continue;
			} else {
				return false;
			}
		}
		//if loop finishes, then all characters pass so password is ok.
		return true;
	}
	
	public void setPasswordAttributes() {
		this.passwordField.setPromptText("Password");
		this.passwordConfirmField.setPromptText("Confirm Password");
		
		this.passwordField.setOnKeyReleased(e -> {
			//check to see if the password is valid
			if (!this.passwordIsValid(this.passwordField.getText())) {
				this.warning.setText("Password contains invalid characters.");
				this.warningContainer.setVisible(true);
			} else {
				this.warningContainer.setVisible(false);
			}
		});
	}
	
	public boolean passwordIsValid(String password) {
		for (Character c : password.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				continue;
			} else if (Character.isDigit(c)) {
				continue;
			} else if (c == '_' || c == '-' || c == '!' || c == '#' || c == '$' || c == '&') {
				continue;
			} else {
				return false;
			}
		}
		//if loop finishes, then all characters pass so password is ok.
		return true;
	}
	
	public void setSubmitAttributes() {
		this.submitContainer.getChildren().add(this.submitButton);
		AnchorPane.setRightAnchor(this.submitButton, 0.0);
		AnchorPane.setLeftAnchor(this.submitButton, 0.0);
		
		this.submitButton.setOnAction(new SubmitForm());
	}
	
	protected class SubmitForm implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			String username = usernameField.getText();
			String password = passwordField.getText();
			String passwordConfirm = passwordConfirmField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String gender = (String) genderField.getValue();
			String phoneNumber = phoneField.getText();
			
			if (this.isValid(username, password, passwordConfirm, firstName, lastName, gender, phoneNumber)) {
				try {
					boolean valid = true;
					if (!password.equals(passwordConfirm)) {
						valid = false;
						passwordField.setStyle("-fx-text-box-border: red;");
						warning.setText("Both password fields must match.");
						warningContainer.setVisible(true);
					}
					if (!passwordIsValid(password)) {
						valid = false;
						passwordField.setStyle("-fx-text-box-border: red;");
						warning.setText("Password cotains invalid characters.");
						warningContainer.setVisible(true);
					}
					if (!usernameIsValid(username)) {
						valid = false;
						usernameField.setStyle("-fx-text-box-border: red;");
						warning.setText("Username contains invalid characters.");
						warningContainer.setVisible(true);
					}
					if (valid) {
						Authentication.addUser(username, password, firstName, lastName, gender, phoneNumber);
						this.clearForm();
						this.alertSaved(firstName + " " + lastName);
						
						/*
						 * prompt user to login to the newly created user.
						 */
						promptUserLogin(username, passwordConfirm);
					}
				} catch (UserAlreadyStoredException e) {
					warning.setText("A user with this info already exists.");
					warningContainer.setVisible(true);
				} catch (UserNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				this.alertToWarning();
				warning.setText("Please complete the form below.");
				warningContainer.setVisible(true);
			}
		}
		
		public void alertSaved(String fullname) {
			warning.setText(fullname + " has been saved!");
			warningContainer.setStyle("-fx-background-color: rgba(0, 255, 0, 0.35); -fx-background-radius: 2;");
			warningContainer.setVisible(true);
			FadeTransition ft = new FadeTransition(Duration.millis(2000), warningContainer);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.play();
		}
		
		public void alertToWarning() {
			warningContainer.setVisible(false);
			warningContainer.setOpacity(1.0);
			warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
		}
		
		public void clearForm() {
			usernameField.setText("");
			passwordField.setText("");
			passwordConfirmField.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			genderField.setValue(null);
			phoneField.setText("");
		}
		
		public void clearBorders() {
			phoneField.setStyle("-fx-text-box-border: LightGray;");
			genderField.setStyle("-fx-text-box-border: LightGray;");
			lastNameField.setStyle("-fx-text-box-border: LightGray;");
			firstNameField.setStyle("-fx-text-box-border: LightGray;");
			usernameField.setStyle("-fx-text-box-border: LightGray;");
			passwordField.setStyle("-fx-text-box-border: LightGray;");
			passwordConfirmField.setStyle("-fx-text-box-border: LightGray;");
		}
		
		public boolean isValid(String username, String password, String passwordConfirm,
				String firstName, String lastName, String gender, String phoneNumber) {
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
			if (passwordConfirm.isEmpty()) {
				passwordConfirmField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			if (firstName.isEmpty()) {
				firstNameField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			if (lastName.isEmpty()) {
				lastNameField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			if (gender == null) {
				genderField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			if (phoneNumber.isEmpty()) {
				phoneField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}

			return valid;
		}
		
	}
	
	/**
	 * This will add the login button to the createUserForm after
	 * a user has been created. If pressed, it will login the last created user.
	 * the button will only be displayed if a user has been created.
	 */
	public void promptUserLogin(String username, String password) {
		this.loginButton.setOnAction(e -> {
			try {
				Authentication.attemptLogin(username, password);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//should never happen cause the user was just created.
				e1.printStackTrace();
			}
			App.mainScene.setRoot(new BasePane());
			App.primaryStage.sizeToScene();
		});
		this.loginContainer.getChildren().add(this.loginButton);
		AnchorPane.setRightAnchor(this.loginButton, 0.0);
		AnchorPane.setLeftAnchor(this.loginButton, 0.0);
		this.add(this.loginButton, 0, 6);
	}
	
	public CreateUserForm() {
		this.add(this.title, 0, 0, 2, 1);
		this.add(this.warningContainer, 0, 1, 2, 1);
		
		this.add(this.firstNameField, 0, 2);
		this.add(this.lastNameField, 1, 2);
		
		this.add(this.phoneField, 0, 3);
		this.add(this.genderContainer, 1, 4); //this will contain the gender box.
		
		this.add(this.usernameField, 1, 3);
		this.add(this.passwordField, 0, 4);
		this.add(this.passwordConfirmField, 0, 5);

		this.add(this.submitContainer, 1, 5); //this will contain the submit button.
		
		this.setTitleAttributes();
		this.setBaseAttributes();
		this.setWarningAttributes();
		this.setLastNameAttributes();
		this.setFirstNameAttributes();
		this.setGenderAttributes();
		this.setPhoneAttributes();
		this.setUsernameAttributes();
		this.setPasswordAttributes();
		this.setSubmitAttributes();
	}
	
	public void setBaseAttributes() {
		this.setPadding(new Insets(25,120,50,120));
		this.setHgap(20);
		this.setVgap(20);
	}
}
