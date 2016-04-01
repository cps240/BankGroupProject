package backend.auth.errors;

import backend.auth.User;

public class UserNotAuthenticatedException extends Exception {
	
	/**
	 * this will indicate when a user tries to add money to an account but does not have permission.
	 * This may be because he tried to bypass atm machines or employee. NOTE: you must pass an employees' credentials
	 * to set the balance of the account. also, an ATM machine will count as an employee.
	 */
	public static final String EMPLOYEE_ONLY = "Sorry, you did not recieve permission to deposit to this account.";
	
	/**
	 * This will indicate that the user trying to access this account is not the owner of the account or
	 * an employee did not give them permission to access it.
	 */
	public static final String WRONG_CUSTOMER = "DOES NOT HAVE ACCESS";
	
	public String message;

	public UserNotAuthenticatedException(User _customer) {
		super("Sorry, user with username: \"" + _customer.getUsername() + "\"" + " does not have access to this account.");
		this.message = WRONG_CUSTOMER;
	}
	
	public UserNotAuthenticatedException(String message) {
		super(message);
		this.message = message;
	}

}
