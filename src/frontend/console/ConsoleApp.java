package frontend.console;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.Settings;
import backend.auth.Authentication;
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
		
		sth.readUsers();
		sth.readAccountRelationships();

//		Authentication.addUser(Customer.class, "hahaha", "joejoe", "Joe", "John", "M", "jdldffsd");
		Customer c = (Customer) Authentication.getUser("hahaha");
		System.out.println(c.getPassword());
		
		sth.printAccountRelationships();
		sth.printUsers();
		
		System.out.println("DONE");
	}

	public static void login() {
		
	}
}
