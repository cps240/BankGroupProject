package backend.auth;

import java.util.HashMap;

import backend.auth.errors.PasswordAlreadySetError;
import backend.auth.errors.PasswordMissmatchException;
import utils.jsonConversion.JSONMappable;

public abstract class User implements JSONMappable {
	
	/**
	 * Used to shift the password encription over.
	 */
	public static int ENCRIPTION_SHIFT_VALUE = 13;

	private String username;
	private Password password;
	
	public Integer userId;
	public String firstName;
	public String lastName;
	public Character gender;
	public String phoneNumber;
	
	private boolean isLoggedIn = false;
	
	public User(String _firstName, String _lastName, Character _gender, String _phoneNumber){
		
		this.firstName = _firstName;
		this.lastName = _lastName;
		this.gender = _gender;
		this.phoneNumber = _phoneNumber;
	}
	
	public void setUsername(String _newUsername, String _guessPassword) throws PasswordMissmatchException {
		if (this.checkPassword(_guessPassword)) {
			this.username = _newUsername;
		} else {
			throw new PasswordMissmatchException(this.username, _guessPassword);
		}
	}
	
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Only use this to set the very first password (if the accounts password atm is null). Once the password is set, use:
	 * <br><br>
	 * * {@code setPassword(newPass, oldPass)}
	 * @param _password
	 */
	public void initializePassword(String _password) {
		if(this.password == null) {
			this.password = new Password(_password, User.ENCRIPTION_SHIFT_VALUE);
		} else {
			throw new PasswordAlreadySetError(this.username);
		}
	}
	
	public void setPassword(String _password, String _oldPassword) throws PasswordMissmatchException {
		if (this.checkPassword(_oldPassword)) {
			this.password = new Password(_password, User.ENCRIPTION_SHIFT_VALUE);
		} else {
			throw new PasswordMissmatchException(this.username, _oldPassword);
		}
	}
	
	public boolean checkPassword(String _guessPassword) {
		if (this.password.matches(_guessPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getPasswordAsEmployee(Employee _accessor, String _employeesPassword) throws PasswordMissmatchException {
		if (_accessor.checkPassword(_employeesPassword)) {
			return this.password.override();
		} else {
			throw new PasswordMissmatchException(_accessor.getUsername(), _employeesPassword);
		}
	}
	
	public void login(String _password) throws PasswordMissmatchException {
		if (this.password.matches(_password)) {
			this.isLoggedIn = true;
		} else {
			throw new PasswordMissmatchException(this.username, _password);
		}
	}
	
	public boolean isLoggedIn() {
		return this.isLoggedIn;
	}
}

class Password {

	private String value;
	private final int SHIFT_VALUE;
	
	public Password(String password, int _shiftValue){
		this.value = password;
		this.SHIFT_VALUE = _shiftValue;
		this.encriptNumber();
	}
	
	public String toString(){
		return this.value;
	}
	
	private String toDecriptedString() {
		this.decipherNumber();
		String toReturn = "DECRIPTED: " + this.value;
		this.encriptNumber();
		return toReturn;
	}
	
	private void encriptNumber(){
		this.value = Encriptions.toShiftedNumberLine(this.value, this.SHIFT_VALUE);
	}
	
	private void decipherNumber(){
		this.value = Encriptions.fromShiftedNumberLine(this.value, this.SHIFT_VALUE);
	}
	
	public boolean equals(Password other) {
		if (this.toString().equals(other.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean matches(String _pass) {
		if (_pass.equals(this.toDecriptedString().split("DECRIPTED: ")[1])) {
			return true;
		} else {
			return false;
		}
	}
	
	protected String override() {
		return this.toDecriptedString();
	}
}
