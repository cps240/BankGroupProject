package backend.auth;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map.Entry;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.Settings;
import backend.auth.errors.PasswordAlreadySetError;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import backend.storage.Storage;
import utils.jsonConversion.JSONMappable;

public class Customer implements JSONMappable{
	
	public static String GENDER_MALE = "M";
	public static String GENDER_FEMALE = "F";
	
	public String username;
	public Password password;
	
	public Integer userId;
	public String firstName;
	public String lastName;
	public String gender;
	public String phoneNumber;
	
	private boolean isLoggedIn = false;

	public Customer(String _firstName, String _lastName, String _gender, String _phoneNumber) {
		this.firstName = _firstName;
		this.lastName = _lastName;
		this.gender = _gender;
		this.phoneNumber = _phoneNumber;
	}
	
	public Customer(Integer _userId, String _username, String _password, String _firstName, String _lastName, String _gender, String _phoneNumber) {
		this.username = _username;
		this.password = new Password(_password, true);
		this.firstName = _firstName;
		this.lastName = _lastName;
		this.gender = _gender;
		this.phoneNumber = _phoneNumber;
		this.userId = _userId;
	}
	
	public void setUsername(String _newUsername, String _guessPassword) throws PasswordMissmatchException {
		if (this.checkPassword(_guessPassword)) {
			this.username = _newUsername;
		} else {
			throw new PasswordMissmatchException(this.username, _guessPassword);
		}
	}
	
	public void initializeUser() throws UserAlreadyStoredException {
		if (this.username != null && Authentication.userExists(this.username)) {
			throw new UserAlreadyStoredException(this.username);
		} else if (this.userId != null) {
			throw new UserAlreadyStoredException(this.userId);
		} else {
			this.userId = Storage.nextUserId;
			Storage.nextUserId ++;
		}
	}
	
	public void initializeLoginInfo(String _username, String _password) {
		if (this.username == null) {
			this.username = _username;
			this.password = new Password(_password);
		}
	}
	
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Only use this to set the very first password (if the accounts password atm is null). Once the password is set, use:
	 * <br><br>
	 * * {@code setPassword(newPass, oldPass)}
	 * @param _password
	 */
	public void initializePassword(String _password) {
		if(this.password == null) {
			this.password = new Password(_password);
		} else {
			throw new PasswordAlreadySetError(this.username);
		}
	}
	
	public void setPassword(String _password, String _oldPassword) throws PasswordMissmatchException {
		if (this.checkPassword(_oldPassword)) {
			this.password = new Password(_password);
		} else {
			throw new PasswordMissmatchException(this.username, _oldPassword);
		}
	}
	
	public boolean checkPassword(String _guessPassword) {
		if (this.password.matches(_guessPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getPassword() {
		return this.password.override();
	}
	
	public void login(String _password) throws PasswordMissmatchException {
		if (this.password.matches(_password)) {
			this.isLoggedIn = true;
		} else {
			throw new PasswordMissmatchException(this.username, _password);
		}
	}
	
	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}
	
	public String fullName() {
		return this.firstName + " " + this.lastName;
	}
	
	private HashMap<Class<? extends Account>, Account> accounts = new HashMap<Class<? extends Account>, Account>();
	
	public Account getAccount(Class<? extends Account> _acctType) {
		return this.accounts.get(_acctType);
	}
	
	/**
	 * 
	 * @param _acctType
	 * @param _supervisor
	 * @param _employeePassword
	 * @throws AccountAlreadyStoredException if the user already has an account of this type.
	 * @throws UserNotFoundException if this user is not a saved user. more specifically, if the userId is null.
	 * @throws PasswordMissmatchException 
	 */
	public void addAccount(Class<? extends Account> _acctType) throws AccountAlreadyStoredException, UserNotFoundException {
		if (this.accounts.containsKey(_acctType)) {
			throw new AccountAlreadyStoredException(this.accounts.get(_acctType));
		} else {
			//initialize the account but with an empty balance.
			if (_acctType.equals(CheckingAccount.class)) {
				this.addCheckingAccount();
			} else if (_acctType.equals(SavingsAccount.class)) {
				this.addSavingsAccount();
			}
			this.accounts.get(_acctType).initializeAccount();
		}
	}
	
	/**
	 * This is called by {@code addAccount} if and only if the account type to add
	 * is a checking account.
	 * @throws UserNotFoundException if this user is not a saved user. more specifically, if the userId is null.
	 * @throws AccountAlreadyStoredException 
	 */
	private void addCheckingAccount() throws UserNotFoundException, AccountAlreadyStoredException {
		/*
		 * Make sure that this account does not exist already. if so, throw an error
		 * do the same for savings account.
		 */
		if (!this.accounts.containsKey(CheckingAccount.class)) {
			CheckingAccount checkingAcct = new CheckingAccount(this);
			this.accounts.put(CheckingAccount.class, checkingAcct);
		} else {
			throw new AccountAlreadyStoredException(CheckingAccount.class);
		}
	}
	
	public void addAccountFromStorage(Account _account) throws AccountAlreadyStoredException {
		if (!this.accounts.containsKey(_account.getClass())) {
			this.accounts.put(_account.getClass(), _account);
		} else {
			throw new AccountAlreadyStoredException(_account);
		}
	}
	
	/**
	 * This is called by {@code addAccount} if and only if the account type to add
	 * is a savings account.
	 * @throws UserNotFoundException if this user is not a saved user. more specifically, if the userId is null
	 * @throws AccountAlreadyStoredException 
	 */
	private void addSavingsAccount() throws UserNotFoundException, AccountAlreadyStoredException {
		/*
		 * Make sure that this account does not exist already. if so, throw an error
		 * do the same for checkin account.
		 */
		if (!this.accounts.containsKey(SavingsAccount.class)) {
			SavingsAccount savingsAcct = new SavingsAccount(this);
			this.accounts.put(SavingsAccount.class, savingsAcct);
		} else {
			throw new AccountAlreadyStoredException(SavingsAccount.class);
		}
	}

	@Override
	public Constructor getJsonConstructor() {
		// TODO Auto-generated method stub
		try {
			return this.getClass().getConstructor(
				Integer.class,
				String.class,
				String.class,
				String.class,
				String.class,
				String.class,
				String.class
			);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getConstructorFieldOrder() {
		// TODO Auto-generated method stub
		return new String[]{
				"userId",
				"username",
				"password",
				"firstName",
				"lastName",
				"gender",
				"phoneNumber"
		};
	}
	
	public String toString() {
		return "Customer: " + this.firstName + " " + this.lastName + " - " + this.gender;
	}
	
	public String getAccountsFolderKey() {
		String key = "cust_" + this.userId;
		return key;
	}
	
	public String getAccountsPathFileKey() {
		String key = this.getAccountsFolderKey() + ":accounts_file";
		return key;
	}
	
	public String pathToCustomerFolder() {
		String path = Settings.storage.folder.ROOT_PATH + "customer_" + this.userId + "/";
		return path;
	}
	
	public HashMap<Class<? extends Account>, Account> getAccounts() {
		return this.accounts;
	}
	
	/**
	 * In each users folder, there will be a file that maps an accountId to the path where the account can be found.
	 * this will return the path to that file.
	 * @return
	 */
	public String pathToAccountsPathStorage() {
		String path = this.pathToCustomerFolder() + "accounts_paths.json";
		return path;
	}
}
