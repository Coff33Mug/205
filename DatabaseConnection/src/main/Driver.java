package main;
import java.sql.SQLException;

import pages.LoginPage;
public class Driver {

	public static void main(String[] args) {
		Database DB = new Database();
		LoginPage LP = new LoginPage();
		//DB.Connect("jdbc:postgresql://localhost:5432/shopping", "postgres", "password");
		LP.Launch();
	}

}
