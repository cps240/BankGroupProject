package backend.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import backend.auth.errors.PasswordMissmatchException;
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
	 */
	public static void addUser(String _username, String _password, String _firstName, String _lastName, String _gender, String _phoneNumber) {
		
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
