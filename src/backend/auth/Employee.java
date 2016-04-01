package backend.auth;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Employee extends User {
	
	public Boolean isATM;

	public Employee(String _firstName, String _lastName, String _gender, String _phoneNumber, Boolean _isATM) {
		super(_firstName, _lastName, _gender, _phoneNumber);
		// TODO Auto-generated constructor stub
		this.isATM = _isATM;
	}
	
	public Employee(Integer _userId, String _firstName, String _lastName, String _gender, String _phoneNumber) {
		super(_firstName, _lastName, _gender, _phoneNumber);
		// TODO Auto-generated constructor stub
		this.userId = _userId;
	}

	@Override
	public Constructor getJsonConstructor() {
		// TODO Auto-generated method stub
		try {
			return this.getClass().getConstructor(
				Integer.class,
				String.class,
				String.class,
				String.class,
				String.class,
				Boolean.class
			);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String[] getConstructorFieldOrder() {
		// TODO Auto-generated method stub
		return new String[]{
				"userId",
				"firstName",
				"lastName",
				"gender",
				"phoneNumber",
				"isATM",
		};
	}
	
	public String toString() {
		return "Employee: " + this.firstName + " " + this.lastName + " - " + this.gender;
	}

}
