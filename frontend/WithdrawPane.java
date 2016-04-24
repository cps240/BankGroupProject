package frontend;

import main.App;

import java.text.ParseException;
import java.util.function.DoubleToLongFunction;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.auth.Authentication;
import backend.errors.AccountNotFoundException;
import backend.errors.LowAccountBalanceException;
import backend.storage.Storage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WithdrawPane  extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");

	public Label Header = new Label("Withdraw");

	public ChoiceBox<Class> fromAccount = new ChoiceBox<Class>(FXCollections.observableArrayList(CheckingAccount.class, SavingsAccount.class));

	public TextField amountField = new TextField();

	public AnchorPane actionContainer = new AnchorPane();

	public Button Btn_Withdraw = new Button("Withdraw");

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
	public void setToAccountAttributes() {
		this.fromAccount.setValue(null);
	}

	public void setAmountAttributes() {
		this.amountField.setPromptText("Amount to Withdraw");
	}

	public void setWithdrawBtnAttributes() {

		this.actionContainer.getChildren().add(this.Btn_Withdraw);

		AnchorPane.setRightAnchor(this.Btn_Withdraw, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_Withdraw, 0.0);

		this.Btn_Withdraw.setOnAction(new actionLogIn());
	}


	protected class actionLogIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {

			Class accountType = fromAccount.getValue();
			String amount = amountField.getText();
			double amountToAdd;
			if(!amount.isEmpty() && accountType != null)
			{
				try {
					Account acct = Authentication.getLoggedInUser().getAccount(accountType);
					acct.doWithdrawal(Double.parseDouble(amount));

					warning.setText("Transaction complete");
					warningContainer.setStyle("-fx-background-color: rgba(0, 255, 0, 0.35); -fx-background-radius: 2;");

				} catch (NumberFormatException e) {
					warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
					warningContainer.setVisible(true);
					warning.setText("Amount Invalid");
				} catch (LowAccountBalanceException e) {
					warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
					warningContainer.setVisible(true);
					warning.setText("You have insufficient funds.");
				}
			}else{

				warningContainer.setStyle("-fx-background-color: rgba(255, 0, 0, 0.35); -fx-background-radius: 2;");
				warningContainer.setVisible(true);
				warning.setText("One or both fields are empty");
			}
		}
	}



	//-------
	public WithdrawPane() {
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setToAccountAttributes();
		this.setAmountAttributes();
		this.setWithdrawBtnAttributes();
		this.setHeaderAttributes();

		this.add(this.Header, 0, 0,2,1);
		this.add(this.warningContainer, 0, 0, 2, 1);
		this.add(this.fromAccount, 1, 2);
		this.add(this.amountField, 1, 3);
		this.add(this.actionContainer, 1, 6);		
	}

	public void setBaseAttributes() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
}
