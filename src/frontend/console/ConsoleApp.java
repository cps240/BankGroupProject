package frontend.console;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import backend.auth.Customer;
import backend.auth.Employee;
import backend.auth.User;
import backend.auth.errors.PasswordMissmatchException;
import backend.storage.Storage;
import backend.storage.StorageAlreadyHasDataException;
import backend.storage.StorageHandler;
import utils.jsonConversion.JSONFormat;

public class ConsoleApp{

	public static void main(String[] args) throws Exception {
		StorageHandler sth = new StorageHandler("storage/");
		
		Customer joe = new Customer("Wynton", "Kirkpatrick", "M", "(644) 333 - 2222");
//		joe.initializePassword("helloMy_54");
		
		System.out.println(Storage.users);
		sth.readUsers();
		System.out.println(Storage.users);
//		System.out.println(Storage.users);
		Storage.users.get("Customers").add(joe);
		joe.initializeUser();
//		System.out.println(Storage.users);
		
		sth.printUsers();
	}

	public static void login() {
		
	}
}
