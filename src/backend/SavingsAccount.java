package backend;

import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;

public class SavingsAccount extends Account {

	public SavingsAccount(Customer _owner) throws UserNotFoundException {
		super(_owner);
		// TODO Auto-generated constructor stub
	}

}
