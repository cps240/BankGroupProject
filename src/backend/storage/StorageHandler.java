package backend.storage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

import backend.auth.User;
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

}
