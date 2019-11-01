

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ControllerDB {
	
	ArrayList<Order> orderListDB = new ArrayList<Order>();
	ArrayList<Ingredient> ingredientListDB = new ArrayList<Ingredient>();
	
	public boolean accessSQL(String dbUser,String usrPass, String tableName) {
		orderListDB.clear();
		ingredientListDB.clear();
		boolean result= false;
		try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;

            String url = "jdbc:mariadb://localhost/pizzadb";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();

            String sql = "select * from "+tableName;

            ResultSet rs = stmt.executeQuery(sql);
            if(tableName.equals("orders")) {
            	while(rs.next())
                {
                	Order o = new Order(rs.getString("id"),rs.getString("base"),rs.getString("toppings"),rs.getString("status"),rs.getString("customerId"));
                	orderListDB.add(o);
                }
            }
            else if(tableName.equals("ingredients")) {
            	while(rs.next())
                {
            		Ingredient ing = new Ingredient(rs.getString("id"),rs.getString("name"),rs.getString("type"),rs.getString("amount"),rs.getString("unit"));
            		ingredientListDB.add(ing);
                }
            }
            else if(tableName.equals("inventory")) {
            	while(rs.next())
                {
//            		Date date;
//            		Timestamp timestamp = rs.getTimestamp("date_time");
//            		if (timestamp != null)
//            		    date = new java.util.Date(timestamp.getTime());
            		Ingredient ing = new Ingredient(rs.getString("id"),rs.getString("name"),rs.getString("type"),rs.getString("amount"),rs.getString("unit"),"",String.valueOf(rs.getDate("date_time")+" "+rs.getTime("date_time")));
            		ingredientListDB.add(ing);
                }
            }
//            while(rs.next())
//            {
//            	Order p = new Order(rs.getString("id"),rs.getString("orderList"),rs.getString("customerName"),rs.getString("status"));
//            	orderListDB.add(p);
//            }
            rs.close();
            stmt.close();
            con.close();
            
            result =true;
		
		}
		catch(Exception ex) {
//			ex.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public boolean updateOrderStatusSQL(String dbUser,String usrPass,Order o) {
		boolean result= false;
		try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;

            String url = "jdbc:mariadb://localhost/pizzadb";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();     
           
            String query = "update orders set status = 'produced' where id = "+o.getID(); 
            
            ResultSet rs = stmt.executeQuery(query);
            
            rs.close();
            stmt.close();
            con.close();
            result =true;
		}
		catch(Exception ex) {
//			System.out.println("Cannot Save");
			result =false;
		}
		return result;
	}
	
	public boolean updateStockSQL(String dbUser,String usrPass,Ingredient ing,String current, String update) {
		boolean result= false;
		try {
            Class.forName("org.mariadb.jdbc.Driver");

            Connection con = null;

            String url = "jdbc:mariadb://localhost/pizzadb";

            con = DriverManager.getConnection(url, dbUser, usrPass);

            Statement stmt = con.createStatement();  

            Double totalNewAmount = Double.valueOf(current);
            totalNewAmount = totalNewAmount+Double.valueOf(update);
            
//            System.out.println("=========================");
//            System.out.println(ing.getNewAmount()+", "+ing.getNewAmount());
//            System.out.println(Double.valueOf(ing.getNewAmount())+Double.valueOf(ing.getNewAmount()));
//            System.out.println("Total: "+totalNewAmount);

            String query = "update ingredients set amount = "+ totalNewAmount +" where id = "+ing.getID(); 

            ResultSet rs = stmt.executeQuery(query);

            rs.close();

            query = "insert into inventory (name,type,amount,unit,date_time)VALUES(?,?,?,?,now())";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, ing.getName());
            preparedStmt.setString (2, ing.getType());
            preparedStmt.setString (3, ing.getNewAmount());
            preparedStmt.setString (4, ing.getUnit());
            preparedStmt.execute();
            
            
            stmt.close();
            con.close();
            result =true;
		}
		catch(Exception ex) {
//			System.out.println("Cannot Save");
			result =false;
		}
		return result;
	}
	
}
