package backend.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents the folder in which all our data will be stored. It acts as a wrapper class with Meta Data and ability to
 * easily create scanners and printwriters.
 * @author kirkp1ia
 *
 */
public class DataFolder {
	
	/**
	 * Path to the folder in which all subfiles are contained. for development, the folder will be int the projects root directory.
	 * So in my case, it will be under
	 * <br>
	 *{@code /Users/kirkp1ia/projects/java/BankGroupProject/storage/}
	 */
	private final String ROOT_PATH;
	
	private ArrayList<File> files = new ArrayList<File>();
	
	/**
	 * This file will hold all of the users in the system.
	 * <br>
	 * Path:
	 * <br><br>
	 *{@code <ROOT_PATH>/users.json}
	 */
	public static final int USERS_STORAGE = 0;
	
	public static final int ACCOUNT_RELATIONSHIPS = 1;

	public DataFolder(String _rootPath) {
		// TODO Auto-generated constructor stub
		this.ROOT_PATH = _rootPath;
		this.files.add(USERS_STORAGE, new File(this.ROOT_PATH + "users.json"));
		this.files.add(ACCOUNT_RELATIONSHIPS, new File(this.ROOT_PATH + "accountRelationships.json"));
	}
	
	public PrintWriter getPrintWriter(int fileIndicator) {
		try {
			return new PrintWriter(getFile(fileIndicator));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotInitializedError(getFile(fileIndicator).getAbsolutePath(), e.getMessage());
		}
	}
	
	public Scanner getScanner(int fileIndicator) {
		try {
			return new Scanner(getFile(fileIndicator));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new FileNotInitializedError(getFile(fileIndicator).getAbsolutePath(), e.getMessage());
		}
	}
	
	/**
	 * Use the static final int constants above to indicate which file to use. for instance:
	 * <br><br>
	 * To get the file that stores users, call {@code getFile(DataFolder.USERS_STORAGE)}.
	 * @param fileIndicator
	 * @return
	 */
	public File getFile(int fileIndicator) {
		File fileToUse = this.files.get(fileIndicator);
		return fileToUse;
	}

}
