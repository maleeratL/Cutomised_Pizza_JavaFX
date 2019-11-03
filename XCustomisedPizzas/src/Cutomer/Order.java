package Cutomer;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private StringProperty ID;
	private StringProperty base;
	private StringProperty toppings;
	private StringProperty status;
	private StringProperty customersId;

	public Order(String ID, String base,String toppings, String status, String customersId) {
		setID(ID);
		setBase(base);
		setToppings(toppings);
		setStatus(status);
		setCustomersId(customersId);
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

	// Base
	public StringProperty baseProperty(){
		if(base == null)
			base = new SimpleStringProperty();
		return base;
	}

	public void setBase(String base){ 
		baseProperty().setValue(base);  
	}
	public String getBase(){ 
		return baseProperty().get();  
	}

	// Toppings
	public StringProperty toppingsProperty(){
		if(toppings == null)
			toppings = new SimpleStringProperty();
		return toppings;
	}

	public void setToppings(String toppings){ 
		toppings = toppings.replaceAll(",", "\n");
//		toppings = toppings.replaceAll("+", "\n");
		toppingsProperty().setValue(toppings);  
	}
	public String getToppings(){ 
		return toppingsProperty().get();  
	}

	// CustomersId
	public StringProperty customersIdProperty(){
		if(customersId == null)
			customersId = new SimpleStringProperty();
		return customersId;
	}

	public void setCustomersId(String customersId){ 
		customersIdProperty().setValue(customersId);  
	}
	public String getCustomersId(){ 
		return customersIdProperty().get();  
	}
	
	// Status
	public StringProperty statusProperty(){
		if(status == null)
			status = new SimpleStringProperty();
		return status;
	}

	public void setStatus(String status){ 
		statusProperty().setValue(status);  
	}
	public String getStatus(){ 
		return statusProperty().get();  
	}
	
	@Override
	public String toString() {
		return getID()+" : "+getBase()+" : "+getToppings()+" : "+getStatus();
	}

}
