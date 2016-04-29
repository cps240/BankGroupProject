package backend.errors;

import backend.Account;

public class AccountAlreadyStoredException extends Exception {

	public AccountAlreadyStoredException(Account _account) {
		super("This account already exists with accountId \"" + _account.getAccountNumber() + "\"");
	}
	
	public AccountAlreadyStoredException(Class<? extends Account> accountType) {
		super("This user already an account of type \"" + accountType + "\".");
	}
}
