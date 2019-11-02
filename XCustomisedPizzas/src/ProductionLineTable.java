

import javafx.application.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Callback;

public class ProductionLineTable extends Application {
   private TableView<Order> tableOrder;
   private TableView<Ingredient> tableReorder;
   private TableView<Ingredient> tableStock;
   private TableView<Order> tableProduced;
   private TableView<Ingredient> tableInventory;
   private ObservableList dataOrder;
   private ObservableList dataReorder;
   private ObservableList dataStock;
   private ObservableList dataProduced;
   private ObservableList dataInventory;
   private TextField filterField;
//   private HBox orderHb;
   private Text ordertxtStatus;
   private Text reordertxtStatus;
   private VBox vboxTab1;
   private VBox vboxTab2;
   private VBox vboxTab3;
   private VBox vboxTab4;
   private VBox vboxTab5;
   private ComboBox comboBox;
   private Button statusbtn;
//   private Button delbtn;
   private Button reorderbtn;
   
   private String dbUser = "myuser";
   private String usrPass = "mypass";
   
   @Override
   public void start(Stage primaryStage) {
      primaryStage.setTitle("tableOrder View Demonstration.");
      this.setOrderTable();
      this.setReorderTable();
      this.setStockTable();
      this.setProduceTable();
      this.setInventoryTable();
      this.headingTabSetUp();
      // W x H
      Scene scene = new Scene(setUptab(), 1050, 775);
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   public void headingTabSetUp() {
	   ordertxtStatus = new Text();
	   reordertxtStatus = new Text();
	   //====================== Tab 1 ======================
	   Label labelTab1 = new Label("Current Order List");
	   labelTab1.setTextFill(Color.DARKBLUE);
	   labelTab1.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab1 = new HBox();
	   labelHbTab1.setAlignment(Pos.CENTER);
	   labelHbTab1.getChildren().add(labelTab1);

	   statusbtn = new Button("Update Status");
//	   statusbtn.setOnAction(new StatusButtonListener());
	   statusbtn.setVisible(false);
//	   delbtn = new Button("Delete");
//	   delbtn.setOnAction(new DeleteButtonListener());
	   
	   HBox buttonHbTab1 = new HBox(10);
	   buttonHbTab1.setAlignment(Pos.CENTER);
	   buttonHbTab1.setPadding(new Insets(0, 0, 20, 0));
	   buttonHbTab1.getChildren().addAll(statusbtn);
//	   buttonHbTab1.getChildren().addAll(statusbtn,delbtn);

	   reordertxtStatus.setText("Reorder Amount >> Double click on number");

	   vboxTab1 = new VBox(20);
	   vboxTab1.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab1.getChildren().addAll(labelHbTab1, tableOrder, ordertxtStatus,buttonHbTab1);

	   //====================== Tab 2 ======================
	   Label labelTab2 = new Label("Reorder List");
	   labelTab2.setTextFill(Color.DARKBLUE);
	   labelTab2.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab2 = new HBox();
	   labelHbTab2.setAlignment(Pos.CENTER);
	   labelHbTab2.getChildren().add(labelTab2);

	   reorderbtn = new Button("Reorder");
//	   reorderbtn.setOnAction(new ReorderButtonListener());
	   reorderbtn.setVisible(false);

	   HBox buttonHbTab2 = new HBox(10);
	   buttonHbTab2.setAlignment(Pos.CENTER);
	   buttonHbTab2.setPadding(new Insets(0, 0, 20, 0));
	   buttonHbTab2.getChildren().addAll(reorderbtn);

	   vboxTab2 = new VBox(20);
	   vboxTab2.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab2.getChildren().addAll(labelHbTab2, tableReorder, reordertxtStatus,buttonHbTab2);

	   //====================== Tab 3 ======================
	   Label labelTab3 = new Label("Stock Ingredient List");
	   labelTab3.setTextFill(Color.DARKBLUE);
	   labelTab3.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab3 = new HBox();
	   labelHbTab3.setAlignment(Pos.CENTER);
	   labelHbTab3.getChildren().add(labelTab3);

	   vboxTab3 = new VBox(20);
	   vboxTab3.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab3.getChildren().addAll(labelHbTab3, tableStock);


	   //====================== Tab 4 ======================
	   Label labelTab4 = new Label("Produced Order List");
	   labelTab4.setTextFill(Color.DARKBLUE);
	   labelTab4.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab4 = new HBox();
	   labelHbTab4.setAlignment(Pos.CENTER);
	   labelHbTab4.getChildren().add(labelTab4);

	   vboxTab4 = new VBox(20);
	   vboxTab4.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab4.getChildren().addAll(labelHbTab4,tableProduced);
	   
	   //====================== Tab 5 ======================
	   Label labelTab5 = new Label("Inventory List");
	   labelTab5.setTextFill(Color.DARKBLUE);
	   labelTab5.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab5 = new HBox();
	   labelHbTab5.setAlignment(Pos.CENTER);
	   labelHbTab5.getChildren().add(labelTab5);
	   
	   comboBox = new ComboBox();
	   comboBox.setValue("All");
       comboBox.getItems().add("Name");
       comboBox.getItems().add("Type");
       comboBox.getItems().add("Amount");
       comboBox.getItems().add("Date and Time");

       VBox hboxTab5 = new VBox(filterField,comboBox);

	   vboxTab5 = new VBox(20);
	   vboxTab5.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab5.getChildren().addAll(labelHbTab5,hboxTab5,tableInventory);
   }

   public void tabListener(Tab tab,Tab tab2,Tab tab3, Tab tab4,Tab tab5) {
	   tab.setOnSelectionChanged(event -> {
		   if (tab.isSelected()) {
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
			   tableProduced.getItems().clear();
			   dataOrder = dummydataOrder("waiting");
			   tableOrder.setItems(dataOrder);
		   }
	   });
	   
	   tab2.setOnSelectionChanged(event -> {
		   if (tab2.isSelected()) {
			   tableOrder.getItems().clear();
			   tableStock.getItems().clear();
			   tableProduced.getItems().clear();
//			   tableInventory.getItems().clear();
			   dataReorder = dummydataReorder();
			   tableReorder.setItems(dataReorder);
			   if (tableReorder.getItems().size() == 0) {
				   reordertxtStatus.setText("");
			   }
			   else {
				   reordertxtStatus.setText("Reorder Amount >> Double click on number");
			   }
		   }
	   });
	   
	   tab3.setOnSelectionChanged(event -> {
		   if (tab3.isSelected()) {
			   tableOrder.getItems().clear();
			   tableReorder.getItems().clear();
			   tableProduced.getItems().clear();
//			   tableInventory.getItems().clear();
			   dataStock = dummydataIngredient();
			   tableStock.setItems(dataStock);
		   }
	   });
	   
	   tab4.setOnSelectionChanged(event -> {
		   if (tab4.isSelected()) {
			   tableOrder.getItems().clear();
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
//			   tableInventory.getItems().clear();
			   dataProduced = dummydataOrder("produced");
			   tableProduced.setItems(dataProduced);
		   }
	   });
	   
	   tab5.setOnSelectionChanged(event -> {
		   if (tab5.isSelected()) {
			   tableOrder.getItems().clear();
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
			   dataInventory = dummydataInventory();
			   tableInventory.setItems(dataInventory);
//			   this.setInventoryTable();
		   }
	   });
   }
   
   public TabPane setUptab() {
	   //Tab menu
	   TabPane tabpane = new TabPane(); 
	   tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

	   // create Tab 
	   Tab tab = new Tab("Current orders"); 
	   Tab tab2 = new Tab("Reorder"); 
	   Tab tab3 = new Tab("Stock"); 
	   Tab tab4 = new Tab("Produced orders");
	   Tab tab5 = new Tab("Inventory");

	   // add tab 
	   tabpane.getTabs().addAll(tab,tab4,tab2,tab3,tab5); 
//	   tabpane.getTabs().add(tab2); 
	   
	   tab.setContent(vboxTab1);
	   tab2.setContent(vboxTab2);
	   tab3.setContent(vboxTab3);
	   tab4.setContent(vboxTab4);
	   tab5.setContent(vboxTab5);
	   
	   // TabListener
	   tabListener(tab,tab2,tab3,tab4,tab5);
	   
	   return tabpane;
   }
   
   public void setOrderTable() {
	   tableOrder = new TableView<>();
	   dataOrder = dummydataOrder("waiting");
	   tableOrder.setItems(dataOrder);
	   
	   TableColumn<Order,String> IDCol = new TableColumn<>("ID");
	   IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	   TableColumn<Order,String> baseDetailsCol = new TableColumn<>("Base");
	   baseDetailsCol.setCellValueFactory(new PropertyValueFactory<>("base"));
	   TableColumn<Order,String> toppingsDetailsCol = new TableColumn<>("Toppings");
	   toppingsDetailsCol.setCellValueFactory(new PropertyValueFactory<>("toppings"));
	   TableColumn<Order,String> customerIdCol = new TableColumn<>("Customer ID");
	   customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customersId"));
	   TableColumn<Order,String> statusCol = new TableColumn("status");
	   statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

	   tableOrder.getColumns().setAll(IDCol, baseDetailsCol,toppingsDetailsCol,customerIdCol, statusCol);
	   tableOrder.setPrefWidth(400);
	   tableOrder.setPrefHeight(850);
	   tableOrder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableOrder.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandler());
	   tableOrder.setEditable(true);
	   
   }
   
   public void setReorderTable() {
	   tableReorder = new TableView<>();
	   dataReorder = dummydataReorder();
	   tableReorder.setItems(dataReorder);
	   
	   TableColumn<Ingredient,String> amountCol = new TableColumn<>("Current Amount");
	   amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	   TableColumn<Ingredient,String> unitCol = new TableColumn<>("Unit");
	   unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
	   TableColumn<Ingredient,String> nameCol = new TableColumn<>("Name");
	   nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	   TableColumn<Ingredient,String> typeCol = new TableColumn<>("Type");
	   typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
	   TableColumn<Ingredient,String> newAmountCol = new TableColumn("Reorder Amount");
	   newAmountCol.setCellValueFactory(new PropertyValueFactory<>("newAmount"));

	   tableReorder.getColumns().setAll(nameCol,typeCol,amountCol,newAmountCol,unitCol);
	   tableReorder.setPrefWidth(400);
	   tableReorder.setPrefHeight(850);
	   tableReorder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableReorder.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandlerReorder());
	   tableReorder.setEditable(true);
	   
//	   statusCol.setCellFactory(getCustomCellFactory("red","16px","Arial","center"));
	   
	   newAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
	   newAmountCol.setOnEditCommit(event -> (event.getTableView().
			   getItems().get(event.getTablePosition().getRow())).
			   setNewAmount(event.getNewValue()));
   }
   
   public void setStockTable() {
	   tableStock = new TableView<>();
	   dataStock = dummydataIngredient();
	   tableStock.setItems(dataStock);
	   
	   TableColumn<Ingredient,String> IDCol = new TableColumn<>("ID");
	   IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	   TableColumn<Ingredient,String> amountCol = new TableColumn<>("Total Amount");
	   amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	   TableColumn<Ingredient,String> unitCol = new TableColumn<>("Unit");
	   unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
	   TableColumn<Ingredient,String> nameCol = new TableColumn<>("Name");
	   nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	   TableColumn<Ingredient,String> typeCol = new TableColumn<>("Type");
	   typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
//	   TableColumn<Ingredient,String> newAmountCol = new TableColumn("New Amount Order");
//	   newAmountCol.setCellValueFactory(new PropertyValueFactory<>("newAmount"));

	   tableStock.getColumns().setAll(IDCol,nameCol,typeCol,amountCol,unitCol);
	   tableStock.setPrefWidth(400);
	   tableStock.setPrefHeight(850);
	   tableStock.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableStock.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandlerReorder());
	   tableStock.setEditable(true);
	   
//	   statusCol.setCellFactory(getCustomCellFactory("red","16px","Arial","center"));
	   
//	   newAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
//	   newAmountCol.setOnEditCommit(event -> (event.getTableView().
//			   getItems().get(event.getTablePosition().getRow())).
//			   setNewAmount(event.getNewValue()));
   }
   
   public void setProduceTable() {
	   tableProduced = new TableView<>();
	   dataProduced = dummydataOrder("produced");
	   tableProduced.setItems(dataOrder);
	   
	   TableColumn<Order,String> IDCol = new TableColumn<>("ID");
	   IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	   TableColumn<Order,String> baseDetailsCol = new TableColumn<>("Base");
	   baseDetailsCol.setCellValueFactory(new PropertyValueFactory<>("base"));
	   TableColumn<Order,String> toppingsDetailsCol = new TableColumn<>("Toppings");
	   toppingsDetailsCol.setCellValueFactory(new PropertyValueFactory<>("toppings"));
	   TableColumn<Order,String> customerIdCol = new TableColumn<>("Customer ID");
	   customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customersId"));
	   TableColumn<Order,String> statusCol = new TableColumn("status");
	   statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

	   tableProduced.getColumns().setAll(IDCol, baseDetailsCol,toppingsDetailsCol,customerIdCol, statusCol);
	   tableProduced.setPrefWidth(400);
	   tableProduced.setPrefHeight(850);
	   tableProduced.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableProduced.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandler());
	   tableProduced.setEditable(true);
	   
//	   statusCol.setCellFactory(getCustomCellFactory("red","16px","Arial","center"));
	   
//	   statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
//	   statusCol.setOnEditCommit(event -> (event.getTableView().
//			   getItems().get(event.getTablePosition().getRow())).
//			   setStatus(event.getNewValue()));
   }
   
   
   public void setInventoryTable() {
	   tableInventory = new TableView<>();
	   dataInventory = dummydataInventory();
	   tableInventory.setItems(dataInventory);
	   filterField = new TextField();
	   
	   TableColumn<Ingredient,String> IDCol = new TableColumn<>("ID");
	   IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	   TableColumn<Ingredient,String> amountCol = new TableColumn<>("Total Amount");
	   amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
	   TableColumn<Ingredient,String> unitCol = new TableColumn<>("Unit");
	   unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
	   TableColumn<Ingredient,String> nameCol = new TableColumn<>("Name");
	   nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
	   TableColumn<Ingredient,String> typeCol = new TableColumn<>("Type");
	   typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
	   TableColumn<Ingredient,String> dateTimeCol = new TableColumn("Date / Time");
	   dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

	   tableInventory.getColumns().setAll(IDCol,nameCol,typeCol,amountCol,unitCol,dateTimeCol);
	   tableInventory.setPrefWidth(400);
	   tableInventory.setPrefHeight(850);
	   tableInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableInventory.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandlerReorder());
	   tableInventory.setEditable(true);
	       
   }
   
   private Callback<TableColumn<Order, String>, TableCell<Order, String>> getCustomCellFactory(final String color,String fontSize, String fontWeight,String fontFamily,String textAlign) {
       return new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {

           @Override
           public TableCell<Order, String> call(TableColumn<Order, String> param) {
               TableCell<Order, String> cell = new TableCell<Order, String>() {

                   @Override
                   public void updateItem(final String item, boolean empty) {
                       if (item != null) {
                           setText(item);
                           setStyle("-fx-text-fill: " + color + ";\n"+
                        		   "-fx-font-size: " + fontSize + ";\n"+
                        		   "-fx-font-family: " + fontFamily + ";\n"+
                        		   "-fx-alignment: " + textAlign + ";\n"+
                        		   "-fx-font-weight: " + fontWeight + ";");
//                           setStyle("-fx-font-size: " + fontsize + ";");
//                           setStyle("-fx-font-family: " + fontfamily + ";");
                       }
                   }
               };
               return cell;
           }
       };
   }
   
   
   private class RowChangeHandler implements ChangeListener {
      @Override
      public void changed(ObservableValue ov, Object oldVal, Object newVal) {
         int val = ((Number)newVal).intValue();
         if (dataOrder.size()<=0) {
        	 statusbtn.setVisible(false);
//        	 reorderbtn.setVisible(false);
            return;
         }
        Order pb= (Order) dataOrder.get(val);
        ordertxtStatus.setText(pb.toString());
        statusbtn.setVisible(true);
//        reorderbtn.setVisible(true);
      }
   }
   
   private class RowChangeHandlerReorder implements ChangeListener {
	      @Override
	      public void changed(ObservableValue ov, Object oldVal, Object newVal) {
	         int val = ((Number)newVal).intValue();
	         if (dataReorder.size()<=0) {
//	        	 statusbtn.setVisible(false);
	        	 reorderbtn.setVisible(false);
	            return;
	         }
	        Ingredient pb= (Ingredient) dataReorder.get(val);
//	        ordertxtStatus.setText(pb.toString());
//	        statusbtn.setVisible(true);
	        reorderbtn.setVisible(true);
	      }
	   }
   

   public ObservableList<Order> dummydataOrder(String txt) {
	   ObservableList<Order> records = FXCollections.observableArrayList();
	   records.clear();
	   ControllerDB db = new ControllerDB();
	   String tableName = "orders";
	   db.accessSQL(dbUser, usrPass,tableName);
	   for(Order o: db.orderListDB) {
		   if(o.getStatus().equalsIgnoreCase(txt)) {
			   records.add(new Order(o.getID(),o.getBase(),o.getToppings(), o.getStatus(),o.getCustomersId()));
		   }
	   }
	   return records;
   }

   public ObservableList<Ingredient> dummydataIngredient() {
	   ObservableList<Ingredient> records = FXCollections.observableArrayList();

	   records.clear();
	   ControllerDB db = new ControllerDB();
	   String tableName = "ingredients";
	   db.accessSQL(dbUser, usrPass,tableName);
	   for(Ingredient ing: db.ingredientListDB) {
		   records.add(new Ingredient(ing.getID(),ing.getName(),ing.getType(),ing.getAmount(),ing.getUnit(),"100"));
	   }
	   return records;
   }
   
   public ObservableList<Ingredient> dummydataReorder() {
	   ObservableList<Ingredient> records = FXCollections.observableArrayList();	   
	   for(Ingredient ing: dummydataIngredient()) {
		   if(Integer.valueOf(ing.getAmount())<500) {
			   records.add(new Ingredient(ing.getID(),ing.getName(),ing.getType(),ing.getAmount(),ing.getUnit(),"100"));
		   }
	   }	   
	   return records;
   }
   
   public ObservableList<Ingredient> dummydataInventory() {
	   ObservableList<Ingredient> records = FXCollections.observableArrayList();

	   records.clear();
	   ControllerDB db = new ControllerDB();
	   String tableName = "inventory";
	   db.accessSQL(dbUser, usrPass,tableName);
	   for(Ingredient ing: db.ingredientListDB) {
		   records.add(new Ingredient(ing.getID(),ing.getName(),ing.getType(),ing.getAmount(),ing.getUnit(),ing.getNewAmount(),ing.getDateTime()));
	   }
	   return records;
   }
   
   
   
   private class DeleteButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// Get selected row and delete
			int ix = tableOrder.getSelectionModel().getSelectedIndex();
			Order ordertxt = (Order) tableOrder.getSelectionModel().getSelectedItem();
			if(tableOrder.getItems().size()!=0) {
				dataOrder.remove(ix);
				
				//call to save to DB here for reorder0
			}
			
			ordertxtStatus.setText("Deleted: " + ordertxt.toString());

			// Select a row
			if (tableOrder.getItems().size() == 0) {

				ordertxtStatus.setText("No data in table !");
//				delbtn.setVisible(false);
				return;
			}

			if (ix != 0) {

				ix = ix -1;
			}

			tableOrder.requestFocus();
			tableOrder.getSelectionModel().select(ix);
			tableOrder.getFocusModel().focus(ix);
		}
	}
   
   private class DeleteIngredientListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// Get selected row and delete
			int ix = tableReorder.getSelectionModel().getSelectedIndex();
			Ingredient ordertxt = (Ingredient) tableReorder.getSelectionModel().getSelectedItem();
			if(tableReorder.getItems().size()!=0) {
				dataReorder.remove(ix);
				
				//call to save to DB here for reorder0
			}
			
			reordertxtStatus.setText("Reorder: " + ordertxt.toString());

			// Select a row
			if (tableReorder.getItems().size() == 0) {

				reordertxtStatus.setText("No data in table !");
//				delbtn.setVisible(false);
				return;
			}

			if (ix != 0) {

				ix = ix -1;
			}

			tableReorder.requestFocus();
			tableReorder.getSelectionModel().select(ix);
			tableReorder.getFocusModel().focus(ix);
		}
	}

  

   
   public static void main(String [] args) {
      Application.launch(args);
   }
}