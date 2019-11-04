package JUnit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Cutomer.ControllerDB;
import Cutomer.Ingredient;
import Cutomer.Order;

class ControllerDBTest {
	ControllerDB db = new ControllerDB();
	String dbUser = "myuser";
	String usrPass = "mypass";
//	ArrayList<Order> orderListDB = new ArrayList<Order>();
//	ArrayList<Ingredient> ingredientListDB = new ArrayList<Ingredient>();

	//========================= AccessSQL =================
	@Test
	void testAccessSQL() {
		String tableName = "inventory";
		boolean result = db.accessSQL(dbUser, usrPass, tableName);
		assertEquals(true,result);
	}
	
	@Test
	void testAccessSQL2() {
		String tableName = "customers";
		boolean result = db.accessSQL(dbUser, usrPass, tableName);
		assertEquals(true,result);
	}
	
	@Test
	void testAccessSQL3() {
		String tableName = "player";
		boolean result = db.accessSQL(dbUser, usrPass, tableName);
		assertEquals(false,result);
	}
	
	//========================= UpdateOrderStatusSQL =================
	@Test
	void testUpdateOrderStatusSQL() {
		Order o = new Order("1", "thin", "+mushroom,+bacon,Cheese", "waiting", "2");
		boolean result = db.updateOrderStatusSQL(dbUser, usrPass, o);
		assertEquals(true,result);
	}
	
	@Test
	void testUpdateOrderStatusSQL2() {
		Order o = new Order("hih", "thin", "+mushroom,+bacon,Cheese", "waiting", "jkj");
		boolean result = db.updateOrderStatusSQL(dbUser, usrPass, o);
		assertEquals(true,result);
	}
	
	//============================ UpdateStockSQL ===================
	@Test
	void testUpdateStockSQL() {
		Ingredient ing = new Ingredient("1", "tomatoes", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		String current = ing.getAmount();
		String update = "200";
		double result = db.updateStockSQL(dbUser, usrPass, ing, current, update);
		assertEquals(400,result);
	}
	
	@Test
	void testUpdateStockSQL2() {
		Ingredient ing = new Ingredient("1", "tomatoes", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		String current = ing.getAmount();
		String update = "200.5";
		double result = db.updateStockSQL(dbUser, usrPass, ing, current, update);
		assertEquals(400.5,result);
	}

	//=========================== SaveStockInSQL ====================
	@Test
	void testSaveStockInSQL() {
		Ingredient ing = new Ingredient("1", "tomatoes", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.saveStockInSQL(dbUser, usrPass, ing);
		assertEquals(true,result);
	}
	
	@Test
	void testSaveStockInSQL2() {
		Ingredient ing = new Ingredient("1", "tomatoes", "veggies", "jjl", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.saveStockInSQL(dbUser, usrPass, ing);
		assertEquals(false,result);
	}

	//=========================== CheckDuplicate ====================
	@Test
	void testCheckDuplicate() {
		Ingredient ing = new Ingredient("1", "tomatoes", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.checkDuplicate(dbUser, usrPass, ing);
		assertEquals(true,result);
	}
	
	@Test
	void testCheckDuplicate2() {
		Ingredient ing = new Ingredient("1", "pudding", "dessert", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.checkDuplicate(dbUser, usrPass, ing);
		assertEquals(false,result);
	}
	
	@Test
	void testCheckDuplicate3() {
		Ingredient ing = new Ingredient("1", "tomato", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.checkDuplicate(dbUser, usrPass, ing);
		assertEquals(false,result);
	}

	//============================ DeleteStock =====================
	@Test
	void testDeleteStock() {
		Ingredient ing = new Ingredient("1", "cheese", "veggies", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.deleteStock(dbUser, usrPass, ing);
		assertEquals(false,result);
	}
	
	@Test
	void testDeleteStock2() {
		Ingredient ing = new Ingredient("1", "chicken", "meat", "200", "grams", "100","2019-11-02 20:54:27");
		boolean result = db.deleteStock(dbUser, usrPass, ing);
		assertEquals(true,result);
	}
	
	//============================ editStockNameSQL =====================
	@Test
	void testEditStockNameSQL() {
		Ingredient ing = new Ingredient("9", "ham", "meat", "200", "grams", "100","2019-11-02 20:54:27");
		String editName = "Carrot";
		String editType = "veggies";
		boolean result = db.editStockNameSQL(dbUser, usrPass, ing, editName, editType);
		assertEquals(true,result);
	}
	
	
	@Test
	void testEditStockNameSQL2() {
		Ingredient ing = new Ingredient("1", "chicken", "meat", "200", "grams", "100","2019-11-02 20:54:27");
		String editName = "Tomatoes";
		String editType = "veggies";
		boolean result = db.editStockNameSQL(dbUser, usrPass, ing, editName, editType);
		assertEquals(true,result);
	}


}
