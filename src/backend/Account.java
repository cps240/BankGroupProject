package backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import backend.auth.Customer;
import backend.auth.Employee;
import backend.auth.User;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserNotAuthenticatedException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import backend.errors.LowAccountBalanceException;
import backend.storage.Storage;

public abstract class Account {
	
	protected Double balance;
	protected Customer owner;
	public String accountNumber;
	
	public META META = new META();
	
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
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	/**
	 * set the balance to a new amount.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _balance
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void setBalance(double _balance, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		if (_user instanceof Employee) {
			if (_user.checkPassword(_password)) {
				this.balance = _balance;
			} else {
				throw new PasswordMissmatchException(_user.getUsername(), _password);
			}
		} else {
			throw new UserNotAuthenticatedException(UserNotAuthenticatedException.EMPLOYEE_ONLY);
		}
	}
	
	/**
	 * In order to access the balance of an account, one must either be the owner of the account or be an employee.
	 * @param _user
	 * @param _password
	 * @return
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public double getBalance(User _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		if (_user instanceof Employee || _user.getUsername().equals(this.owner.getUsername())) {
			if (_user.checkPassword(_password)) {
				return this.balance;
			} else {
				throw new PasswordMissmatchException(_user.getUsername(), _password);
			}
		} else {
			throw new UserNotAuthenticatedException(_user);
		}
	}
	
	/**
	 * withdrawal money.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _amount
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void doWithdrawal(double _amount, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException, LowAccountBalanceException {
		double newBal = this.getBalance(_user, _password) - _amount;
		this.setBalance(newBal, _user, _password);
	}
	
	/**
	 * deposit money.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _amount
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void doDeposit(double _amount, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException, LowAccountBalanceException {
		double newBal = this.getBalance(_user, _password) + _amount;
		this.setBalance(newBal, _user, _password);
	}
	
	/**
	 * transfer money FROM one account TO another. must have correct password
	 * @param _fromAccount - account being pulled from
	 * @param _toAccount - account being deposited into
	 * @param _user - employee that is authorizing the withdrawal. This could be an ATM machine
	 * @param _userPassword - password of the employee authorizing the transfer.
	 * @param _amount - amount of money being transferred
	 * @throws LowAccountBalanceException 
	 * @throws UserNotAuthenticatedException 
	 * @throws PasswordMissmatchException 
	 */
	public static void doTransfer(Account _fromAccount, Account _toAccount, Employee _user, String _userPassword, double _amount) throws PasswordMissmatchException, UserNotAuthenticatedException, LowAccountBalanceException {
		_fromAccount.doWithdrawal(_amount, _user, _userPassword);
		_toAccount.doDeposit(_amount, _user, _userPassword);
	}
	
	/**
	 * This is only allowed if balance is currently null (hasn't been set yet).
	 * @param _balance
	 */
	public void overrideSetBalance(double _balance) {
		if (this.balance == null) {
			this.balance = _balance;
		} else {
			throw new SecurityException("Not allowed to override setBalance unless current balance is null (hasn't been set yet).");
		}
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
}
