package backend.auth;

import backend.auth.security.Decriptions;
import backend.auth.security.Encriptions;

public class Password {

	private String value;
	
	public Password(String password){
		this.value = password;
		this.encriptNumber();
	}
	
	public Password(String encripted, boolean encriptedBool){
		this.value = encripted;
	}
	
	public String toString(){
		return "ENCRIPTED:" + this.value + "";
	}
	
	private String toDecriptedString() {
		this.decipherNumber();
		String toReturn = "DECRIPTED: " + this.value;
		this.encriptNumber();
		return toReturn;
	}
	
	private void encriptNumber(){
		this.value = Encriptions.encript(this.value);
	}
	
	private void decipherNumber(){
		this.value = Decriptions.decript(this.value);
	}
	
	public boolean equals(Password other) {
		if (this.toString().equals(other.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean matches(String _pass) {
		if (_pass.equals(this.toDecriptedString().split("DECRIPTED: ")[1])) {
			return true;
		} else {
			return false;
		}
	}
	
	protected String override() {
		return this.toDecriptedString();
	}
}
