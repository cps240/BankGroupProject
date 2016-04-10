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
		
//		c.addAccount(CheckingAccount.class);
//		c.addAccount(SavingsAccount.class);
		
//		Account savingsAcct = c.getAccount(SavingsAccount.class);
//		
//		Account checkingAcct = c.getAccount(CheckingAccount.class);

//		checkingAcct.doDeposit(600);
//		savingsAcct.doDeposit(100);
		
//		Account.doTransfer(checkingAcct, savingsAcct, 256.87);
		
		sth.printAccountRelationships();
		sth.printUsers();
		
		System.out.println("DONE");
	}

	public static void login() {
		
	}
}
