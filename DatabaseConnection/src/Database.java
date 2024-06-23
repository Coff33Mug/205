import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database {
	
		// Objects and variables
		// SQL variables
		private Connection c = null;
		
		public void Connect(String url, String user, String pass) {
			try {
				// ForName seems to throw an error when driver you're trying to access doesn't exist
				Class.forName("org.postgresql.Driver");
				c = DriverManager.getConnection(url, user, pass);
				System.out.print("Connected to database");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		
		}
		
		public Connection getConnection() {
			return c;
		}
		
		/*try {
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from usersdb;");
			rs.next();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}*/
}
