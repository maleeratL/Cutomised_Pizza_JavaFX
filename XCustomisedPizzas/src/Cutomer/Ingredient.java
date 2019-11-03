package Cutomer;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {
	
	private StringProperty ID;
	private StringProperty name;
	private StringProperty type;
	private StringProperty amount;
	private StringProperty unit;
	private StringProperty newAmount;
	private StringProperty dateTime;
	
	public Ingredient(String ID,String name, String type,String amount, String unit,String newAmount,String dateTime) {
		setID(ID);
		setName(name);
		setType(type);
		setAmount(amount);
		setUnit(unit);
		setNewAmount(newAmount);
		setDateTime(dateTime);
	}
	
	public Ingredient(String ID,String name, String type,String amount, String unit,String newAmount) {
		setID(ID);
		setName(name);
		setType(type);
		setAmount(amount);
		setUnit(unit);
		setNewAmount(newAmount);
	}
	
	public Ingredient(String ID,String name, String type,String amount,String unit) {
		setID(ID);
		setName(name);
		setType(type);
		setUnit(unit);
		setAmount(amount);
	}
	
//	public Ingredient(String ID, String dateTime) {
//		setID(ID);
//		setDateTime(dateTime);
//	}

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
	
	// Type
	public StringProperty typeProperty(){
		if(type == null)
			type = new SimpleStringProperty();
		return type;
	}

	public void setType(String type){ 
		typeProperty().setValue(type);  
	}
	public String getType(){ 
		return typeProperty().get();  
	}

	// Amount
	public StringProperty amountProperty(){
		if(amount == null)
			amount = new SimpleStringProperty();
		return amount;
	}

	public void setAmount(String amount){ 
		amountProperty().setValue(amount);  
	}
	public String getAmount(){ 
		return amountProperty().get();  
	}

	// unit
	public StringProperty unitProperty(){
		if(unit == null)
			unit = new SimpleStringProperty();
		return unit;
	}

	public void setUnit(String unit){ 
		unitProperty().setValue(unit);  
	}
	public String getUnit(){ 
		return unitProperty().get();  
	}


	// NewAmount
	public StringProperty newAmountProperty(){
		if(newAmount == null)
			newAmount = new SimpleStringProperty();
		return newAmount;
	}

	public void setNewAmount(String newAmount){ 
		newAmountProperty().setValue(newAmount);  
	}
	public String getNewAmount(){ 
		return newAmountProperty().get();  
	}

	// DateTime
	public StringProperty dateTimeProperty(){
		if(dateTime == null)
			dateTime = new SimpleStringProperty();
		return dateTime;
	}

	public void setDateTime(String dateTime){ 
		dateTimeProperty().setValue(dateTime);  
	}
	public String getDateTime(){ 
		return dateTimeProperty().get();  
	}

	@Override
	public String toString() {
		return getID()+" : "+getName()+" : "+getAmount()+" "+getUnit();
	}

}
