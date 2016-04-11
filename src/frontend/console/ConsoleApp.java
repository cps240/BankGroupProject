package frontend.console;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.Settings;
import backend.auth.Authentication;
import backend.auth.Customer;
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
//		Authentication.addUser("ianmann56", "saline54", "Ian", "Kirkpatrick", "M", "jdldffsd");
		Customer c = (Customer) Authentication.getUser("ianmann56");
		
//		c.addAccount(CheckingAccount.class);
//		c.addAccount(SavingsAccount.class);
		
//		Account savingsAcct = c.getAccount(SavingsAccount.class);
		
		Account checkingAcct = c.getAccount(CheckingAccount.class);
		
		System.out.println(checkingAcct.toString());

//		checkingAcct.doDeposit(600);
//		savingsAcct.doDeposit(100);
		
//		Account.doTransfer(checkingAcct, savingsAcct, 256.87);
		
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
