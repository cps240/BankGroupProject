package frontend.console;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import backend.auth.Employee;
import backend.auth.User;
import backend.auth.errors.PasswordMissmatchException;
import backend.storage.Storage;
import backend.storage.StorageAlreadyHasDataException;
import backend.storage.StorageHandler;
import utils.jsonConversion.JSONFormat;

public class ConsoleApp{

	public static void main(String[] args) {
		StorageHandler sth = new StorageHandler("storage/");
		
		Employee joe = new Employee("Joe", "Schmoe", "M", "(567) 123 - 4567");
		joe.initializePassword("helloMy_54");
		
		try {
			sth.readUsers();
		} catch (ParseException | StorageAlreadyHasDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Storage.users.put("Customer", new ArrayList<User>());
		Storage.users.get("Customer").add(joe);
		
		sth.printUsers();
	}

	public static void login() {
		
	}
}
