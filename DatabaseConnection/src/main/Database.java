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
		    String sql = "CREATE TABLE users (" +
		                 "ID serial PRIMARY KEY," +
		                 "username TEXT NOT NULL," +
		                 "password TEXT NOT NULL," +
		                 "firstname TEXT NOT NULL," +
		                 "lastname TEXT NOT NULL," +
		                 "address TEXT NOT NULL," +
		                 "isemployee boolean default false," +
		                 "ismanager boolean default false)";
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
		
}
