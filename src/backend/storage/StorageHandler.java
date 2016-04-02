package backend.storage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import backend.Account;
import backend.auth.Authentication;
import backend.auth.User;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import utils.jsonConversion.JSONFormat;

/**
 * This class contains methods that prints and reads data from storage files. It acts as a go-between for backend.storage.Storage and backend.storage.DataFolder
 * @author kirkp1ia
 *
 */
public class StorageHandler {
	
	private final DataFolder folder;
	
	public StorageHandler(String _rootPath) {
		this.folder = new DataFolder(_rootPath);
	}

	public void printUsers() {
		PrintWriter pw = this.folder.getPrintWriter(DataFolder.USERS_STORAGE);
		
		String jsonOfUsers = JSONFormat.formatJSON(Storage.usersToJsonObject(), 0);
		
		pw.println(jsonOfUsers);
		
		pw.close();
	}
	
	public void readUsers() throws ParseException, StorageAlreadyHasDataException {
		if (Storage.users.size() == 0) {
			Scanner scnr = this.folder.getScanner(DataFolder.USERS_STORAGE).useDelimiter("\\Z"); // "\\Z" reads entire file as one string
			
			String json = scnr.next();
			HashMap<String, ArrayList<User>> users = Storage.getUsersFromJson(json);
			
			Storage.users = users;
			
			scnr.close();
		} else {
			throw new StorageAlreadyHasDataException();
		}
	}
	
	public User getAccountOwner(Account _account) {
		Integer userId = Storage.accountRelationships.get(_account.getAccountNumber());
		try {
			return Authentication.getUser(userId);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public User getAccountOwner(String _accountId) {
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

}
