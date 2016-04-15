package frontend;

import java.io.IOException;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.auth.Customer;
import backend.Settings;
import backend.auth.Authentication;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
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
/**
 * CreateAccountForm GUI Creates a new account for a user based off input.
 * Customer must input correct username and password. Initial account deposit has to be x >= $50.
 * 
 * @author Mason Cross (cross1m)
 * @since April 14, 2016
 * 
 */
public class CreateAccountForm extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("Sorry, could not make that account.");
	public AnchorPane titleAnchor = new AnchorPane();

	public TextField usernameField = new TextField();
	public PasswordField passwordField = new PasswordField();
	public TextField initialAmountField = new TextField();

	public ChoiceBox<String> accountField = new ChoiceBox<String>(
			FXCollections.observableArrayList("Checking", "Savings"));

	public Button submitButton = new Button("Submit");
	AnchorPane submitAnchor = new AnchorPane();
	AnchorPane accountAnchor = new AnchorPane();

	public Label title = new Label("Create a new Account");

	public void setTitleAttributes() {
		this.titleAnchor.getChildren().add(title);
		AnchorPane.setRightAnchor(warning, 0.0);
		AnchorPane.setLeftAnchor(warning, 0.00);
		this.title.setAlignment(Pos.CENTER);
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

	public void setUsernameAttributes() {
		this.usernameField.setPromptText("Username");
	}

	public void setInitialAmountAttributes() {
		this.initialAmountField.setPromptText("Enter your initial amount");

		this.accountAnchor.getChildren().add(this.accountField);
		AnchorPane.setRightAnchor(this.accountField, 0.0);
		AnchorPane.setLeftAnchor(this.accountField, 0.0);

		this.initialAmountField.setOnKeyPressed(e -> {
			if (!verifyUsername(usernameField.getText())) {
				usernameField.setStyle("-fx-text-box-border: red;");
				usernameField.setText("");
				usernameField.setPromptText("Invalid Username");
			} else {
				usernameField.setStyle("");
			}
		});
	}

	public boolean verifyUsername(String username) {
		if (Authentication.userExists(username)) {
			return true;
		} else {
			return false;
		}
	}

	public void setPasswordAttributes() {
		this.passwordField.setPromptText("Password");
		this.passwordField.setOnKeyPressed(e -> {
			if (!verifyUsername(usernameField.getText())) {
				usernameField.setStyle("-fx-text-box-border: red;");
				usernameField.setText("");
				usernameField.setPromptText("Invalid Username");
			} else {
				usernameField.setStyle("");
			}
		});
	}

	public void setSubmitAttributes() {
		submitAnchor.getChildren().add(this.submitButton);
		this.submitButton.setOnAction(new accountForm());
		AnchorPane.setLeftAnchor(this.submitButton, 0.0);
		AnchorPane.setRightAnchor(this.submitButton, 0.0);
	}

	protected class accountForm implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			String username = usernameField.getText();
			String password = passwordField.getText();
			String accountType = (String) accountField.getValue();
			String initialAmount = initialAmountField.getText();

			if (this.isValid(username, password, accountType, initialAmount)) {
				try {
					Double amountToDeposit = Double.parseDouble(initialAmount);
					boolean valid = true;
					if (!Authentication.getUser(username).checkPassword(password)) {
						warning.setText("Please enter a valid password!");
						warningContainer.setVisible(true);
						valid = false;
					} else {
						warningContainer.setVisible(false);

					}

					if (amountToDeposit < 50) {
						warning.setText("Initial amount needs to be $50 or more");
						warningContainer.setVisible(true);
						valid = false;
					}

					if (valid) {
						if (accountType.equals("Savings")) {
							Authentication.getUser(username).addAccount(SavingsAccount.class);
							Authentication.getUser(username).getAccount(SavingsAccount.class)
									.doDeposit(amountToDeposit);
							this.clearForm();
							this.alertCreated(accountType);
						}
						if (accountType.equals("Checking")) {
							Authentication.getUser(username).addAccount(CheckingAccount.class);
							Authentication.getUser(username).getAccount(CheckingAccount.class)
									.doDeposit(amountToDeposit);
							this.clearForm();
							this.alertCreated(accountType);
						}

					}
				} catch (UserNotFoundException e) {
					clearForm();
					warning.setText("Enter a valid Username!");
					warningContainer.setVisible(true);
				} catch (AccountAlreadyStoredException e) {
					clearForm();
					warning.setText("Account already exists!");
					warningContainer.setVisible(true);

				}
			}

		}

		public void alertCreated(String accountType) {
			clearForm();
			warning.setText(accountType + " successfully created!");
			warningContainer.setStyle("-fx-background-color: rgba(0, 255, 0, 0.35); -fx-background-radius: 2;");
			warningContainer.setVisible(true);
			FadeTransition ft = new FadeTransition(Duration.millis(2000), warningContainer);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.play();

		}

		public void clearForm() {
			usernameField.setText("");
			passwordField.setText("");
			initialAmountField.setText("");
			accountField.setValue(null);
		}

		public void clearBorders() {
			accountField.setStyle("-fx-text-box-border: LightGray;");
			usernameField.setStyle("-fx-text-box-border: LightGray;");
			passwordField.setStyle("-fx-text-box-border: LightGray;");
		}

		public boolean isValid(String username, String password, String accountType, String initialAmount) {
			String regex = "^[0-9]+\\.?[0-9]*$";
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

			if (accountType == null) {
				accountField.setStyle("-fx-choice-box-border: red;");
				valid = false;
			}
			if (initialAmount.isEmpty() || !initialAmount.matches(regex)) {
				initialAmountField.setStyle("-fx-text-box-border: red;");
				valid = false;
			}
			return valid;
		}
	}

	public CreateAccountForm() {

		this.add(titleAnchor, 0, 0, 2, 1);
		this.add(warningContainer, 0, 0, 2, 1);

		this.add(usernameField, 1, 2);
		this.add(passwordField, 1, 3);
		this.add(initialAmountField, 1, 4);

		this.add(accountAnchor, 1, 5);
		this.add(submitAnchor, 1, 7);

		this.setTitleAttributes();
		this.setWarningAttributes();
		this.setPasswordAttributes();
		this.setUsernameAttributes();
		this.setSubmitAttributes();
		this.setInitialAmountAttributes();

		this.setPadding(new Insets(25, 120, 50, 120));
		this.setHgap(20);
		this.setVgap(20);
	}
}
