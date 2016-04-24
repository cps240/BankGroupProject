package backend.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import backend.Account;
import backend.Settings;
import backend.auth.Authentication;
import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import utils.jsonConversion.JSONFormat;

/**
 * This class contains methods that prints and reads data from storage files. It acts as a go-between for backend.storage.Storage and backend.storage.DataFolder
 * @author kirkp1ia
 *
 */
public class StorageHandler {
	
	public final DataFolder folder;
	
	public StorageHandler(String _rootPath) {
		this.folder = new DataFolder(_rootPath);
	}

	public void printUsers() {
		PrintWriter pw = this.folder.getPrintWriter(DataFolder.USERS_STORAGE);
		
		String jsonOfUsers = JSONFormat.formatJSON(Storage.usersToJsonArray(), 0);
		
		pw.println(jsonOfUsers);
		
		pw.close();
		
		/*
		 * print the accounts for this user.
		 */
		for (Customer user : Storage.users) {
			try {
				this.printAccountsForCustomer(((Customer) user));
			} catch (AccountAlreadyStoredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void readUsers() throws ParseException, StorageAlreadyHasDataException {
		if (Storage.users.size() == 0) {
			Scanner scnr = this.folder.getScanner(DataFolder.USERS_STORAGE).useDelimiter("\\Z"); // "\\Z" reads entire file as one string
			
			String json = scnr.next();
			ArrayList<Customer> users = Storage.getUsersFromJson(json);
			
			Storage.users = users;
			scnr.close();
			
			/*
			 * Read the accounts for this user.
			 */
			for (Customer user : Storage.users) {
				try {
					this.readAccountsForCustomer(user);
				} catch (AccountAlreadyStoredException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			throw new StorageAlreadyHasDataException();
		}
	}
	
	public Customer getAccountOwner(Account _account) {
		Integer userId = Storage.accountRelationships.get(_account.getAccountNumber());
		try {
			return Authentication.getUser(userId);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Customer getAccountOwner(String _accountId) {
		Integer userId = Storage.accountRelationships.get(_accountId);
		try {
			return Authentication.getUser(userId);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveAccount(Account _account) throws AccountAlreadyStoredException {
		_account.initializeAccount();
		Storage.accountRelationships.put(_account.getAccountNumber(), _account.getOwner().userId);
	}
	
	public void readAccountRelationships() throws ParseException {
		Scanner scnr = this.folder.getScanner(DataFolder.ACCOUNT_RELATIONSHIPS).useDelimiter("\\Z");
		
		String json = scnr.next();
		Storage.accountRelationships = Storage.jsonToAccountRels(json);
		
		scnr.close();
	}
	
	public void printAccountRelationships() {
		PrintWriter pw = this.folder.getPrintWriter(DataFolder.ACCOUNT_RELATIONSHIPS);
		
		String json = JSONFormat.formatJSON(Storage.accountRelsToJsonObject(), 0);
		
		pw.println(json);
		pw.close();
	}
	
	/**
	 * Read in all the accounts for a given customer
	 * @param _customer
	 * @throws AccountAlreadyStoredException
	 */
	public void readAccountsForCustomer(Customer _customer) throws AccountAlreadyStoredException {
		File accountsPathFile = Settings.storage.folder.getFile(_customer.getAccountsPathFileKey());
		
		try {
			Scanner fileReader = new Scanner(accountsPathFile).useDelimiter("\\Z");
			String json = fileReader.next();
			fileReader.close();
			
			HashMap<String, String[]> accountsForUser = Storage.getAccountsForCustomer(_customer, json);
			
			//add account to users accounts
			//must get account from the file though. do this in storage.
			for (Entry<String, String[]> entry : accountsForUser.entrySet()) {
				Account acct = getAccountFromFile(_customer, entry);
				_customer.addAccountFromStorage(acct);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Goes out to the file which contains the account and parses the account from that file.
	 * @param _accountRaw - a key entry from the hashmap returned in {@code readAccountsForCustomer} method above.
	 * the key in this entry must be the id of the account.
	 * The array is a coupling of the path to the folder along with the class name of the account type.
	 * @return
	 */
	public Account getAccountFromFile(Customer _customer, Entry<String, String[]> _accountRaw) {
		File accountFile = new File(_accountRaw.getValue()[0]);
		
		try {
			Scanner fileReader = new Scanner(accountFile).useDelimiter("\\Z");
			String contents = fileReader.next();
			fileReader.close();
			
			Account acct = Storage.getAccountFromString(_customer, _accountRaw.getKey(), contents);
			
			return acct;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void printAccountsForCustomer(Customer _customer) throws AccountNotFoundException, ParseException, AccountAlreadyStoredException, FileNotFoundException {
		try {
			/*
			 * Refresh the accounts_paths file to an empty json file
			 */
			PrintWriter pw = new PrintWriter(_customer.pathToAccountsPathStorage());
			pw.print("{}");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Entry<Class<? extends Account>, Account> entry : _customer.getAccounts().entrySet()) {
			Account acct = entry.getValue();
			this.printAccountToFile(acct);
			this.addAccountToPaths(acct);
		}
	}
	
	/**
	 * Prints an account to it's storage folder.
	 * @param _account
	 * @throws AccountNotFoundException
	 */
	public void printAccountToFile(Account _account) throws AccountNotFoundException {
		File accountFile = new File(_account.pathToAccountFile());
		
		try {
			PrintWriter accountWriter = new PrintWriter(accountFile);
			String json = JSONFormat.formatJSON(_account.toJson(), 0);
			accountWriter.print(json);
			accountWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new AccountNotFoundException("Account with accountId " + _account.accountNumber + " does not have a storage file.\r\nPlease initialize the file with the account.");
		}
	}
	
	/**
	 * This will add the account to the users record. I will read the accounts_paths.json file and
	 * add _account to that json if and only if the account is fully initialized and the user does not already have an account
	 * of this type already.
	 * @param _account
	 * @throws ParseException 
	 * @throws AccountAlreadyStoredException 
	 */
	public void addAccountToPaths(Account _account) throws ParseException, AccountAlreadyStoredException, FileNotFoundException {
		Scanner scnr = null;
		JSONObject json = null;
		try {
			scnr = new Scanner(new File(_account.getOwner().pathToAccountsPathStorage())).useDelimiter("\\Z");
			String jsonAlreadyInFile = scnr.next();
			json = (JSONObject) new JSONParser().parse(jsonAlreadyInFile);
			if (!json.containsKey(_account.keyForAccountsPathFile())) {
				json.put(_account.keyForAccountsPathFile(), _account.jsonPathValue());
			} else {
				throw new AccountAlreadyStoredException(_account.getClass());
			}
			scnr.close();
		} catch (Exception e) {
			scnr.close();
			throw e;
		}
		
		try {
			PrintWriter pw = new PrintWriter(_account.getOwner().pathToAccountsPathStorage());
			pw.print(JSONFormat.formatJSON(json, 0));
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printData() {
		this.printAccountRelationships();
		this.printUsers();
	}

}
