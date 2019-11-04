package Cutomer;

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
   private TableView<Ingredient> tableEdit;
   private ObservableList dataOrder;
   private ObservableList dataReorder;
   private ObservableList dataStock;
   private ObservableList dataProduced;
   private ObservableList dataInventory;
   private ObservableList dataEdit;
   private TextField filterField;
   private TextField nameField;
   private TextField typeField;
   private TextField amountField;
   private TextField editNameField;
   private TextField editTypeField;
//   private Text ordertxtStatus;
   private Text reordertxtStatus;
   private Text addtxtStock;
   private Text edittxtStock;
   private VBox vboxTab1;
   private VBox vboxTab2;
   private VBox vboxTab3;
   private VBox vboxTab4;
   private VBox vboxTab5;
   private VBox vboxTab6;
   private Button statusbtn;
   private Button delbtn;
   private Button addbtn;
   private Button reorderbtn;
   private Button editbtn;
   
   private String dbUser = "myuser";
   private String usrPass = "mypass";
   
   @Override
   public void start(Stage primaryStage) {
      primaryStage.setTitle("TableOrder View");
      this.setOrderTable();
      this.setReorderTable();
      this.setStockTable();
      this.setProduceTable();
      this.setInventoryTable();
      this.setEditTable();
      this.headingTabSetUp();
      // W x H
      Scene scene = new Scene(setUptab(), 1050, 775);
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   public void headingTabSetUp() {
//	   ordertxtStatus = new Text();
	   reordertxtStatus = new Text();
	   addtxtStock = new Text();
	   edittxtStock = new Text();
	   //====================== Tab 1 ======================
	   Label labelTab1 = new Label("Current Order List");
	   labelTab1.setTextFill(Color.DARKBLUE);
	   labelTab1.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab1 = new HBox();
	   labelHbTab1.setAlignment(Pos.CENTER);
	   labelHbTab1.getChildren().add(labelTab1);

	   statusbtn = new Button("Update Status");
	   statusbtn.setOnAction(new StatusButtonListener());
	   statusbtn.setVisible(false);
	   
	   HBox buttonHbTab1 = new HBox(10);
	   buttonHbTab1.setAlignment(Pos.CENTER);
	   buttonHbTab1.setPadding(new Insets(0, 0, 20, 0));
	   buttonHbTab1.getChildren().addAll(statusbtn);
//	   buttonHbTab1.getChildren().addAll(statusbtn,delbtn);

	   vboxTab1 = new VBox(20);
	   vboxTab1.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab1.getChildren().addAll(labelHbTab1, tableOrder,buttonHbTab1);

	   //====================== Tab 2 ======================
	   Label labelTab2 = new Label("Reorder List");
	   labelTab2.setTextFill(Color.DARKBLUE);
	   labelTab2.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab2 = new HBox();
	   labelHbTab2.setAlignment(Pos.CENTER);
	   labelHbTab2.getChildren().add(labelTab2);
	   
	   reordertxtStatus.setText("Reorder Amount >> Double click on number");

	   reorderbtn = new Button("Reorder");
	   reorderbtn.setOnAction(new ReorderButtonListener());
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

	   addbtn = new Button("Add");
	   addbtn.setOnAction(new AddButtonListener());
	   delbtn = new Button("Delete");
	   delbtn.setOnAction(new DeleteStockButtonListener());
	   
	   nameField = new TextField();
	   nameField.setId("textField");
	   nameField.setPromptText("Name");
	   
	   typeField = new TextField();
	   typeField.setId("textField");
	   typeField.setPromptText("Type");
	   
	   amountField = new TextField();
	   amountField.setId("textField");
	   amountField.setPromptText("Amount");

	   HBox buttonHb = new HBox(10);
	   buttonHb.setAlignment(Pos.CENTER);
	   buttonHb.setPadding(new Insets(0, 0, 20, 0));
	   buttonHb.getChildren().addAll(nameField,typeField,amountField,addbtn,delbtn);

	   vboxTab3 = new VBox(20);
	   vboxTab3.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab3.getChildren().addAll(labelHbTab3, tableStock,addtxtStock,buttonHb);


	   //====================== Tab 4 ======================
	   Label labelTab4 = new Label("Produced Order List");
	   labelTab4.setTextFill(Color.DARKBLUE);
	   labelTab4.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab4 = new HBox();
	   labelHbTab4.setAlignment(Pos.CENTER);
	   labelHbTab4.getChildren().add(labelTab4);

	   vboxTab4 = new VBox(20);
	   vboxTab4.setPadding(new Insets(20, 20, 60, 20));
	   vboxTab4.getChildren().addAll(labelHbTab4,tableProduced);
	   
	   //====================== Tab 5 ======================
	   Label labelTab5 = new Label("Inventory List");
	   labelTab5.setTextFill(Color.DARKBLUE);
	   labelTab5.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab5 = new HBox();
	   labelHbTab5.setAlignment(Pos.CENTER);
	   labelHbTab5.getChildren().add(labelTab5);
	   
	   vboxTab5 = new VBox(20);
	   vboxTab5.setPadding(new Insets(20, 20, 60, 20));
	   vboxTab5.getChildren().addAll(labelHbTab5,filterField,tableInventory);
	   
	 //====================== Tab 6 ======================
	   Label labelTab6 = new Label("Edit Ingredients Stock");
	   labelTab6.setTextFill(Color.DARKBLUE);
	   labelTab6.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
	   HBox labelHbTab6 = new HBox();
	   labelHbTab6.setAlignment(Pos.CENTER);
	   labelHbTab6.getChildren().add(labelTab6);
	   
	   editNameField = new TextField();
	   editNameField.setId("textField");
	   editNameField.setPromptText("Name");
	   
	   editTypeField = new TextField();
	   editTypeField.setId("textField");
	   editTypeField.setPromptText("Type");
	   
	   editbtn = new Button("Save change");
	   editbtn.setOnAction(new EditButtonListener());
	   
	   HBox editFormTab6 = new HBox();
	   editFormTab6.setAlignment(Pos.CENTER);
	   editFormTab6.getChildren().addAll(editNameField,editTypeField,editbtn);
	   editFormTab6.setSpacing(20);
	   editFormTab6.setPadding(new Insets(10, 20, 20, 20));
	   
//	   VBox editVbTab6 = new VBox(10);
//	   editVbTab6.setAlignment(Pos.CENTER);
//	   editVbTab6.getChildren().addAll(tableEdit,edittxtStock,editFormTab6);
	   
	   vboxTab6 = new VBox(20);
	   vboxTab6.setPadding(new Insets(20, 20, 20, 20));
	   vboxTab6.getChildren().addAll(labelHbTab6,tableEdit,edittxtStock,editFormTab6);
	   
   }

   public void tabListener(Tab tab,Tab tab2,Tab tab3, Tab tab4,Tab tab5, Tab tab6) {
	   tab.setOnSelectionChanged(event -> {
		   if (tab.isSelected()) {
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
			   tableProduced.getItems().clear();
			   tableEdit.getItems().clear();
			   dataOrder = dummydataOrder("waiting");
			   tableOrder.setItems(dataOrder);
//			   if (tableOrder.getItems().size() > 0) {
//				   ordertxtStatus.setText("");
//			   }
		   }
	   });
	   
	   tab2.setOnSelectionChanged(event -> {
		   if (tab2.isSelected()) {
			   tableOrder.getItems().clear();
			   tableStock.getItems().clear();
			   tableProduced.getItems().clear();
			   tableEdit.getItems().clear();
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
			   tableEdit.getItems().clear();
			   addtxtStock.setText("");
			   delbtn.setVisible(false);
			   dataStock = dummydataIngredient();
			   tableStock.setItems(dataStock);
		   }
	   });
	   
	   tab4.setOnSelectionChanged(event -> {
		   if (tab4.isSelected()) {
			   tableOrder.getItems().clear();
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
			   tableEdit.getItems().clear();
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
			   tableEdit.getItems().clear();
			   tableProduced.getItems().clear();
			   dataInventory = dummydataInventory();
			   tableInventory.setItems(dataInventory);
//			   this.setInventoryTable();
		   }
	   });
	   
	   tab6.setOnSelectionChanged(event -> {
		   if (tab6.isSelected()) {
			   tableOrder.getItems().clear();
			   tableReorder.getItems().clear();
			   tableStock.getItems().clear();
			   tableProduced.getItems().clear();
			   dataEdit = dummydataIngredient();
			   tableEdit.setItems(dataEdit);
			   editNameField.setText("");
			   editTypeField.setText("");
			   edittxtStock.setText("");
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
	   Tab tab6 = new Tab("Edit");

	   // add tab 
	   tabpane.getTabs().addAll(tab,tab4,tab2,tab3,tab5,tab6); 
//	   tabpane.getTabs().add(tab2); 
	   
	   tab.setContent(vboxTab1);
	   tab2.setContent(vboxTab2);
	   tab3.setContent(vboxTab3);
	   tab4.setContent(vboxTab4);
	   tab5.setContent(vboxTab5);
	   tab6.setContent(vboxTab6);
	   
	   // TabListener
	   tabListener(tab,tab2,tab3,tab4,tab5,tab6);
	   
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
	   TableColumn<Order,String> statusCol = new TableColumn("Status");
	   statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

	   tableOrder.getColumns().setAll(IDCol, baseDetailsCol,toppingsDetailsCol,customerIdCol, statusCol);
	   tableOrder.setPrefWidth(400);
	   tableOrder.setPrefHeight(850);
	   tableOrder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableOrder.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandler());
	   tableOrder.setEditable(true);
	   
//	   IDCol.setCellFactory(getCustomCellFactory("black","14px","normal","Arial","top_center"));
//	   statusCol.setCellFactory(getCustomCellFactory("black","14px","bold","Arial","center"));
	   
//	   statusCol.setCellFactory(TextFieldTableCell.forTableColumn());
//	   statusCol.setOnEditCommit(event -> (event.getTableView().
//			   getItems().get(event.getTablePosition().getRow())).
//			   setStatus(event.getNewValue()));
   }
   
   public void setReorderTable() {
	   tableReorder = new TableView<>();
	   dataReorder = dummydataReorder();
	   tableReorder.setItems(dataReorder);
	   
//	   TableColumn<Ingredient,String> IDCol = new TableColumn<>("ID");
//	   IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
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
	   tableStock.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandlerStock());
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
	   TableColumn<Order,String> statusCol = new TableColumn("Status");
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
	   
	   
	   FilteredList<Ingredient> filteredData = new FilteredList<Ingredient>(dataInventory, p -> true);

	   filterField.textProperty().addListener((observable, oldValue, newValue) -> {
           filteredData.setPredicate(ing -> {
               if (newValue == null || newValue.isEmpty()) {
                   return true;
               }
               
               String lowerCaseFilter = newValue.toLowerCase();
               
               if (ing.getName().toLowerCase().contains(lowerCaseFilter)) {
                   return true; 
               } else if (ing.getType().toLowerCase().contains(lowerCaseFilter)) {
                   return true;
               } else if (ing.getDateTime().toLowerCase().contains(lowerCaseFilter)) {
                   return true;
               } else if (ing.getAmount().toLowerCase().contains(lowerCaseFilter)) {
                   return true;
               }
               return false;
           });
           tableInventory.setItems(filteredData);
       });
     
   }
   
   public void setEditTable() {
	   tableEdit = new TableView<>();
	   dataEdit = dummydataIngredient();
	   tableEdit.setItems(dataEdit);
	   
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

	   tableEdit.getColumns().setAll(IDCol,nameCol,typeCol,amountCol,unitCol);
	   tableEdit.setPrefWidth(800);
	   tableEdit.setPrefHeight(850);
	   tableEdit.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	   tableEdit.getSelectionModel().selectedIndexProperty().addListener(new RowChangeHandlerEditStock());
	   tableEdit.setEditable(true);
	   
//	   statusCol.setCellFactory(getCustomCellFactory("red","16px","Arial","center"));
	   
//	   newAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
//	   newAmountCol.setOnEditCommit(event -> (event.getTableView().
//			   getItems().get(event.getTablePosition().getRow())).
//			   setNewAmount(event.getNewValue()));
   }
   
//   private Callback<TableColumn<Order, String>, TableCell<Order, String>> getCustomCellFactory(final String color,String fontSize, String fontWeight,String fontFamily,String textAlign) {
//       return new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
//
//           @Override
//           public TableCell<Order, String> call(TableColumn<Order, String> param) {
//               TableCell<Order, String> cell = new TableCell<Order, String>() {
//
//                   @Override
//                   public void updateItem(final String item, boolean empty) {
//                       if (item != null) {
//                           setText(item);
//                           setStyle("-fx-text-fill: " + color + ";\n"+
//                        		   "-fx-font-size: " + fontSize + ";\n"+
//                        		   "-fx-font-family: " + fontFamily + ";\n"+
//                        		   "-fx-alignment: " + textAlign + ";\n"+
//                        		   "-fx-font-weight: " + fontWeight + ";");
////                           setStyle("-fx-font-size: " + fontsize + ";");
////                           setStyle("-fx-font-family: " + fontfamily + ";");
//                       }
//                   }
//               };
//               return cell;
//           }
//       };
//   }
   
   
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
//        ordertxtStatus.setText(pb.toString());
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
   
   private class RowChangeHandlerStock implements ChangeListener {
	      @Override
	      public void changed(ObservableValue ov, Object oldVal, Object newVal) {
	         int val = ((Number)newVal).intValue();
	         if (dataStock.size()<=0) {
//	        	 statusbtn.setVisible(false);
	        	 delbtn.setVisible(false);
	            return;
	         }
	        Ingredient pb= (Ingredient) dataStock.get(val);
//	        ordertxtStatus.setText(pb.toString());
//	        statusbtn.setVisible(true);
	        if(Integer.valueOf(pb.getAmount())==0) {
	        	delbtn.setVisible(true);
	        }
	        else {
	        	delbtn.setVisible(false);
	        }
//	        delbtn.setVisible(true);
	      }
	   }
   
   		private class RowChangeHandlerEditStock implements ChangeListener {
   			@Override
   			public void changed(ObservableValue ov, Object oldVal, Object newVal) {
   				int val = ((Number)newVal).intValue();
   				if (dataEdit.size()<=0) {
   					//	        	 statusbtn.setVisible(false);
//   					delbtn.setVisible(false);
   					return;
   				}
   				Ingredient pb= (Ingredient) dataEdit.get(val);
   				//	        ordertxtStatus.setText(pb.toString());
   				//	        statusbtn.setVisible(true);
//   				if(Integer.valueOf(pb.getAmount())==0) {
//   					delbtn.setVisible(true);
//   				}
//   				else {
//   					delbtn.setVisible(false);
//   				}
   				editNameField.setText(pb.getName());
   				editTypeField.setText(pb.getType());
   				//	        delbtn.setVisible(true);
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
			
//			ordertxtStatus.setText("Deleted: " + ordertxt.toString());

			// Select a row
			if (tableOrder.getItems().size() == 0) {

//				ordertxtStatus.setText("No data in table !");
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

//				reordertxtStatus.setText("No data in table !");
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

   private class StatusButtonListener implements EventHandler<ActionEvent> {

	   @Override
	   public void handle(ActionEvent e) {
		   Order selectedItems = tableOrder.getSelectionModel().getSelectedItems().get(0);
		   selectedItems.setStatus("Produced");
		   String first_Column = selectedItems.toString().split(",")[0].substring(0);
//		   System.out.println(first_Column);
//		   ordertxtStatus.setText(first_Column);
		   
		   ControllerDB db = new ControllerDB();
		   db.updateOrderStatusSQL(dbUser, usrPass,selectedItems);
		   
//		  for(Order o : dummydataOrder("waiting")) {
//			  if(o.getID().equals(selectedItems.getID())) {
//				  System.out.println("============================");
//				  System.out.println(o.getID()+" : "+o.getToppings()+" : "+o.getStatus());
//			  }
//		  } 

		   DeleteButtonListener del = new DeleteButtonListener();
		   del.handle(e);
	   }
   }


   private class ReorderButtonListener implements EventHandler<ActionEvent> {

	   @Override
	   public void handle(ActionEvent e) {
		   Ingredient selectedItems = tableReorder.getSelectionModel().getSelectedItems().get(0);
//		   selectedItems.setStatus("Produced");
		   String first_Column = selectedItems.toString().split(",")[0].substring(0);
//		   System.out.println(first_Column);
//		   ordertxtStatus.setText(first_Column);

//		   System.out.println(selectedItems.getAmount()+", "+selectedItems.getNewAmount());
		   ControllerDB db = new ControllerDB();
//		   db.accessSQL(dbUser, usrPass, "ingredients");
//		   for(Ingredient ing:db.ingredientListDB) {
//			   if(selectedItems.getID().equals(ing.getID())) {
//				   db.updateStockSQL(dbUser, usrPass,new Ingredient(selectedItems.getID(),selectedItems.getName()
//						   ,selectedItems.getType(),selectedItems.getAmount(),selectedItems.getUnit()
//						   ,selectedItems.getNewAmount()));
//			   }
//		   }
		
		   db.updateStockSQL(dbUser, usrPass,selectedItems,selectedItems.getAmount(),selectedItems.getNewAmount());
//		   System.out.println(selectedItems.getAmount()+", "+selectedItems.getNewAmount());

		   DeleteIngredientListener del = new DeleteIngredientListener();
		   del.handle(e);
	   }
   }
   public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
   
   private class AddButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			if(!(nameField.getText().equals(""))&&!(typeField.getText().equals(""))&&!(amountField.getText().equals(""))&&isDouble(amountField.getText())) {
//				String addID = String.valueOf(tableStock.getItems().size()+1);
				Ingredient ing = new Ingredient("New ID", nameField.getText(), typeField.getText(), amountField.getText(), "grams");
				ControllerDB db = new ControllerDB();
					if(!db.checkDuplicate(dbUser, usrPass,ing)) {
						dataStock.add(ing);
						
						int row = dataStock.size() - 1;

						// Select the new row
						tableStock.requestFocus();
						tableStock.getSelectionModel().select(row);
						tableStock.getFocusModel().focus(row);
						addtxtStock.setText("");
						db.saveStockInSQL(dbUser, usrPass,ing);
						nameField.setText("");
						typeField.setText("");
						amountField.setText("");
						return;
					}
			}
			
			String nameValidate = "product name";
	   		String typeValidate = "product type";
	   		String amountValidate = "amount of product";
	   		
	   		if(nameField.getText().equals("")) {
				addtxtStock.setText("Your product "+ nameValidate +" is empty.");
			}
			if(typeField.getText().equals("")) {
				addtxtStock.setText("Your type "+ typeValidate +" empty.");
			}
			if(!(isDouble(amountField.getText()))) {
				addtxtStock.setText("Your "+ amountValidate +" should be number.");
			}
			if(amountField.getText().equals("")) {
				addtxtStock.setText("Your "+ amountValidate +" is empty.");
			}
			
			if(nameField.getText().equals("")&&typeField.getText().equals("")) {
				addtxtStock.setText("Your "+ nameValidate +" and "+ typeValidate +" are empty.");
			}
			if(nameField.getText().equals("")&&amountField.getText().equals("")) {
				addtxtStock.setText("Your "+ nameValidate +" and "+ amountValidate +" are empty.");
			}
			if(typeField.getText().equals("")&&amountField.getText().equals("")) {
				addtxtStock.setText("Your "+ typeValidate +" and "+ amountValidate +" are empty.");
			}
			if(nameField.getText().equals("")&&typeField.getText().equals("")&&amountField.getText().equals("")) {
				addtxtStock.setText("All fields are empty. Please enter your data.");
			}
		}
	}
   
   private class DeleteStockButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			// Get selected row and delete
			int ix = tableStock.getSelectionModel().getSelectedIndex();
			Ingredient stocktxt = (Ingredient) tableStock.getSelectionModel().getSelectedItem();
			if(tableStock.getItems().size()!=0) {
				if(Integer.valueOf(stocktxt.getAmount())==0) {
					dataStock.remove(ix);
					
					ControllerDB db = new ControllerDB();
					db.deleteStock(dbUser, usrPass, stocktxt);
					delbtn.setVisible(false);
				}
			}
			
			addtxtStock.setText("Remove: " + stocktxt.toString());

			// Select a row
			if (tableStock.getItems().size() == 0) {

//				addtxtStock.setText("No data in table !");
				delbtn.setVisible(false);
				return;
			}

			if (ix != 0) {

				ix = ix -1;
			}

			tableStock.requestFocus();
			tableStock.getSelectionModel().select(ix);
			tableStock.getFocusModel().focus(ix);
		}
	}
   
   private class EditButtonListener implements EventHandler<ActionEvent> {

	   @Override
	   public void handle(ActionEvent e) {
		   
		   String editName = editNameField.getText();
		   String editType = editTypeField.getText();
		   
		   Ingredient selectedItems = tableEdit.getSelectionModel().getSelectedItems().get(0);
		   ControllerDB db = new ControllerDB();
		   if(!editName.equals("")&&!editType.equals("")) {
			   db.editStockNameSQL(dbUser, usrPass, selectedItems,editName,editType); 
			   selectedItems.setName(editName);
			   selectedItems.setType(editType);
			   edittxtStock.setText("");
		   }
		   else {
			   if(editName.equals("")) {
				   edittxtStock.setText("Please enter your product name.");
			   }
			   if(editType.equals("")) {
				   edittxtStock.setText("Please enter your type of product.");
			   }
			   if(editName.equals("")&&editType.equals("")) {
				   edittxtStock.setText("Please enter product name and your type of product.");
			   }
			   
		   }
	   }
   }
   
   public static void main(String [] args) {
      Application.launch(args);
   }
}