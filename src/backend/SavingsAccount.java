package backend;

import java.util.ArrayList;
import java.util.HashMap;

import backend.auth.Customer;
import backend.auth.errors.UserNotFoundException;

public class SavingsAccount extends Account {
	
	/**
	 * will contain behavioral attributes such as minimum balance allowed and other
	 * guidelines.
	 * @author Ian
	 *
	 */
	public class META extends Account.META{
		
		public HashMap<ArrayList<Double>, Double> interestRates = new HashMap<ArrayList<Double>, Double>(){{
			//Decide what the interest rates will be.
			
			/*
			 * anything from $50 to $100000 has interest gain rate of 0.1%
			 */
			put(
				new ArrayList<Double>(){{add(50.00);add(100000.00);}},
				0.1
			);
			
			/*
			 * anything from $100001 to $1000000 has interest gain rate of 0.1%
			 */
			put(
				new ArrayList<Double>(){{add(100001.00);add(1000000.00);}},
				0.2
			);
		}};
		
		/*
		 * make a class called interest bracket that does logic to see what an accounts interest
		 * will be instead of doing it with the hashmap like I did up above.
		 */
		
		public double interest_rate;
	}
	
	public META META = new META();

	public SavingsAccount(Customer _owner) throws UserNotFoundException {
		super(_owner);
		// TODO Auto-generated constructor stub
	}

}
