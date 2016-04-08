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

//		Authentication.addUser(Customer.class, "myusername", "haha67", "Jim", "Joe", "M", "(787) 333 - 4444");
		
		Employee emp = (Employee) Authentication.getUser("ianmann56");
		Customer cust = (Customer) Authentication.getUser("myusername");
		
		cust.addAccount(CheckingAccount.class, emp, "yyy");
		
//		Account acct = sth.getAccountOwner("000000003").;
		
//		Employee e = (Employee) Authentication.getUser(5);
//		System.out.println(e.getPasswordAsEmployee(e, "saline54"));
//
////		Authentication.addUser(Employee.class, "joe56joe", "hello_67", "John", "Jackson", "M", "(677) 456 - 8796");
//		
		sth.printAccountRelationships();
		sth.printUsers();
		
		System.out.println("DONE");
	}

	public static void login() {
		
	}
}
