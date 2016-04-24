package backend.auth.errors;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String _identifier) {
		// TODO Auto-generated constructor stub
		super("User with name or username: \"" + _identifier + "\" does not exist.");
	}
	
	public UserNotFoundException(Integer _identifier) {
		// TODO Auto-generated constructor stub
		super("User with id: " + _identifier + " does not exist.");
	}
	
	public UserNotFoundException() {
		// TODO Auto-generated constructor stub
		super("User does not exist.");
	}

}
