package backend.storage;

public class StorageAlreadyHasDataException extends Exception {

	public StorageAlreadyHasDataException() {
		super("The storage structure you are trying to udate has unsaved data.");
	}

}
