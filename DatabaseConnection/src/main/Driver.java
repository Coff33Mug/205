package main;
import pages.LoginPage;
public class Driver {

	public static void main(String[] args) {
		Database DB = new Database();
		LoginPage LP = new LoginPage();
		LP.Launch();
		
	}

}
