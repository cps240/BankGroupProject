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

		Customer joe = (Customer) Authentication.getUser(3);
		Customer wynton = (Customer) Authentication.getUser(5);
		
		sth.readAccountsForCustomer(joe);
		Employee ian = (Employee) Authentication.getUser(1);
		ian.initializePassword("saline54");
		
		joe.addAccount(CheckingAccount.class, ian, "saline54");
		
		Account acc = joe.getAccount(CheckingAccount.class);
		acc.overrideSetBalance(150.50);
		sth.folder.initializeAccountFile(acc);
		
		sth.printAccountRelationships();
		sth.printUsers();
		sth.printAccountsForCustomer(joe);
	}

	public static void login() {
		
	}
}
