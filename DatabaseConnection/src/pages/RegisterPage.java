package pages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.Database;

import java.awt.SystemColor;

public class RegisterPage {

	public JFrame RegisterPage;
	private JTextField UsernameInput;
	private JTextField FirstNameInput;
	private JTextField PasswordInput;
	private JTextField LastNameInput;
	private JTextField AddressInput;
	Database DB = Database.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterPage window = new RegisterPage();
					window.RegisterPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RegisterPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		RegisterPage = new JFrame();
		RegisterPage.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 18));
		RegisterPage.setBounds(100, 100, 1000, 1000);
		RegisterPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RegisterPage.getContentPane().setLayout(null);
		
		JButton BackButton = new JButton("Back");
		// Brings user back to login page
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage LP = new LoginPage();
				LP.LoginPage.setVisible(true);
				RegisterPage.dispose();
			}
		});
		RegisterPage.getContentPane().setLayout(null);
		
		BackButton.setBounds(885, 11, 89, 23);
		RegisterPage.getContentPane().add(BackButton);
		
		JLabel UsernameText = new JLabel("Username");
		UsernameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		UsernameText.setBounds(23, 11, 131, 42);
		RegisterPage.getContentPane().add(UsernameText);
		
		JLabel PasswordText = new JLabel("Password");
		PasswordText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		PasswordText.setBounds(23, 131, 131, 42);
		RegisterPage.getContentPane().add(PasswordText);
		
		JLabel FirstNameText = new JLabel("First Name");
		FirstNameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		FirstNameText.setBounds(429, 11, 131, 42);
		RegisterPage.getContentPane().add(FirstNameText);
		
		JLabel LastNameText = new JLabel("Last Name");
		LastNameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		LastNameText.setBounds(429, 131, 131, 42);
		RegisterPage.getContentPane().add(LastNameText);
		
		UsernameInput = new JTextField();
		UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UsernameInput.setBounds(23, 58, 303, 62);
		RegisterPage.getContentPane().add(UsernameInput);
		UsernameInput.setColumns(10);
		
		FirstNameInput = new JTextField();
		FirstNameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		FirstNameInput.setColumns(10);
		FirstNameInput.setBounds(429, 58, 303, 62);
		RegisterPage.getContentPane().add(FirstNameInput);
		
		PasswordInput = new JTextField();
		PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PasswordInput.setColumns(10);
		PasswordInput.setBounds(23, 184, 303, 62);
		RegisterPage.getContentPane().add(PasswordInput);
		
		LastNameInput = new JTextField();
		LastNameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		LastNameInput.setColumns(10);
		LastNameInput.setBounds(429, 184, 303, 62);
		RegisterPage.getContentPane().add(LastNameInput);
		
		JLabel AddressText = new JLabel("Address");
		AddressText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		AddressText.setBounds(23, 257, 131, 42);
		RegisterPage.getContentPane().add(AddressText);
		
		AddressInput = new JTextField();
		AddressInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AddressInput.setColumns(10);
		AddressInput.setBounds(23, 307, 303, 62);
		RegisterPage.getContentPane().add(AddressInput);
		
		JLabel AccountCreationLabel = new JLabel("Account creation error.");
		AccountCreationLabel.setForeground(SystemColor.menu);
		AccountCreationLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		AccountCreationLabel.setBounds(138, 461, 509, 50);
		RegisterPage.getContentPane().add(AccountCreationLabel);
		
		JLabel PasswordWarningLabel = new JLabel("Password must be longer than 3 characters");
		PasswordWarningLabel.setForeground(SystemColor.menu);
		PasswordWarningLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		PasswordWarningLabel.setBounds(138, 400, 509, 50);
		RegisterPage.getContentPane().add(PasswordWarningLabel);
		
		// Button that creates customer accounts
		JButton ConfirmButton = new JButton("Confirm");
		ConfirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PasswordInput.getText().length() > 3) {
					
					try {
						// Adds customer and notifies them that account was made.
						DB.addCustomer(UsernameInput.getText(), PasswordInput.getText(), FirstNameInput.getText(), LastNameInput.getText(), AddressInput.getText());
						
						// Moves to login page
						LoginPage LP = new LoginPage();
						LP.LoginPage.setVisible(true);
						LP.AccountCreationLabel.setText("Account created succesfully, please login with the same credentials.");
				        LP.AccountCreationLabel.setForeground(Color.GREEN);
						RegisterPage.dispose();
					} catch (SQLException e1) {
						// Notifies customer that account creation error.
						AccountCreationLabel.setForeground(Color.LIGHT_GRAY);
						e1.printStackTrace();
					}
				} else {
					// Notifies customer password was not of correct length
					PasswordWarningLabel.setForeground(Color.red);
				}
			}
		});
		
		
		
		ConfirmButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		ConfirmButton.setBounds(429, 307, 303, 62);
		RegisterPage.getContentPane().add(ConfirmButton);
		
		
		
		
		
		
		
	}
}
