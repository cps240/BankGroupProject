package utils.jsonConversion;

import java.lang.reflect.Constructor;

public interface JSONMappable {
	
	/**
	 * Use this so that JSONClassMapping can parse json to users and so that ObjectParser can print users to json.
	 * @return
	 */
	public Constructor getJsonConstructor();
	
	/**
	 * Use this, also, so that JSONClassMapping can parse json to users and so that ObjectParser can print users to json.
	 * @return
	 */
	public String[] getConstructorFieldOrder();

}
