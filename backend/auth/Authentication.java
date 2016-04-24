package backend.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import backend.Settings;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserAlreadyStoredException;
import backend.auth.errors.UserNotFoundException;
import backend.storage.Storage;

/**
 * Handels login and other authentication stuff. Use this to login users and change passwords and check to see what users are logged in.
 * @author kirkp1ia
 *
 */
public abstract class Authentication {
	
	/**
	 * This method is kind of self explanitory. It will return the user that is currently logged in.
	 * @return
	 */
	public static Customer getLoggedInUser() {
		Customer loggedIn = null;
		
		for (Customer customer : Storage.users) {
			if (customer.isLoggedIn()) {
				loggedIn = customer;
			}
		}
		
		return loggedIn;
	}
	
	public static boolean userExists(String _username) {
		for (Customer customer : Storage.users) {
			if (customer.getUsername().equals(_username)) {
				return true;
			}
		}
		return false;
	}

	public static Customer getUser(Integer _userId) throws UserNotFoundException {
		for (Customer customer : Storage.users) {
			if (customer.userId.equals(_userId)) {
				return customer;
			}
		}
		throw new UserNotFoundException(_userId);
	}
	
	public static Customer getUser(String _username) throws UserNotFoundException {
		for (Customer customer : Storage.users) {
			if (customer.getUsername().equals(_username)) {
				return customer;
			}
		}
		throw new UserNotFoundException(_username);
	}
	
	/**
	 * Use this method to create a user. You must pass all the attributes to this method. Therefore, before this is called, you
	 * will need to get the attributes via input.
	 * @param _username
	 * @param _password
	 * @param _firstName
	 * @param _lastName
	 * @param _gender
	 * @param _phoneNumber
	 * @throws UserAlreadyStoredException 
	 * @throws UserNotFoundException 
	 * @throws IOException 
	 */
	public static void addUser(String _username, String _password, String _firstName, String _lastName, String _gender, String _phoneNumber) throws UserAlreadyStoredException, UserNotFoundException, IOException {
		/*
		 * create customer with the parameters
		 * 
		 * steps to take:
		 * 1. create user
		 * 2. initialize customer with id
		 * 3. create folder for user
		 */
		Customer cust = new Customer(_firstName, _lastName, _gender, _phoneNumber);
		cust.initializeLoginInfo(_username, _password);
		cust.initializeUser();
		Settings.storage.folder.addCustomerFolder(cust);
		Storage.users.add(cust);
	}
	
	/**
	 * This method will take the input from the login prompt and attempt to log the user in. If it can't find the user, it will throw a {@code UserNotFoundException}. if the password is wrong, it will throw a {@code PasswordMissMatchException}.
	 * @return
	 * @throws UserNotFoundException 
	 * @throws PasswordMissmatchException 
	 */
	public static boolean attemptLogin(String _username, String _password) throws UserNotFoundException, PasswordMissmatchException {
		Customer toLogin = getUser(_username);
		
		toLogin.login(_password);
		
		if (toLogin.isLoggedIn()) {
			return true;
		} else {
			return false;
		}
	}

}
