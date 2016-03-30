package backend.auth.errors;

public class PasswordMissmatchException extends Exception {
	
	public String username;
	public String guess;

	public PasswordMissmatchException(String _username, String _guessedPassword) {
		super("\"" + _guessedPassword + "\" is not the correct password for account with username: \"" + _username + "\"");
		this.guess = _guessedPassword;
		this.username = _username;
	}

}
