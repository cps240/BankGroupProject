package frontend;



import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.auth.Authentication;
import frontend.CreateUserForm.SubmitForm;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CustomerMainPage extends GridPane {

	
	public AnchorPane AccountListContainer = new AnchorPane();
	

	public Label title = new Label("Account");

	public Label[] checkingAccountDisplay = null;
	public Label[] savingsAccountDisplay = null;
	
	public AnchorPane transContainer = new AnchorPane();
	public Button translog = new Button("Transaction Log");
	
	public void setTitleAttributes() {
		this.title.setFont(new Font("Tahoma", 28));
		this.setMargin(this.title, new Insets(0, 0, -15, 120));
		this.title.setTextFill(Color.CADETBLUE);
	}
	public void setTranslog(){
			this.transContainer.getChildren().add(this.translog);
			AnchorPane.setRightAnchor(this.translog, 0.0);
			AnchorPane.setLeftAnchor(this.translog, 0.0);
			
			this.translog.setOnAction(e -> {
				((BasePane) this.getParent()).setContent(new TransactionListPane());
			});
		
		
	}
	
	
	public void getAccountDisplay(){
		if(Authentication.getLoggedInUser().getUserAccounts().size() == 0) {
			this.checkingAccountDisplay = new Label[2];
			this.checkingAccountDisplay[0] = new Label("There is no checking account");
			this.checkingAccountDisplay[1] = new Label("go to add account if needed");
			
		} else {
			for(Account acc: Authentication.getLoggedInUser().getUserAccounts()){
				String type = acc.getClass().getSimpleName().replace("Account", "");
				String accNum = acc.getAccountNumber();
				double accBal = acc.getBalance();
				
				if (acc instanceof CheckingAccount) {
					this.checkingAccountDisplay = new Label[2];
					this.checkingAccountDisplay[0] = new Label(type + ": " + accNum);
					this.checkingAccountDisplay[1] = new Label("$ " + accBal);
				}
				else if(acc instanceof SavingsAccount) {
					this.savingsAccountDisplay = new Label[2];
					this.savingsAccountDisplay[0] = new Label(type + ": " + accNum);
					this.savingsAccountDisplay[1] = new Label("$ " + accBal);
				}
			}
		}
	}
	
	public CustomerMainPage() {
		this.getAccountDisplay();
		this.setTitleAttributes();
		this.setTranslog();
		
		this.setHgap(20);
		this.setVgap(20);
		this.setPadding(new Insets(50, 120, 50, 120));
		this.add(this.title, 0, 0, 2, 1);
		this.add(this.checkingAccountDisplay[0], 0, 1);
		this.add(this.checkingAccountDisplay[1], 1, 1);
		if(this.savingsAccountDisplay != null){
			this.add(this.savingsAccountDisplay[0], 0, 2);
			this.add(this.savingsAccountDisplay[1], 1, 2);
			this.add(this.transContainer, 1, 3);
		} else {
			this.add(this.transContainer, 1, 2);
		}
	}
}
