package backend.errors;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import backend.Account;

public class LowAccountBalanceException extends Exception {

	private static NumberFormat dollarFormat = new DecimalFormat("#0.00");
	
	public LowAccountBalanceException(Account _account) {
		super("Account \"" + _account.getAccountNumber() + "\" may not go below $" + dollarFormat.format(_account.META.MIN_BAL_ALLOWED));
	}
}
