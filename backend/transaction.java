package backend;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class transaction {
	public Date date;
	public String accNum;
	public double value;
	public Double balance;
	public String type;
	public String fileName;
	 
	
	public transaction(Date d, String Acc, String t, double val, Double bal){
		this.date = d;
		this.accNum = Acc;
		this.type = t;
		this.value = val;
		this.balance = bal;
		this.fileName = this.accNum+".txt";
		writeOut();
	}
	

	
	public void writeOut(){
    	try (PrintWriter pw = new PrintWriter(new FileWriter(fileName,true));) {
    		pw.println(this.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return this.date +"\t\t"+ this.accNum+ "\t\t" + this.type+ "\t\t" + this.value+ "\t\t" + this.balance + "\n";
	}
	
	
//	transDate.toString(), "Checking", "Withdraw", dep.toString(), currBalance.toString()
}
