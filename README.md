# Cutomised_Pizza_JavaFX
This project is a project which aim to create a production line application that will be able to change status of orders, tracking total amount of inredients in stock, reorder ingredients, and view history of ingredient inventory.

## Function Details
##### Core functions
* Change orders status (waiting to produced)
* Display waiting and produced order lists
* Reorder ingredients which have threadhold lower than 500
* Fliter search inventory history by name, type, amount and date and time of ingredients that were reordered

##### Extra functions
* Allow to edit name and type of ingredients
* Allow to delete ingredients which have threadhold equal to 0
* Allow to add a new ingredient in the stock with automatic updated in the inventory history

## Database
Tables
```
+-------------------+
| Tables_in_pizzadb |
+-------------------+
| customers         |
| ingredients       |
| inventory         |
| members           |
| orders            |
| staff             |
+-------------------+
```

Customers table
```
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | int(11)      | NO   | PRI | NULL    | auto_increment |
| name     | varchar(255) | YES  |     | NULL    |                |
| address  | varchar(255) | YES  |     | NULL    |                |
| memberId | int(11)      | YES  |     | NULL    |                |
+----------+--------------+------+-----+---------+----------------+
```

Ingredients table
```
+--------+--------------+------+-----+---------+----------------+
| Field  | Type         | Null | Key | Default | Extra          |
+--------+--------------+------+-----+---------+----------------+
| id     | int(11)      | NO   | PRI | NULL    | auto_increment |
| name   | varchar(255) | YES  |     | NULL    |                |
| type   | varchar(255) | YES  |     | NULL    |                |
| amount | double       | YES  |     | NULL    |                |
| unit   | varchar(255) | YES  |     | NULL    |                |
+--------+--------------+------+-----+---------+----------------+
```

Inventory table
```
+-----------+--------------+------+-----+---------+----------------+
| Field     | Type         | Null | Key | Default | Extra          |
+-----------+--------------+------+-----+---------+----------------+
| id        | int(11)      | NO   | PRI | NULL    | auto_increment |
| name      | varchar(255) | YES  |     | NULL    |                |
| type      | varchar(255) | YES  |     | NULL    |                |
| amount    | double       | YES  |     | NULL    |                |
| unit      | varchar(255) | YES  |     | NULL    |                |
| date_time | datetime     | YES  |     | NULL    |                |
+-----------+--------------+------+-----+---------+----------------+
```

Members table
```
+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| username   | varchar(255) | YES  |     | NULL    |                |
| password   | varchar(255) | YES  |     | NULL    |                |
| email      | varchar(255) | YES  |     | NULL    |                |
| customerId | int(11)      | YES  |     | NULL    |                |
+------------+--------------+------+-----+---------+----------------+
```

Orders table
```
+------------+---------------+------+-----+---------+----------------+
| Field      | Type          | Null | Key | Default | Extra          |
+------------+---------------+------+-----+---------+----------------+
| id         | int(11)       | NO   | PRI | NULL    | auto_increment |
| base       | varchar(255)  | YES  |     | NULL    |                |
| toppings   | varchar(4000) | YES  |     | NULL    |                |
| status     | varchar(255)  | YES  |     | NULL    |                |
| customerId | int(11)       | YES  |     | NULL    |                |
+------------+---------------+------+-----+---------+----------------+
```

Staff table
```
+----------+--------------+------+-----+---------+----------------+
| Field    | Type         | Null | Key | Default | Extra          |
+----------+--------------+------+-----+---------+----------------+
| id       | int(11)      | NO   | PRI | NULL    | auto_increment |
| name     | varchar(255) | YES  |     | NULL    |                |
| lastname | varchar(255) | YES  |     | NULL    |                |
| username | varchar(255) | YES  |     | NULL    |                |
| password | varchar(255) | YES  |     | NULL    |                |
| role     | varchar(255) | NO   |     | NULL    |                |
+----------+--------------+------+-----+---------+----------------+
```
