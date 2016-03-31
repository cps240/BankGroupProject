package backend;

import java.util.ArrayList;
import java.util.HashMap;

import backend.auth.User;

public abstract class Storage {

	/**
	 * This contains every user in storage. We will read all users from their accounts and store them in here. It contains two keys:
	 * <br><br>
	 * * Employee<br>
	 * --- a list of employees<br>
	 * * Customer<br>
	 * --- a list of customers
	 * <br><br>
	 * These users are not necessarily logged in.
	 */
	public static HashMap<String, ArrayList<User>> users = new HashMap<String, ArrayList<User>>();

}
