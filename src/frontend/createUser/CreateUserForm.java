package frontend.createUser;

import java.io.IOException;
import java.util.ArrayList;

import backend.auth.Authentication;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CreateUserForm extends GridPane {
	
	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("Sorry, could not add this.");

	public TextField firstNameField = new TextField();
	
	public TextField phoneField = new TextField();
	
	public AnchorPane genderContainer = new AnchorPane(); //contains the gender choice box.
	public ChoiceBox genderField = new ChoiceBox<String>(FXCollections.observableArrayList("Male", "Female"));
	
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
				} catch (UserAlreadyStoredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public boolean isValid(String username, String password, String firstName,
								String lastName, String gender, String phoneNumber) {
			if (username.isEmpty()) {
				return false;
			} else if (password.isEmpty()) {
				return false;
			} else if (firstName.isEmpty()) {
				return false;
			} else if (lastName.isEmpty()) {
				return false;
			} else if (gender.isEmpty()) {
				return false;
			} else if (phoneNumber.isEmpty()) {
				return false;
			} else {
				return true;
			}
		}
		
	}
	
	public CreateUserForm() {
		this.add(this.warningContainer, 0, 0);
		
		this.add(this.firstNameField, 0, 1);
		this.add(this.lastNameField, 1, 1);
		
		this.add(this.phoneField, 0, 2);
		this.add(this.genderContainer, 0, 3); //this will contain the gender box.
		
		this.add(this.usernameField, 1, 2);
		this.add(this.passwordField, 1, 3);

		this.add(this.submitContainer, 1, 4); //this will contain the submit button.
		
		this.setWarningAttributes();
		this.setLastNameAttributes();
		this.setFirstNameAttributes();
		this.setGenderAttributes();
		this.setPhoneAttributes();
		this.setUsernameAttributes();
		this.setPasswordAttributes();
		this.setSubmitAttributes();
	}
}
