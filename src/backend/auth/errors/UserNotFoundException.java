package backend.auth.errors;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String _identifier) {
		// TODO Auto-generated constructor stub
		super("User with name or username: \"" + _identifier + "\" does not exist.");
	}

}
