package utils.jsonConversion;

public class JsonParseError extends Error {

	public JsonParseError(String e) {
		super("Cannot parse json supplied to map of users. " + e);
		// TODO Auto-generated constructor stub
	}

}
