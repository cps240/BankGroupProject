package backend.storage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import backend.auth.User;
import utils.jsonConversion.JSONClassMapping;
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
			Entry<String, JSONArray> userType = (Entry<String, JSONArray>) userTypeObject;
			
			//initiiate the key for this current type. This could be Emplyee or Customer
			users.put(userType.getKey(), new ArrayList<User>());
			
			for (Object userAsObject : userType.getValue()) {
				JSONObject userAsJsonObject = (JSONObject) userAsObject;
				
				User parsedUser;
				try {
					parsedUser = (User) JSONClassMapping.jsonAnyToObject(userAsJsonObject);
				} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					throw new JsonParseError(e.getMessage());
				}
				
				users.get(userType.getKey()).add(parsedUser);
			}
		}
		
		return users;
	}

}
