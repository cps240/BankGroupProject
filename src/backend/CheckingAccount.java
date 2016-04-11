package backend;

import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;
import backend.errors.LowAccountBalanceException;

public class CheckingAccount extends Account {
	
	/**
	 * will contain behavioral attributes such as minimum balance allowed and other
	 * guidelines.
	 * @author Ian
	 *
	 */
	public class META extends Account.META{
	}
	
	public META META = new META();

	public CheckingAccount(Customer _owner) throws UserNotFoundException {
		super(_owner);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doWithdrawal(double _amount) throws LowAccountBalanceException {
		if (this.META.MIN_BAL_ALLOWED > this.balance - _amount) {
			// don't let the user withdrawal anymore. he would be going under the minimum limit.
			throw new LowAccountBalanceException(this);
		} else {
			super.doWithdrawal(_amount);
		}
	}

}
