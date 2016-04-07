package backend.auth;

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
	public static User getLoggedInUser() {
		User loggedIn = null;
		
		for (Entry<String, ArrayList<User>> userType : Storage.users.entrySet()) {
			for (User user : userType.getValue()) {
				if (user.isLoggedIn()) {
					loggedIn = user;
				}
			}
		}
		
		return loggedIn;
	}
	
	public static boolean userExists(String _username) {
		for (Entry<String, ArrayList<User>> userType : Storage.users.entrySet()) {
			for (User user : userType.getValue()) {
				if (user.getUsername().equals(_username)) {
					return true;
				}
			}
		}
		return false;
	}

	public static User getUser(Integer _userId) throws UserNotFoundException {
		for (Entry<String, ArrayList<User>> userType : Storage.users.entrySet()) {
			for (User user : userType.getValue()) {
				if (user.userId.equals(_userId)) {
					return user;
				}
			}
		}
		throw new UserNotFoundException(_userId);
	}
	
	public static User getUser(String _username) throws UserNotFoundException {
		for (Entry<String, ArrayList<User>> userType : Storage.users.entrySet()) {
			for (User user : userType.getValue()) {
				if (user.getUsername().equals(_username)) {
					return user;
				}
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
	 */
	public static void addUser(Class<? extends User> _type, String _username, String _password, String _firstName, String _lastName, String _gender, String _phoneNumber) throws UserAlreadyStoredException, UserNotFoundException {
		if (_type.equals(Customer.class)) {
			/*
			 * create customer with the parameters
			 * 
			 * steps to take:
			 * 1. create user
			 * 2. initialize customer with id
			 * 3. create folder for user
			 */
			Customer cust = new Customer(_firstName, _lastName, _gender, _phoneNumber);
			cust.initializeUser();
			Settings.storage.folder.addCustomerFolder(cust);
			Storage.users.get("Customers").add(cust);
		} else {
			/*
			 * create employee with parameters
			 * 
			 * steps to take
			 * 1. create employee
			 * 2. initialize employee
			 */
			Employee emp = new Employee(_firstName, _lastName, _gender, _phoneNumber, false);
			emp.initializeUser();
			Storage.users.get("Employees").add(emp);
		}
	}
	
	/**
	 * This method will take the input from the login prompt and attempt to log the user in. If it can't find the user, it will throw a {@code UserNotFoundException}. if the password is wrong, it will throw a {@code PasswordMissMatchException}.
	 * @return
	 * @throws UserNotFoundException 
	 * @throws PasswordMissmatchException 
	 */
	public boolean attemptLogin(String _username, String _password) throws UserNotFoundException, PasswordMissmatchException {
		User toLogin = getUser(_username);
		
		toLogin.login(_password);
		
		if (toLogin.isLoggedIn()) {
			return true;
		} else {
			return false;
		}
	}

}
