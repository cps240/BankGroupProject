package backend.auth;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map.Entry;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.Settings;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;

public class Customer extends User {
	
	private HashMap<Class<? extends Account>, Account> accounts = new HashMap<Class<? extends Account>, Account>();

	public Customer(String _firstName, String _lastName, String _gender, String _phoneNumber) {
		super(_firstName, _lastName, _gender, _phoneNumber);
		// TODO Auto-generated constructor stub
	}
	
	public Customer(Integer _userId, String _firstName, String _lastName, String _gender, String _phoneNumber) {
		super(_firstName, _lastName, _gender, _phoneNumber);
		// TODO Auto-generated constructor stub
		this.userId = _userId;
	}
	
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
	 */
	public void addAccount(Class<? extends Account> _acctType, Employee _supervisor, String _employeePassword) throws AccountAlreadyStoredException, UserNotFoundException {
		if (_supervisor.checkPassword(_employeePassword)) {
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
	}
	
	/**
	 * This is called by {@code addAccount} if and only if the account type to add
	 * is a checking account.
	 * @throws UserNotFoundException if this user is not a saved user. more specifically, if the userId is null.
	 */
	private void addCheckingAccount() throws UserNotFoundException {
		CheckingAccount checkingAcct = new CheckingAccount(this);
		this.accounts.put(CheckingAccount.class, checkingAcct);
	}
	
	public void addAccountFromStorage(Account _account) throws AccountAlreadyStoredException {
		//path to this users folder
		if (this.accounts.containsKey(_account.getClass())) {
			this.accounts.put(_account.getClass(), _account);
		} else {
			throw new AccountAlreadyStoredException(_account);
		}
	}
	
	/**
	 * This is called by {@code addAccount} if and only if the account type to add
	 * is a savings account.
	 * @throws UserNotFoundException if this user is not a saved user. more specifically, if the userId is null
	 */
	private void addSavingsAccount() throws UserNotFoundException {
		SavingsAccount savingsAcct = new SavingsAccount(this);
		this.accounts.put(SavingsAccount.class, savingsAcct);
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
