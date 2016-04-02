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
		
		sth.readUsers();

		System.out.println(Storage.users);
		
//		Storage.accountRelationships.put("aaaa", 3345);
//		Storage.accountRelationships.put("bbbb", 7689);
		
		System.out.println(Storage.accountRelsToJsonObject());
		
		System.out.println(Integer.parseInt(Integer.toHexString(345564332), 16));
		
	}

	public static void login() {
		
	}
}
