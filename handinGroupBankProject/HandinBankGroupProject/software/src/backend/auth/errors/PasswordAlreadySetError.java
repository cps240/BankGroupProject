package backend.auth.errors;

public class PasswordAlreadySetError extends Error {

	public PasswordAlreadySetError(String _username) {
		super("Password is already set for account with username: " + _username);
		// TODO Auto-generated constructor stub
	}

}
