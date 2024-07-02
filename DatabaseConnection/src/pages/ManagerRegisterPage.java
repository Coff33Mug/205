package pages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class ManagerRegisterPage {

	private JTextField UsernameInput;
	private JTextField FirstNameInput;
	private JTextField PasswordInput;
	private JTextField LastNameInput;
	private JTextField AddressInput;
	
	public JFrame ManagerRegisterPage;
	Database DB = Database.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerRegisterPage window = new ManagerRegisterPage();
					window.ManagerRegisterPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerRegisterPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ManagerRegisterPage = new JFrame();
		ManagerRegisterPage.setBounds(100, 100, 1000, 1000);
		ManagerRegisterPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton BackButton = new JButton("Back");
		BackButton.setBounds(885, 11, 89, 23);
		// Brings user back to login page
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerPage MP = new ManagerPage();
				MP.ManagerPage.setVisible(true);
				ManagerRegisterPage.dispose();
			}
		});
		ManagerRegisterPage.getContentPane().setLayout(null);
		ManagerRegisterPage.getContentPane().setLayout(null);
		ManagerRegisterPage.getContentPane().add(BackButton);
		
		JLabel UsernameText = new JLabel("Username");
		UsernameText.setBounds(23, 11, 131, 42);
		UsernameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		ManagerRegisterPage.getContentPane().add(UsernameText);
		
		JLabel PasswordText = new JLabel("Password");
		PasswordText.setBounds(23, 131, 131, 42);
		PasswordText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		ManagerRegisterPage.getContentPane().add(PasswordText);
		
		JLabel FirstNameText = new JLabel("First Name");
		FirstNameText.setBounds(429, 11, 131, 42);
		FirstNameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		ManagerRegisterPage.getContentPane().add(FirstNameText);
		
		JLabel LastNameText = new JLabel("Last Name");
		LastNameText.setBounds(429, 131, 131, 42);
		LastNameText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		ManagerRegisterPage.getContentPane().add(LastNameText);
		
		UsernameInput = new JTextField();
		UsernameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UsernameInput.setBounds(23, 58, 303, 62);
		ManagerRegisterPage.getContentPane().add(UsernameInput);
		UsernameInput.setColumns(10);
		
		FirstNameInput = new JTextField();
		FirstNameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		FirstNameInput.setColumns(10);
		FirstNameInput.setBounds(429, 58, 303, 62);
		ManagerRegisterPage.getContentPane().add(FirstNameInput);
		
		PasswordInput = new JTextField();
		PasswordInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PasswordInput.setColumns(10);
		PasswordInput.setBounds(23, 184, 303, 62);
		ManagerRegisterPage.getContentPane().add(PasswordInput);
		
		LastNameInput = new JTextField();
		LastNameInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		LastNameInput.setColumns(10);
		LastNameInput.setBounds(429, 184, 303, 62);
		ManagerRegisterPage.getContentPane().add(LastNameInput);
		
		JLabel AddressText = new JLabel("Address");
		AddressText.setBounds(23, 257, 131, 42);
		AddressText.setFont(new Font("Tahoma", Font.PLAIN, 27));
		ManagerRegisterPage.getContentPane().add(AddressText);
		
		AddressInput = new JTextField();
		AddressInput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AddressInput.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				AddressInput.setText("");
			}
		});
		AddressInput.setForeground(Color.GRAY);
		AddressInput.setText("Address not required for employees");
		AddressInput.setColumns(10);
		AddressInput.setBounds(23, 307, 303, 62);
		ManagerRegisterPage.getContentPane().add(AddressInput);
		
		JLabel AccountCreationLabel = new JLabel("Account creation error.");
		AccountCreationLabel.setBounds(138, 461, 509, 50);
		AccountCreationLabel.setForeground(SystemColor.menu);
		AccountCreationLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		ManagerRegisterPage.getContentPane().add(AccountCreationLabel);
		
		JLabel PasswordWarningLabel = new JLabel("Password must be longer than 3 characters");
		PasswordWarningLabel.setBounds(138, 400, 509, 50);
		PasswordWarningLabel.setForeground(SystemColor.menu);
		PasswordWarningLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		ManagerRegisterPage.getContentPane().add(PasswordWarningLabel);
		
		// Button that creates customer accounts
		JButton CreateCustomerButton = new JButton("Create Customer");
		CreateCustomerButton.setBounds(429, 294, 303, 35);
		CreateCustomerButton.addActionListener(new ActionListener() {
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
						ManagerRegisterPage.dispose();
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
		
		
		
		CreateCustomerButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
		ManagerRegisterPage.getContentPane().add(CreateCustomerButton);
		
		JButton CreateEmployeeAccount = new JButton("Create Employee");
		CreateEmployeeAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PasswordInput.getText().length() > 3) {
					Database DP = new Database();
					try {
						// Adds customer and notifies them that account was made.
						DP.addEmployee(UsernameInput.getText(), PasswordInput.getText(), FirstNameInput.getText(), LastNameInput.getText());
						
						// Moves to login page
						LoginPage LP = new LoginPage();
						LP.LoginPage.setVisible(true);
						LP.AccountCreationLabel.setText("Account created succesfully, please login with the same credentials.");
				        LP.AccountCreationLabel.setForeground(Color.GREEN);
						ManagerRegisterPage.dispose();
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
		CreateEmployeeAccount.setFont(new Font("Tahoma", Font.PLAIN, 21));
		CreateEmployeeAccount.setBounds(429, 348, 303, 35);
		ManagerRegisterPage.getContentPane().add(CreateEmployeeAccount);
		
		
		
		
		
		
		
	}
	}


