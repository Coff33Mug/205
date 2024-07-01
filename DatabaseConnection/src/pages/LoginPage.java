package pages;
import java.awt.EventQueue;
import main.Database;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.im.InputContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class LoginPage {

	public JFrame LoginPage;
	public JLabel AccountCreationLabel;
	private int userID;
	private JTextField userInput;
	private JTextField passInput;

	/**
	 * Launch the application.
	 */
	public void Launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage window = new LoginPage();
					window.LoginPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPage() {
		initialize();
	}
	
	public int getID() {
		return userID;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LoginPage = new JFrame();
		LoginPage.setBounds(100, 100, 1000, 1000);
		LoginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LoginPage.getContentPane().setLayout(null);
		
		// User inputs
		userInput = new JTextField();
		userInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		userInput.setBounds(21, 63, 493, 45);
		LoginPage.getContentPane().add(userInput);
		userInput.setColumns(10);
				
		
				
		passInput = new JTextField();
		passInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passInput.setColumns(10);
		passInput.setBounds(21, 169, 493, 45);
		LoginPage.getContentPane().add(passInput);
		
		AccountCreationLabel = new JLabel();
		AccountCreationLabel.setBounds(158, 363, 670, 106);
		AccountCreationLabel.setForeground(SystemColor.menu);
		AccountCreationLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		LoginPage.getContentPane().add(AccountCreationLabel);
		
				
		
		
		JLabel IncorrectLabel = new JLabel("Username or password is incorrect");
		IncorrectLabel.setForeground(SystemColor.menu);
		IncorrectLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		IncorrectLabel.setBounds(66, 225, 417, 41);
		LoginPage.getContentPane().add(IncorrectLabel);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton.addActionListener(new ActionListener() {
			
			// Checks database for information of login
			public void actionPerformed(ActionEvent e) {
				Database db = new Database();
				db.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
				IncorrectLabel.setForeground(SystemColor.menu);
				String inputUsername = userInput.getText();
				String inputPassword = passInput.getText();
				
				try {
					Statement stmt = db.getConnection().createStatement();
					ResultSet rs = stmt.executeQuery("select id, username, password, isemployee, ismanager from public.users;");
					
					while (rs.next()) {
						String username = rs.getString("username");
						String password = rs.getString("password");
						boolean isEmployee = rs.getBoolean("isemployee");
						boolean isManager = rs.getBoolean("isManager");
						
						
						if (username.equals(inputUsername) && password.equals(inputPassword)) {	
							
							if (isManager == true) {
								userID = rs.getInt("ID");
								ManagerPage MP = new ManagerPage();
								MP.ManagerPage.setVisible(true);
								LoginPage.dispose();
							} else {
								userID = rs.getInt("ID");
								ShoppingPage SP = new ShoppingPage();
								SP.ShoppingPage.setVisible(true);
								SP.ManagerPageButton.setVisible(false);
								LoginPage.dispose();
								break;
							}
							
							
						}
					}
					
					IncorrectLabel.setForeground(Color.RED);
					stmt.close();
					db.closeConnection();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		btnNewButton.setBounds(21, 273, 184, 58);
		LoginPage.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPage RP = new RegisterPage();
				RP.RegisterPage.setVisible(true);
				LoginPage.dispose();
			}
		});
		btnNewButton_1.setBounds(330, 273, 184, 58);
		LoginPage.getContentPane().add(btnNewButton_1);
		
		
		
		
		// Labels for user input
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblNewLabel.setBounds(21, 24, 184, 28);
		LoginPage.getContentPane().add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblPassword.setBounds(21, 130, 184, 28);
		LoginPage.getContentPane().add(lblPassword);
		
		
		
	}
}
