package pages;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class RegisterPage {

	public JFrame RegisterPage;

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
		RegisterPage.setBounds(100, 100, 1000, 1000);
		RegisterPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
