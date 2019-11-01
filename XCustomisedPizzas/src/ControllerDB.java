

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

}
