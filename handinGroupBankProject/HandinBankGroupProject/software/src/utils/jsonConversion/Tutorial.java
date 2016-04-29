package utils.jsonConversion;

import java.lang.reflect.InvocationTargetException;

import backend.auth.Customer;

public class Tutorial {

	public static void main(String[] a) {
		//create an object with data
		Customer ian = new Customer("Ian", "Kirkpatrick", "M", "(734) 352 - 9580");
		
		//There are errors that are thrown on the case of syntax errors in json or
			//if the object does not implement JSONMappable
		try {
			//Turn an object into a json string.
			Object jsonIan = ObjectParser.anyObjectToJSON(ian);
			//format the string so that it is more readable insted of the one line
				//ugliness that JSON objects print out as.
			String jsonToPrint = JSONFormat.formatJSON(jsonIan, 0);
			
			System.out.println(jsonToPrint);
			
			//You can also take json and use it to instantiate objects
			Object ian2 = JSONClassMapping.jsonAnyToObject(jsonIan);
			System.out.println(ian2);
			
			/*
			 * Using these lines combined will allow us to store objects as json text in a file and then later,
			 * 		read that data in as the object it was stored as.
			 */
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
