package backend.errors;

import backend.auth.User;

public class AccountNotFoundException extends Exception {

	public AccountNotFoundException(User _user, Class c) {
		super("Sorry, the user with username: " + _user.getUsername() + " does not have a " + c.getSimpleName());
	}
}
