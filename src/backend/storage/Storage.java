package backend.storage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import backend.Account;
import backend.CheckingAccount;
import backend.SavingsAccount;
import backend.Settings;
import backend.auth.Authentication;
import backend.auth.Customer;
import backend.auth.User;
import backend.auth.errors.UserNotFoundException;
import utils.jsonConversion.JSONClassMapping;
import utils.jsonConversion.JSONFormat;
import utils.jsonConversion.JsonParseError;
import utils.jsonConversion.ObjectParser;

/**
 * This will hold all of the backend.storage for the entire program.
 * permenant backend.storage will be saved in /backend.storage folder in the project.
 * @author kirkp1ia
 *
 */
public abstract class Storage {

	/**
	 * This contains every user in backend.storage. We will read all users from their accounts and store them in here. It contains two keys:
	 * <br><br>
	 * * Employee<br>
	 * --- a list of employees<br>
	 * * Customer<br>
	 * --- a list of customers
	 * <br><br>
	 * These users are not necessarily logged in.
	 */
	public static HashMap<String, ArrayList<User>> users = new HashMap<String, ArrayList<User>>();
	
	/**
	 * Next time a user is initialized and his ID is set, use this for his id.
	 */
	public static Integer nextUserId = 1;
	
	/**
	 * This contains a map of account ids that map to their owner id's.
	 * I don't store the actual objects here because I don't want to have the same object stored in two different places.
	 * <br>
	 * To get the user from the integer returned which is the userId, call {@link backend.auth.Authentication}.getUser(Integer _userId)
	 */
	public static HashMap<String, Integer> accountRelationships = new HashMap<String, Integer>();
	
	/**
	 * Next time an account is initialized and the ID is set, use this for it's id.
	 * This will be a hex code that represents the number id. so the 3rd account
	 * will have an account id of "000000000000001"
	 */
	public static String nextAccountId = "000000000000001";
	
	/**
	 * nextAccountId is a hexidecimal string so this increments it.
	 * @return
	 */
	public static String incrementAccountId() {
		Long nai = Long.parseLong(nextAccountId, 16) + 1;
		String hexCode = Long.toHexString(nai);
		hexCode = ("000000000000000" + hexCode).substring(hexCode.length());
		return hexCode;
	}
	
	/**
	 * This method will put all the users into a json string with employees and customers seperated. this json will be printed to a file and later parsed into the above hashmap called users using {@code getUsersFromJson(json)} where json is the string read in by the file.
	 * @return
	 */
	public static JSONObject usersToJsonObject() {
		JSONObject usersJson = new JSONObject();
		
		for (Entry<String, ArrayList<User>> userType : Storage.users.entrySet()) {
			
			//initiate the key for this current type. This could be Employee or Customer
			usersJson.put(userType.getKey(), new JSONArray());
			
			for (User user : userType.getValue()) {
				Object userAsJsonObject = null;
				try {
					userAsJsonObject = ObjectParser.anyObjectToJSON(user);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
						| ClassNotFoundException e) {
					// TODO Auto-generated catch block
					throw new JsonParseError(e.getMessage());
				}
				((JSONArray) usersJson.get(userType.getKey())).add(userAsJsonObject);
			}
		}
		
		usersJson.put("nextUserId", String.valueOf(Storage.nextUserId));
		
		return usersJson;
	}
	
	/**
	 * This method will take a string of json format which contains the users in the backend.storage file and will put them into the above hashmap users.
	 * @param json
	 * @return
	 * @throws ParseException
	 */
	public static HashMap<String, ArrayList<User>> getUsersFromJson(String json) throws ParseException {
		HashMap<String, ArrayList<User>> users = new HashMap<String, ArrayList<User>>();
		
		JSONObject jsonOfUsers = (JSONObject) new JSONParser().parse(json);
		
		for (Object userTypeObject : jsonOfUsers.entrySet()) {
			if (((Entry) userTypeObject).getKey().equals("nextUserId")) {							//Use nextUserId to set the id of any user that is added to storage.
				Storage.nextUserId = Integer.parseInt((String) jsonOfUsers.get("nextUserId"));
			} else {
				Entry<String, JSONArray> userType = (Entry<String, JSONArray>) userTypeObject;
				
				//initiiate the key for this current type. This could be Emplyee or Customer
				if (!users.containsKey(userType.getKey())) {
					users.put(userType.getKey(), new ArrayList<User>());
				}
				
				for (Object userAsObject : userType.getValue()) {
					JSONObject userAsJsonObject = (JSONObject) userAsObject;
					
					User parsedUser;
					try {
						parsedUser = (User) JSONClassMapping.jsonAnyToObject(userAsJsonObject);
						users.get(userType.getKey()).add(parsedUser);
						
						//add accountsPath file to storage folder keys.
						if (parsedUser instanceof Customer) {
							String accountsKey = ((Customer) parsedUser).getAccountsPathFileKey();
							Settings.storage.folder.addFile(accountsKey, new File(((Customer) parsedUser).pathToAccountsPathStorage()));
						}
					} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						throw new JsonParseError(e.getMessage());
					}
				}
			}
		}
		
		return users;
	}
	
	/**
	 * creates a json object to be printed out to a text file of storage.
	 * this is just a flat hashmap that maps account id's to user id's for later
	 * use of querying the users that own an account.
	 * @return
	 */
	public static JSONObject accountRelsToJsonObject() {
		JSONObject json = JSONFormat.hashMapToJSON(accountRelationships);
		json.put("nextAccountId", nextAccountId);
		return json;
	}
	
	/**
	 * takes a json string and extracts the relationships between accounts and customers
	 * @param _jsonString
	 * @return
	 * @throws ParseException
	 */
	public static HashMap<String, Integer> jsonToAccountRels(String _jsonString) throws ParseException {
		JSONObject json = (JSONObject) new JSONParser().parse(_jsonString);
		HashMap<String, Integer> accRels = new HashMap<String, Integer>();
		
		for (Object entryObject : json.entrySet()) {
			//one of the keys is just an integer.
			if (((Entry) entryObject).getKey().equals("nextAccountId")) {
				nextAccountId = (String) json.get("nextAccountId");
			} else {
				Entry entry = (Entry) entryObject;
				String accountId = (String) entry.getKey();
				Integer userId = new Integer(((Long) entry.getValue()).intValue());
				accRels.put(accountId, userId);
				
				try {
					Customer c = (Customer) Authentication.getUser(userId);
					//add this account to the users hashmap of accounts.
				} catch (UserNotFoundException e) {
					// TODO Auto-generated catch block
					throw new JsonParseError(e.getMessage());
				}
			}
		}
		
		return accRels;
	}
	
	public static HashMap<String, String[]> getAccountsForCustomer(Customer _customer, String _jsonString) throws ParseException {
		JSONObject json = (JSONObject) new JSONParser().parse(_jsonString);
		HashMap<String, String[]> accounts = new HashMap<String, String[]>();
		
		/*
		 * Format of file expected:
		 * 
		 * ===start of file
		 * {
		 * 		"Checking": [<accountId>, <pathToAccount>],
		 * 		"Savings": [<accountId>, <pathToAccount>]
		 * }
		 * ===end of file
		 */
		
		//get checking account
		if (json.containsKey("Checking")) {
			String accountId = (String) ((JSONArray) json.get("Checking")).get(0);
			String pathToAccount = (String) ((JSONArray) json.get("Checking")).get(1);
			accounts.put(accountId, new String[]{pathToAccount, CheckingAccount.class.getName()});
		}
		
		//get savings account
		if (json.containsKey("Savings")) {
			String accountId = (String) ((JSONArray) json.get("Savings")).get(0);
			String pathToAccount = (String) ((JSONArray) json.get("Savings")).get(1);
			accounts.put(accountId, new String[]{pathToAccount, CheckingAccount.class.getName()});
		}
		
		return accounts;
	}
	
	/**
	 * returns the account stored as a string which is _fileContents. This comes from the file in the users folder.
	 * 
	 * format of _fileContents should be:
	 * 
	 * {
	 * 		"type": "<class name for account type>",
	 * 		"balance": ?????.??
	 * }
	 * @param _fileContents
	 * @return
	 * @throws ParseException 
	 */
	public static Account getAccountFromString(Customer _owner, String _acctId, String _fileContents) throws ParseException {
		JSONObject json = (JSONObject) new JSONParser().parse(_fileContents);
		
		/*
		 * To create the account, we have to use java.lang.reflect. This allows us to call the constructor
		 * from savings account or checking account without casting it to either of those. the class type will be
		 * found by getting the class stored in the json key: "type"
		 * 
		 * constructorArgs is a list of parameter types so that we can get the constructor from the account type.
		 */
		Class<?>[] constructorArgs = new Class<?>[] {
			Customer.class
		};
		
		//get the account type
		try {
			Class accountType = Class.forName((String) json.get("type"));
			
			//get the constructor for the account type
			Constructor constructor = accountType.getConstructor(constructorArgs);
			
			//instantiate account by calling constructor
			Account acct = (Account) constructor.newInstance(_owner);
			acct.accountNumber = _acctId;
			acct.overrideSetBalance((double) json.get("balance"));
			return acct;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new JsonParseError(e.getMessage());
		}
	}
}
