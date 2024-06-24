package pages;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShoppingPage {

	public JFrame ShoppingPage;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShoppingPage window = new ShoppingPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

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
		lblNewLabel.setBounds(64, 43, 500, 90);
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
	}
}
