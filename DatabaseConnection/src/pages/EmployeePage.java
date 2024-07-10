package pages;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EmployeePage {

	public JFrame employeePage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeePage window = new EmployeePage();
					window.employeePage.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeePage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		employeePage = new JFrame();
		employeePage.setBounds(100, 100, 300, 450);
		employeePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		employeePage.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee Menu");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNewLabel.setBounds(23, 23, 251, 69);
		employeePage.getContentPane().add(lblNewLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginPage LP = new LoginPage();
				LP.LoginPage.setVisible(true);
				employeePage.dispose();
			}
		});
		logoutButton.setBounds(23, 303, 156, 40);
		employeePage.getContentPane().add(logoutButton);
		
		JButton ShoppingPageButton = new JButton("Shopping page");
		ShoppingPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShoppingPage SP = new ShoppingPage();
				SP.ShoppingPage.setVisible(true);
				SP.ManagerPageButton.setVisible(true);
				employeePage.dispose();
			}
		});
		ShoppingPageButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		ShoppingPageButton.setBounds(23, 117, 156, 40);
		employeePage.getContentPane().add(ShoppingPageButton);
		
		JButton modifyItemsButton = new JButton("Modify items");
		modifyItemsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmpItemPage EP = new EmpItemPage();
				EP.EmpItemPage.setVisible(true);
				employeePage.dispose();
			}
		});
		modifyItemsButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		modifyItemsButton.setBounds(23, 179, 156, 40);
		employeePage.getContentPane().add(modifyItemsButton);
		
		JButton pendingOrdersButton = new JButton("Pending Orders");
		pendingOrdersButton.setBounds(23, 241, 156, 40);
        pendingOrdersButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
        employeePage.getContentPane().add(pendingOrdersButton);
        pendingOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PendingOrdersPage POP = new PendingOrdersPage();
                POP.PendingOrderPage.setVisible(true);
                employeePage.dispose();
            }
        });
	}
}
