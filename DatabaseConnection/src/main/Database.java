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
		// SQL variables
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
		    String sql = "CREATE TABLE items (" +
		                 "ID serial PRIMARY KEY," +
		                 "name TEXT NOT NULL," +
		                 "price DOUBLE PRECISION)";
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
		
		// Item adding and modifications
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
		
}
