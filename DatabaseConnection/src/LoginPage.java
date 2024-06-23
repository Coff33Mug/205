import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoginPage extends JFrame{
	// Window objects
	public LoginPage() {
		JFrame frame = new JFrame("Test");
		JPanel panel = new JPanel();
			
		// Window properties
		frame.add(panel);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
