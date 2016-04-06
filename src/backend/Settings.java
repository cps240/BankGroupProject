/**
 * 
 */
package backend;

import backend.storage.StorageHandler;

/**
 * @author kirkp1ia
 * This class contains all the global static variables that will be used project wise. Do not instantiate this.
 * All attributes here must be static so we can call them with {@code Settings.______}
 */
public abstract class Settings {

	public static StorageHandler storage = new StorageHandler("storage/");

}
