package frontend.console;

import backend.auth.Employee;
import backend.auth.errors.PasswordMissmatchException;

public class ConsoleApp{

	public static void main(String[] args) {
		Employee ian = new Employee("Ian", "Kirkpatrick", 'M', "(734) 352 - 9580");
		ian.initializePassword("helloMy_54");
		try {
			String pass = ian.getPasswordAsEmployee(ian, "helloMy_54");
			System.out.println(pass);
		} catch (PasswordMissmatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void login() {
		
	}
}
