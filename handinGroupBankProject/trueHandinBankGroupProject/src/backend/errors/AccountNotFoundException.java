package backend.errors;

import backend.auth.Customer;

public class AccountNotFoundException extends Exception {

	public AccountNotFoundException(Customer _user, Class c) {
		super("Sorry, the user with username: " + _user.getUsername() + " does not have a " + c.getSimpleName());
	}
	
	public AccountNotFoundException(String accountId) {
		super("Sorry, no account with id: " + accountId + " could be found.");
	}
}
