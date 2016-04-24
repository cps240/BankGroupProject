package backend.storage;

public class FileNotInitializedError extends Error {

	public FileNotInitializedError(String _path, String _message) {
		super("File with path: " + _path + " does not exist. " + _message);
		// TODO Auto-generated constructor stub
	}

}
