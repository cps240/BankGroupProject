package backend.auth.errors;

import backend.storage.ObjectAlreadyStoredException;

public class UserAlreadyStoredException extends ObjectAlreadyStoredException {

	public UserAlreadyStoredException(String _username) {
		super("User with username \"" + _username + "\" already exists.");
		// TODO Auto-generated constructor stub
	}
	
	public UserAlreadyStoredException(Integer _userId) {
		super("User with userId " + _userId + " already exists.");
		// TODO Auto-generated constructor stub
	}

}
