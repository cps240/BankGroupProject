package frontend;

import java.io.IOException;

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
import javafx.util.Duration;

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

	public AnchorPane submitContainer = new AnchorPane(); //contains the submit button.
	public Button submitButton = new Button("Submit");
	
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
	}
	
	public void setPasswordAttributes() {
		this.passwordField.setPromptText("Password");
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
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String gender = (String) genderField.getValue();
			String phoneNumber = phoneField.getText();
			
			if (this.isValid(username, password, firstName, lastName, gender, phoneNumber)) {
				try {
					Authentication.addUser(username, password, firstName, lastName, gender, phoneNumber);
					this.clearForm();
					this.alertSaved(firstName + " " + lastName);
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
	
	public CreateUserForm() {
		this.add(this.warningContainer, 0, 0, 2, 1);
		
		this.add(this.firstNameField, 0, 1);
		this.add(this.lastNameField, 1, 1);
		
		this.add(this.phoneField, 0, 2);
		this.add(this.genderContainer, 0, 3); //this will contain the gender box.
		
		this.add(this.usernameField, 1, 2);
		this.add(this.passwordField, 1, 3);

		this.add(this.submitContainer, 1, 4); //this will contain the submit button.
		
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
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
}
