package backend;

import java.util.ArrayList;
import java.util.HashMap;

import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;

public class SavingsAccount extends Account {
	
	/**
	 * will contain behavioral attributes such as minimum balance allowed and other
	 * guidelines.
	 * @author Ian
	 *
	 */
	public class META extends Account.META{
	}
	
	public META META = new META();

	public SavingsAccount(Customer _owner) throws UserNotFoundException {
		super(_owner);
		// TODO Auto-generated constructor stub
	}

}
