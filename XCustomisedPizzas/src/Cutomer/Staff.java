package Cutomer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Staff {
	
	private StringProperty ID;
	private StringProperty name;
	private StringProperty lastname; 
	private StringProperty username;
	private StringProperty password; 

	public Staff(String ID,String name, String lastname,String username,String password) {
		setID(ID);
		setName(name);
		setLastname(lastname);
		setUsername(username);
		setPassword(password);
	}
	
	public Staff(String name, String lastname,String username,String password) {
		setName(name);
		setLastname(lastname);
		setUsername(username);
		setPassword(password);
	}
	
	public Staff(String username,String password) {
		setUsername(username);
		setPassword(password);
	}

	// ID
	public StringProperty IDProperty(){
		if(ID == null)
			ID = new SimpleStringProperty();
		return ID;
	}

	public void setID(String ID){ 
		IDProperty().setValue(ID);  
	}
	public String getID(){ 
		return IDProperty().get();  
	}

	// Name
	public StringProperty nameProperty(){
		if(name == null)
			name = new SimpleStringProperty();
		return name;
	}

	public void setName(String name){ 
		nameProperty().setValue(name);  
	}
	public String getName(){ 
		return nameProperty().get();  
	}

	// Last name
	public StringProperty lastnameProperty(){
		if(lastname == null)
			lastname = new SimpleStringProperty();
		return lastname;
	}

	public void setLastname(String lastname){ 
		lastnameProperty().setValue(lastname);  
	}
	public String getLastname(){ 
		return lastnameProperty().get();  
	}

	// Username
	public StringProperty usernameProperty(){
		if(username == null)
			username = new SimpleStringProperty();
		return username;
	}

	public void setUsername(String username){ 
		usernameProperty().setValue(username);  
	}
	public String getUsername(){ 
		return usernameProperty().get();  
	}
	
	// Password
	public StringProperty passwordProperty(){
		if(password == null)
			password = new SimpleStringProperty();
		return password;
	}

	public void setPassword(String password){ 
		passwordProperty().setValue(password);  
	}
	public String getPassword(){ 
		return passwordProperty().get();  
	}

}
