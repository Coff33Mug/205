package pages;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerPage {

	public JFrame ManagerPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerPage window = new ManagerPage();
					window.ManagerPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ManagerPage = new JFrame();
		ManagerPage.setBounds(100, 100, 1000, 1000);
		ManagerPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ManagerPage.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Manager Menu");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNewLabel.setBounds(23, 23, 212, 69);
		ManagerPage.getContentPane().add(lblNewLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage LP = new LoginPage();
				LP.LoginPage.setVisible(true);
				ManagerPage.dispose();
			}
		});
		logoutButton.setBounds(23, 242, 156, 40);
		ManagerPage.getContentPane().add(logoutButton);
		
		JButton ShoppingPageButton = new JButton("Shopping page");
		ShoppingPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShoppingPage SP = new ShoppingPage();
				SP.ShoppingPage.setVisible(true);
				SP.ManagerPageButton.setVisible(true);
				ManagerPage.dispose();
			}
		});
		ShoppingPageButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ShoppingPageButton.setBounds(23, 117, 156, 40);
		ManagerPage.getContentPane().add(ShoppingPageButton);
		
		JButton CreateAccountButton = new JButton("Create account");
		CreateAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerRegisterPage MRP = new ManagerRegisterPage();
				MRP.ManagerRegisterPage.setVisible(true);
				ManagerPage.dispose();
			}
		});
		CreateAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CreateAccountButton.setBounds(23, 179, 156, 40);
		ManagerPage.getContentPane().add(CreateAccountButton);
	}
}
