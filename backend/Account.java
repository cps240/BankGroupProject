package backend;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import backend.auth.Customer;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserNotAuthenticatedException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import backend.errors.LowAccountBalanceException;
import backend.storage.Storage;

public abstract class Account {
	
	protected static Double balance;
	protected Customer owner;
	public String accountNumber;
	public ArrayList<transaction> transactions = new ArrayList<>();
	
	public META META;
	
	/**
	 * will contain behavioral attributes such as minimum balance allowed and other
	 * guidelines.
	 * @author Ian
	 *
	 */
	public class META {
		
		/**
		 * Accounts cannot go below this balance.
		 */
		public double MIN_BAL_ALLOWED = 50.00;
	}
	
	public Account(Customer _owner) throws UserNotFoundException {
		if (_owner.userId != null) {
			this.owner = _owner;
		} else {
			throw new UserNotFoundException(_owner.getUsername());
		}
	}
	
	public Customer getOwner() {
		return this.owner;
	}
	
	/**
	 * Use this to pass to {@code Settings.storage.folder.files} as the key to return the path to the file
	 * in which this account is stored.
	 * @return
	 */
	public String getAccountFolderKey() {
		String key = this.getOwner().getAccountsFolderKey() + ":acc_" + this.accountNumber;
		return key;
	}
	
	public String pathToAccountFile() {
		String path = this.getOwner().pathToCustomerFolder() + this.getClass().getSimpleName().toLowerCase() + "s/acct_" + this.accountNumber + ".json";
		return path;
	}
	
	/**
	 * sets the initial account number for this account.
	 * @throws AccountAlreadyStoredException 
	 */
	public void initializeAccount() throws AccountAlreadyStoredException {
		if (this.accountNumber == null) {
			this.accountNumber = Storage.nextAccountId;
			Storage.nextAccountId = Storage.incrementAccountId();
			Storage.accountRelationships.put(this.accountNumber, this.getOwner().userId);
		} else {
			throw new AccountAlreadyStoredException(this);
		}
		if (this.balance == null) {
			this.balance = 0.0;
		}
		initFile();
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	/**
	 * set the balance to a new amount.
	 * @param _balance
	 */
	public void setBalance(double _balance) {
		this.balance = _balance;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * withdrawal money.
	 * @param _amount
	 * @throws LowAccountBalanceException
	 */
	public void doWithdrawal(double _amount) throws LowAccountBalanceException {
		double newBal = this.getBalance() - _amount;
		this.setBalance(newBal);
		new transaction(new Date(), this.accountNumber, "Withdraw", _amount, this.balance);

	}
	
	/**
	 * deposit money.
	 * @param _amount
	 */
	public void doDeposit(double _amount) {
		double newBal = this.getBalance() + _amount;
		this.setBalance(newBal);
		new transaction(new Date(), this.accountNumber, "Deposit", _amount, this.balance);

	}
	
	/**
	 * transfer money FROM one account TO another. must have correct password
	 * @param _fromAccount - account being pulled from
	 * @param _toAccount - account being deposited into
	 * @param _amount - amount of money being transferred
	 * @throws LowAccountBalanceException
	 */
	public static void doTransfer(Account _fromAccount, Account _toAccount, double _amount) throws LowAccountBalanceException {
		_fromAccount.doWithdrawal(_amount);
		_toAccount.doDeposit(_amount);
		new transaction(new Date(), _toAccount.accountNumber, "Transfer(W)", _amount, _fromAccount.balance);
		new transaction(new Date(), _fromAccount.accountNumber, "Transfer(D)", _amount, _toAccount.balance);


	}
	
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("type", this.getClass().getName());
		json.put("balance", this.balance);
		return json;
	}
	
	/**
	 * used to store this accounts path in the accounts_paths.json file.
	 * @return
	 */
	public JSONArray jsonPathValue() {
		JSONArray json = new JSONArray();
		json.add(this.accountNumber);
		json.add(this.pathToAccountFile());
		
		return json;
	}
	
	public String keyForAccountsPathFile() {
		if(this instanceof CheckingAccount) {
			return "Checking";
		} else if (this instanceof SavingsAccount) {
			return "Savings";
		} else {
			return null;
		}
	}
	
	public void initFile(){
		String fileName = this.accountNumber +".txt";
	  	try (PrintWriter pw = new PrintWriter(new FileWriter(fileName,true));) {
    		pw.write(" Date \t\t Account \t\t Action \t\t Amount \t\t Final Balance \n");
    		pw.write("------\t\t---------\t\t--------\t\t--------\t\t---------------\n");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String listAccounts() {
		return this.getClass().getSimpleName().split("Account")[0] + " Account with balance: $" + this.balance;
	}

	public String toString() {
		return this.getClass().getSimpleName().split("Account")[0] + " Account balance: $" + this.balance;
	}
}
