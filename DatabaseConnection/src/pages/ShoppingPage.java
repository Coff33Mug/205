package pages;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ShoppingPage {

	public JFrame ShoppingPage;
	public JButton ManagerPageButton;

	/**
	 * Launch the application.
	 */
	public void Launch() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShoppingPage window = new ShoppingPage();
					window.ShoppingPage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShoppingPage() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ShoppingPage = new JFrame();
		ShoppingPage.setBounds(100, 100, 1000, 1000);
		ShoppingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ShoppingPage.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Shopping Page");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
		lblNewLabel.setBounds(10, -4, 500, 90);
		ShoppingPage.getContentPane().add(lblNewLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage LP = new LoginPage();
				LP.LoginPage.setVisible(true);
				ShoppingPage.dispose();
			}
		});
		logoutButton.setBounds(885, 11, 89, 23);
		ShoppingPage.getContentPane().add(logoutButton);
		
		ManagerPageButton = new JButton("Manage");
		ManagerPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerPage MP = new ManagerPage();
				MP.ManagerPage.setVisible(true);
				ShoppingPage.dispose();
			}
		});
		ManagerPageButton.setBounds(885, 45, 89, 23);
		ShoppingPage.getContentPane().add(ManagerPageButton);
		
		
		
		
		
	}
}
