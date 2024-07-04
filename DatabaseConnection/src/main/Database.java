package main;
import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import pages.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
		// Objects and variables
		private int userID;
		// SQL variables
		private static Database instance;
		private Connection c = null;
		
		public void Connect(String url, String user, String pass) {
			try {
				// ForName seems to throw an error when driver you're trying to access doesn't exist
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection(url, user, pass);
				System.out.println("Connected to database");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		
		}
		
		// Makes sure that only one database instance is created and referenced.
		public static synchronized Database getInstance() {
			if (instance == null) {
				instance = new Database();
			} 
			return instance;
		}
		
		
		
		// For all add methods, deleted IDs do not exist anymore in database.
		public void addAdmin() throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		    Statement stmt = c.createStatement();
		    String sql = "INSERT INTO public.users(id, username, password, firstname, lastname, address, isemployee, ismanager) "
		            + "VALUES(default, 'admin', 'admin', 'admin', 'admin', 'n/a', false, true)";
		    stmt.executeUpdate(sql);
		    stmt.close();
		    c.close();
		}
		
		public void addCustomer(String username, String password, String firstname, String lastname, String address) throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			PreparedStatement stmt = c.prepareStatement(
			"INSERT INTO public.users(id, username, password, firstname, lastname, address, isemployee, ismanager) VALUES(DEFAULT,?,?,?,?,?, false, false)");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstname);
			stmt.setString(4, lastname);
			stmt.setString(5, address);
			stmt.executeUpdate();
			stmt.close();
			c.close();
		}
		
		public void addEmployee(String username, String password, String firstname, String lastname) throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			PreparedStatement stmt = c.prepareStatement(
					"INSERT INTO public.users(id, username, password, firstname, lastname, address, isemployee, ismanager) VALUES(DEFAULT,?,?,?,?, 'n/a', true, false)");
					stmt.setString(1, username);
					stmt.setString(2, password);
					stmt.setString(3, firstname);
					stmt.setString(4, lastname);
					stmt.executeUpdate();
					stmt.close();
					c.close();
		}
		
		public void createTable() throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			Statement stmt = c.createStatement();
		    String sql = "CREATE TABLE checkout (" +
		                 "orderID serial PRIMARY KEY," +
		                 "userID int not null," +
		                 "itemname TEXT NOT NULL," +
		                 "quantity int not null," +
		                 "price DOUBLE PRECISION," + 
		                 "orderfilled boolean)";
		    
		    stmt.executeUpdate(sql);
		    stmt.close();
		    c.close();
		}
		
		public Connection getConnection() {
			return c;
		}
		
		public void closeConnection() throws SQLException {
			c.close();
		}
		
		public void launch() {
			LoginPage LP = new LoginPage();
			LP.Launch();
		}
		
		// Get and set UserID
		public int getUserID() {
			return userID;
		}
		
		public void setUserID(int inputUserID) {
			userID = inputUserID;
		}
		
		// Item adding and modifications to items database
		public void addItem(String InputName, double InputPrice) throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			PreparedStatement stmt = c.prepareStatement(
					"INSERT INTO public.items(id, name, price) VALUES(DEFAULT,?,?)");
					stmt.setString(1, InputName);
					stmt.setDouble(2, InputPrice);
					stmt.executeUpdate();
					System.out.print("Added item");
					stmt.close();
					c.close();
		}
		
		
		
		public void deleteItem(String InputName, int InputID) throws SQLException {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		    Statement stmt = getConnection().createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT id, name FROM public.items;");

		    while (rs.next()) {
		        int DatabaseID = rs.getInt("id");
		        String Name = rs.getString("name");
		        
		        if (DatabaseID == InputID || InputName.toLowerCase().equals(Name)) {
		            PreparedStatement prestmt = getConnection().prepareStatement("DELETE FROM public.items WHERE id =? OR name =?");
		            prestmt.setInt(1, InputID);
		            prestmt.setString(2, InputName);
		            prestmt.executeUpdate();
		            System.out.print("Item Deleted");
		            break;
		        } else {
		            System.out.print("Could not find item");
		        }
		    }
		    stmt.close();
		    getConnection().close();
		}
		
		public double getItemPrice(String InputName, int InputID) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select id, name, price from public.items;");
				
				while (rs.next()) {
					int DatabaseID = rs.getInt("id");
					double Price = rs.getDouble("price");
					String Name = rs.getString("name");
					
					if (InputName.toLowerCase().equals(Name) || InputID == DatabaseID) {
						stmt.close();
					    c.close();
						return Price;
					}
				}
				
				
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
				return -1;
			}
			
			return -1;
		}
		
		public void updateItemPrice(String InputName, int InputID, double InputPrice) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select id, name, price from public.items;");
				
				while (rs.next()) {
					int DatabaseID = rs.getInt("id");
					double Price = rs.getDouble("price");
					String Name = rs.getString("name");
					
					if (InputName.toLowerCase().equals(Name) || InputID == DatabaseID) {
						PreparedStatement prestmt = getConnection().prepareStatement("UPDATE public.items SET price =? WHERE id =? OR name =?");
						prestmt.setDouble(1, InputPrice);
						prestmt.setInt(2, DatabaseID);
						prestmt.setString(3, Name);
						prestmt.executeUpdate();
						System.out.print("item price updated");
						break;
					}
				}
				
				
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
			}
		}
		
		
		public String getItemName(int InputID) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select id, name from public.items;");
				
				while (rs.next()) {
					int DatabaseID = rs.getInt("id");
					String name = rs.getString("name");
					if (InputID == DatabaseID) {
						stmt.close();
					    c.close();
						return name;
					}
				}
				
				
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
				return "";
			}
			
			return "";
		}
		
		public int getItemID(String InputName) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select id, name from public.items;");
				
				while (rs.next()) {
					int DatabaseID = rs.getInt("id");
					String name = rs.getString("name");
					if (InputName.toLowerCase().equals(name)) {
						stmt.close();
					    c.close();
						return DatabaseID;
					}
				}
				
				
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
				return -1;
			}
			
			return -1;
		}
		
		// Checkout item modifications
		public void checkoutAddItem(String inputItemName, int inputQuantity, int inputItemID) {
			
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
				Statement stmt = getConnection().createStatement();
				ResultSet rs = stmt.executeQuery("select id, name, price from public.items;");
				
				while (rs.next()) {
					int itemID = rs.getInt("id");
					String name = rs.getString("name");
					double price = rs.getDouble("price");
					
					if (inputItemName.toLowerCase().equals(name) || inputItemID == itemID) {
						PreparedStatement prestmt = c.prepareStatement(
								"INSERT INTO public.checkout(orderid, userid, itemname, quantity, price, orderfilled) VALUES(DEFAULT,?,?,?,?, false)");
						prestmt.setInt(1, userID);
						prestmt.setString(2, name);
						prestmt.setInt(3, inputQuantity);
						prestmt.setDouble(4, price * inputQuantity);
						prestmt.executeUpdate();
						System.out.print("Added item");
						stmt.close();
						c.close();
						break;
					}
				}
				
				
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
				
			}
		}
		
		public void checkoutDeleteItem(int inputUserID, int inputOrderID) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
		    Statement stmt = getConnection().createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT orderid, userid FROM public.checkout;");

		    while (rs.next()) {
		        int databaseOrderID = rs.getInt("orderid");
		        int databaseUserID = rs.getInt("userid");
		        
		        if (inputUserID == databaseUserID || databaseOrderID == inputOrderID) {
		            PreparedStatement prestmt = getConnection().prepareStatement("DELETE FROM public.checkout WHERE orderid =? OR userid =?");
		            prestmt.setInt(1, databaseOrderID);
		            prestmt.setInt(2, databaseUserID);
		            prestmt.executeUpdate();
		            System.out.print("Item Deleted");
		            break;
		        	}
		    	}
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
			}
			
		}
		
		public void checkoutUpdateItemQuantity(int inputUserID, int inputOrderID, int inputQuantity) {
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
		    Statement stmt = getConnection().createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT orderid, userid, itemname FROM public.checkout;");

		    while (rs.next()) {
		        int databaseOrderID = rs.getInt("orderid");
		        int databaseUserID = rs.getInt("userid");
		        String databaseItemName = rs.getString("itemname");
		        
		        if (inputUserID == databaseUserID || databaseOrderID == inputOrderID) {
		            PreparedStatement prestmt = getConnection().prepareStatement("UPDATE public.checkout SET quantity =?, price =? WHERE orderid =? OR userid =?");
		            prestmt.setInt(1, inputQuantity);
		            // Second parameter of getItemPrice is 0 because databaseItemName should be correct
		            // making another ID unnecessary
		            prestmt.setDouble(2, inputQuantity * getItemPrice(databaseItemName, 0));
		            prestmt.setInt(3, databaseOrderID);
		            prestmt.setInt(4, databaseUserID);
		            prestmt.executeUpdate();
		            System.out.print("Item quantity updated");
		            break;
		        	}
		    	}
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
			}
		}
		
		public int getCheckoutCount(int inputUserID) {
			int count = 0;
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
		    Statement stmt = getConnection().createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT userid FROM public.checkout;");

		    while (rs.next()) {
		        int databaseUserID = rs.getInt("userid");
		        
		        if (inputUserID == databaseUserID) {
		            	count++;
		        	}
		    	}
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
			}
			return count;
		}
		
		public double getCheckoutTotalPrice(int inputUserID) {
			double totalPrice = 0;
			Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
			try {
		    Statement stmt = getConnection().createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT userid, price FROM public.checkout;");

		    while (rs.next()) {
		        int databaseUserID = rs.getInt("userid");
		        double databasePrice = rs.getDouble("price");
		        if (inputUserID == databaseUserID) {
		            	totalPrice += databasePrice;
		        	}
		    	}
			} catch (SQLException e1) {
				System.out.print("item not found");
				e1.printStackTrace();
			}
			return totalPrice;
		}
}
