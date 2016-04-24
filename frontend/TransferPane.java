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

public class TransferPane extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");

	public Label Header = new Label("Transfer");
	//-
	public TextField toAccount = new TextField();
	public TextField fromAccount = new TextField();

	public AnchorPane makeTransferContainer = new AnchorPane();

	public Button Btn_Transfer = new Button("Transfer");

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
	public void setFromAccountAttributes() {
		this.fromAccount.setPromptText("From Account");
	}

	public void setToAccountAttributes() {
		this.toAccount.setPromptText("To Account");
	}

	public void setTransferBtn() {

		this.makeTransferContainer.getChildren().add(this.Btn_Transfer);

		AnchorPane.setRightAnchor(this.Btn_Transfer, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_Transfer, 0.0);

		this.Btn_Transfer.setOnAction(new actionLogIn());
	}


	protected class actionLogIn implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			
			String account = toAccount.getText();
			String amount = fromAccount.getText();

			if(!amount.isEmpty() && !account.isEmpty())
			{
				//need stuff here
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
	public TransferPane() {
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setFromAccountAttributes();
		this.setToAccountAttributes();
		this.setTransferBtn();
		this.setHeaderAttributes();

		this.add(this.Header, 0, 0,2,1);
		this.add(this.warningContainer, 0, 0, 2, 1);
		this.add(this.toAccount, 1, 2);
		this.add(this.fromAccount, 1, 3);
		this.add(this.makeTransferContainer, 1, 6);		
	}

	public void setBaseAttributes() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
}
