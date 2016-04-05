package backend;

import backend.auth.Customer;
import backend.auth.Employee;
import backend.auth.User;
import backend.auth.errors.PasswordMissmatchException;
import backend.auth.errors.UserNotAuthenticatedException;
import backend.auth.errors.UserNotFoundException;
import backend.errors.AccountAlreadyStoredException;
import backend.errors.LowAccountBalanceException;
import backend.storage.Storage;

public abstract class Account {
	
	protected double balance;
	protected Customer owner;
	protected String accountNumber;
	
	public META META = new META();
	
	/**
	 * will contain behavioral attributes such as minimum balance allowed and other
	 * guidelines.
	 * @author Ian
	 *
	 */
	public class META {
		
		/**
		 * Accounts cannot go below this balance.
		 */
		public double MIN_BAL_ALLOWED = 50.00;
	}
	
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
			Storage.nextAccountId = Storage.incrementAccountId();
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
	public void doWithdrawal(double _amount, Employee _user, String _password) throws PasswordMissmatchException, UserNotAuthenticatedException, LowAccountBalanceException {
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
	
	/**
	 * transfer money FROM one account TO another. must have correct password
	 * @param _fromAccount - account being pulled from
	 * @param _toAccount - account being deposited into
	 * @param _user - employee that is authorizing the withdrawal. This could be an ATM machine
	 * @param _userPassword - password of the employee authorizing the transfer.
	 * @param _amount - amount of money being transferred
	 * @throws LowAccountBalanceException 
	 * @throws UserNotAuthenticatedException 
	 * @throws PasswordMissmatchException 
	 */
	public static void doTransfer(Account _fromAccount, Account _toAccount, Employee _user, String _userPassword, double _amount) throws PasswordMissmatchException, UserNotAuthenticatedException, LowAccountBalanceException {
		_fromAccount.doWithdrawal(_amount, _user, _userPassword);
		_toAccount.doDeposit(_amount, _user, _userPassword);
	}
}
