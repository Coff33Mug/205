package main;
import java.sql.SQLException;

import pages.LoginPage;
public class Driver {

	public static void main(String[] args) {
		Database DB = new Database();
		LoginPage LP = new LoginPage();
		
		/*try {
			DB.addCustomer("bob", "password", "bob", "test", "123 Backalley St");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		LP.Launch();
	}

}
