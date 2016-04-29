package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.auth.Authentication;
import backend.auth.Customer;
import frontend.LogInPane;
import frontend.TransactionListPane.printTranList;
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

public class TransactionListPane extends GridPane {

	public AnchorPane warningContainer = new AnchorPane();
	public Label warning = new Label("this is junk ");
	public ArrayList<Account> getAccounts = Authentication.getLoggedInUser().getUserAccounts();
	
	
	public Label Header = new Label("View Transaction List");
	//-
	
	public TextField accountNum = new TextField();
	
	public ChoiceBox<String> whichAccount;



	public AnchorPane makeTransactionListContainer = new AnchorPane();

	public Button Btn_TransactionList = new Button("View List");

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
	
	public void setWhichAccountAttributes() {
		if(getAccounts.size()==2){
			 whichAccount= new ChoiceBox<String>(FXCollections.observableArrayList(getAccounts.get(0).accountNumber, getAccounts.get(1).accountNumber));
		
		}
		else if(getAccounts.size()==1)
		{
			 whichAccount= new ChoiceBox<String>(FXCollections.observableArrayList(getAccounts.get(0).accountNumber));
		}
		else{
			warningContainer.setVisible(true);
			warning.setText("Sorry, you do not have any accounts.");
		}
		this.whichAccount.setValue(null);
	}
	
	public void setaccountNumAttributes() {
		this.accountNum.setPromptText("Enter Account Number");
	}


	public void setTransListBtn() {

		this.makeTransactionListContainer.getChildren().add(this.Btn_TransactionList);

		AnchorPane.setRightAnchor(this.Btn_TransactionList, 0.0);
		AnchorPane.setLeftAnchor(this.Btn_TransactionList, 0.0);

		this.Btn_TransactionList.setOnAction(new printTranList());
	}


	protected class printTranList implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			String accNumStr = whichAccount.getValue();
			if(!accNumStr.isEmpty()){
				read(accNumStr);
			}	
			else{
				warningContainer.setVisible(true);
				warning.setText("You must enter a value!");
			}
		}



	}






	//-------
	public TransactionListPane() {
		this.setWarningAttributes();
		this.setBaseAttributes();
		this.setTransListBtn();
		this.setWhichAccountAttributes();
		this.setHeaderAttributes();
		this.add(this.Header, 0, 0,2,1);
		this.add(this.warningContainer, 0, 0, 2, 1);
		this.add(this.whichAccount, 1, 4);
		this.add(this.makeTransactionListContainer, 1, 6);		
	}

	public void setBaseAttributes() {
		this.setPadding(new Insets(15));
		this.setHgap(10);
		this.setVgap(10);
	}
	
	public void read(String num){
		try 
		{   // The name of the file which we will read from
			String filename = num + ".txt";

			// Prepare to read from the file, using a Scanner object
			File file = new File(filename);
			Scanner in = new Scanner(file);
			//in.useDelimiter(" \t\t ");
			//in.useDelimiter("\n");

				
			int count =0;
			int j =7;

			// Read each line until end of file is reached
			this.getChildren().clear();
			while (in.hasNextLine())
			{
				// Read an entire line, which contains all the details for 1 transaction
					String line = in.nextLine();
					String spLine[] = line.split("\t\t");
					for(int i=0; i<spLine.length; i++){
						this.add(new Label(spLine[i]), i, j);
					}
					j++;

				count++;

				// Make a Scanner object to break up this line into parts
			}
			in.close();

		}catch (FileNotFoundException e)
		{
			warningContainer.setVisible(true);
			warning.setText("Invalid entry!");

		} 

	}
}