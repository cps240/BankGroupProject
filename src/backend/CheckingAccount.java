package backend;

import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;

public class CheckingAccount extends Account {

	public CheckingAccount(Customer _owner) throws UserNotFoundException {
		super(_owner);
		// TODO Auto-generated constructor stub
	}

}
