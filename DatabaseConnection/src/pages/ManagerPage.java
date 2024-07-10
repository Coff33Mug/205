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
		ManagerPage.setBounds(100, 100, 300, 500);
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
		logoutButton.setBounds(23, 360, 156, 40);
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
		
		JButton modifyItemsButton = new JButton("Modify items");
		modifyItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpItemPage EP = new EmpItemPage();
				EP.EmpItemPage.setVisible(true);
				ManagerPage.dispose();
			}
		});
		modifyItemsButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		modifyItemsButton.setBounds(23, 240, 156, 40);
		ManagerPage.getContentPane().add(modifyItemsButton);
		
		JButton pendingOrdersButton = new JButton("Pending Orders");
		pendingOrdersButton.setBounds(23, 298, 156, 40);
        pendingOrdersButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        ManagerPage.getContentPane().add(pendingOrdersButton);
        pendingOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PendingOrdersPage POP = new PendingOrdersPage();
                POP.PendingOrderPage.setVisible(true);
                ManagerPage.dispose();
            }
        });
	}
}
