package frontend.console;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
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
		Employee ian = (Employee) Authentication.getUser(1);
		ian.initializePassword("saline54");
		
//		Account acc = joe.getAccount(SavingsAccount.class);
		
		joe.addAccount(SavingsAccount.class, ian, "saline54");
		
		Account acc = joe.getAccount(SavingsAccount.class);
		acc.doDeposit(30.00, ian, "saline54");
		
		System.out.println(acc.getBalance(ian, "saline54"));
		
		sth.printAccountRelationships();
		sth.printUsers();
	}

	public static void login() {
		
	}
}
