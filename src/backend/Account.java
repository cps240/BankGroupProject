package backend;

import backend.auth.Customer;
import backend.auth.Employee;
import backend.auth.User;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserNotAuthenticatedException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import backend.storage.Storage;

public abstract class Account {

	/**
	 * Accounts cannot go below this balance.
	 */
	public static double MIN_BAL_ALLOWED = 50.00;
	
	protected double balance;
	protected Customer owner;
	protected String accountNumber;
	
	public Account(Customer _owner) throws UserNotFoundException {
		if (_owner.userId != null) {
			this.owner = _owner;
		} else {
			throw new UserNotFoundException(_owner.getUsername());
		}
	}
	
	public Customer getOwner() {
		return this.owner;
	}
	
	/**
	 * sets the initial account number for this account.
	 * @throws AccountAlreadyStoredException 
	 */
	public void initializeAccount() throws AccountAlreadyStoredException {
		if (this.accountNumber == null) {
			this.accountNumber = Storage.nextAccountId;
			Storage.incrementAccountId();
		} else {
			throw new AccountAlreadyStoredException(this);
		}
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	/**
	 * set the balance to a new amount.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _balance
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void setBalance(double _balance, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		if (_user instanceof Employee) {
			if (_user.checkPassword(_password)) {
				this.balance = _balance;
			} else {
				throw new PasswordMissmatchException(_user.getUsername(), _password);
			}
		} else {
			throw new UserNotAuthenticatedException(UserNotAuthenticatedException.EMPLOYEE_ONLY);
		}
	}
	
	/**
	 * In order to access the balance of an account, one must either be the owner of the account or be an employee.
	 * @param _user
	 * @param _password
	 * @return
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public double getBalance(User _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		if (_user instanceof Employee || _user.getUsername().equals(this.owner.getUsername())) {
			if (_user.checkPassword(_password)) {
				return this.balance;
			} else {
				throw new PasswordMissmatchException(_user.getUsername(), _password);
			}
		} else {
			throw new UserNotAuthenticatedException(_user);
		}
	}
	
	/**
	 * withdrawal money.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _amount
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void doWithdrawal(double _amount, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		double newBal = this.getBalance(_user, _password) - _amount;
		this.setBalance(newBal, _user, _password);
	}
	
	/**
	 * deposit money.
	 * In order to do this, you must have permission from an employee. An ATM machine will count as an employee.
	 * @param _amount
	 * @param _user
	 * @param _password
	 * @throws PasswordMissmatchException
	 * @throws UserNotAuthenticatedException
	 */
	public void doDeposit(double _amount, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException {
		double newBal = this.getBalance(_user, _password) + _amount;
		this.setBalance(newBal, _user, _password);
	}
}
