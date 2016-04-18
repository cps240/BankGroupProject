package frontend;

import main.App;
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

public class DepositPane extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");

	public Label Header = new Label("Deposit");
//-
	public ChoiceBox<String> toAccount = new ChoiceBox<String>(FXCollections.observableArrayList("Checking", "Savings"));

	public TextField amountField = new TextField();

	public AnchorPane actionContainer = new AnchorPane();

	public Button Btn_Deposit = new Button("Deposit");

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
		this.toAccount.setValue(null);
	}

	public void setAmountAttributes() {
		this.amountField.setPromptText("Amount to Deposit");
	}

	public void setDepositBtnAttributes() {

		this.actionContainer.getChildren().add(this.Btn_Deposit);

		AnchorPane.setRightAnchor(this.Btn_Deposit, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_Deposit, 0.0);

		this.Btn_Deposit.setOnAction(new actionLogIn());
	}


	protected class actionLogIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			String account = toAccount.getValue();
			String amount = amountField.getText();

			if(!amount.isEmpty() && account != null)
			{
				
			}
			else{
				warningContainer.setVisible(true);
				warning.setText("One or both fields are empty");
			}
		}



	}


	//-------
	public DepositPane() {
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setToAccountAttributes();
		this.setAmountAttributes();
		this.setDepositBtnAttributes();
		this.setHeaderAttributes();

		this.add(this.Header, 0, 0,2,1);
		this.add(this.warningContainer, 0, 0, 2, 1);
		this.add(this.toAccount, 1, 2);
		this.add(this.amountField, 1, 3);
		this.add(this.actionContainer, 1, 6);		
	}

	public void setBaseAttributes() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
}
