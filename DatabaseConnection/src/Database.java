import java.sql.Statement;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Database {
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		JFrame frame = new JFrame("Test");
		JPanel panel = new JPanel();
		
		// Add the panel to the frame
        frame.add(panel);
        
        // Set the size of the frame
        frame.setSize(600, 600);
        
        // Make the frame visible
        frame.setVisible(true);
        
        // Exit the application when the frame is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			// ForName seems to throw an error when driver you're trying to access doesn't exist
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/usersdb", "postgres", "password");
			System.out.print("Connected to database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
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
}
