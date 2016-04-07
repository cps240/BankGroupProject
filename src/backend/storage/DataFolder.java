package backend.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import backend.Account;
import backend.Settings;
import backend.auth.Customer;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;

/**
 * This class represents the folder in which all our data will be stored. It acts as a wrapper class with Meta Data and ability to
 * easily create scanners and printwriters.
 * @author kirkp1ia
 *
 */
public class DataFolder {
	
	/**
	 * Path to the folder in which all subfiles are contained. for development, the folder will be in the projects root directory.
	 * So in my case, it will be under
	 * <br>
	 *{@code /Users/kirkp1ia/projects/java/BankGroupProject/storage/}
	 */
	public final String ROOT_PATH;
	
	/**
	 * If you are looking for the specific files in the folder:<br>
	 * - Assume you have User x<br>
	 * {@code
	 * Account acct = x.getAccount(CheckingAccount.class);<br>
	 * File acctStorageFile = Settings.storage.folder.files.get(acct.getAccountFolderKey());
	 * }
	 */
	private HashMap<String, File> files = new HashMap<String, File>();
	
	/**
	 * This file will hold all of the users in the system.
	 * <br>
	 * Path:
	 * <br><br>
	 *{@code <ROOT_PATH>/users.json}
	 */
	public static final String USERS_STORAGE = "users";
	
	public static final String ACCOUNT_RELATIONSHIPS = "acc_rels";

	public DataFolder(String _rootPath) {
		// TODO Auto-generated constructor stub
		this.ROOT_PATH = _rootPath;
		this.files.put(USERS_STORAGE, new File(this.ROOT_PATH + "users.json"));
		this.files.put(ACCOUNT_RELATIONSHIPS, new File(this.ROOT_PATH + "accountRelationships.json"));
	}
	
	public PrintWriter getPrintWriter(String fileIndicator) {
		try {
			return new PrintWriter(getFile(fileIndicator));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotInitializedError(getFile(fileIndicator).getAbsolutePath(), e.getMessage());
		}
	}
	
	public Scanner getScanner(String fileIndicator) {
		try {
			return new Scanner(getFile(fileIndicator));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotInitializedError(getFile(fileIndicator).getAbsolutePath(), e.getMessage());
		}
	}
	
	/**
	 * Use the static final int constants above to indicate which file to use. for instance:
	 * <br><br>
	 * To get the file that stores users, call {@code getFile(DataFolder.USERS_STORAGE)}.
	 * @param fileIndicator
	 * @return
	 */
	public File getFile(String fileIndicator) {
		File fileToUse = this.files.get(fileIndicator);
		return fileToUse;
	}
	
	public void addFile(String _key, File _file) {
		this.files.put(_key, _file);
	}
	
	public void initializeAccountFile(Account _account) throws AccountAlreadyStoredException {
		File accountFile = new File(_account.pathToAccountFile());
		try {
			if (!accountFile.createNewFile()) {
				throw new AccountAlreadyStoredException(_account);
			} else {
				//print account to it's file
				Settings.storage.printAccountToFile(_account);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			//This won't ever happen because we create the file above.
			e.printStackTrace();
		}
	}
	
	/**
	 * initialize a user storage folder. The file hierarchy looks like so:
	 * <br>
	 * - customer_<userId>/
	 * 		- accounts_paths.json
	 * 		- checkingaccounts/
	 * 		- savingsaccounts/
	 * 
	 * @param _customer
	 * @throws UserNotFoundException
	 * @throws UserAlreadyStoredException
	 */
	public void addCustomerFolder(Customer _customer) throws UserNotFoundException, UserAlreadyStoredException {
		if (_customer.userId == null) {
			throw new UserNotFoundException();
		} else {
			File customerFile = new File(_customer.pathToCustomerFolder());
			
			if (customerFile.exists()) {
				throw new UserAlreadyStoredException(_customer.userId);
			} else {
				customerFile.mkdir();
				//create folders to hold accounts.
				File checkingFolder = new File(_customer.pathToCustomerFolder() + "checkingaccounts/");
				File savingsFolder = new File(_customer.pathToCustomerFolder() + "savingsaccounts/");
				//create file with path to accounts
				try {
					File acctFilePath = new File(_customer.pathToAccountsPathStorage());
					acctFilePath.createNewFile();
					PrintWriter writer = new PrintWriter(acctFilePath);
					writer.print("{}");
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					checkingFolder.delete();
					savingsFolder.delete();
					e.printStackTrace();
				}
			}
		}
	}

}
