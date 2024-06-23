
public class Driver {

	public static void main(String[] args) {
		Database usersdb = new Database();
		LoginPage LP = new LoginPage();
		usersdb.Connect("jdbc:postgresql://localhost:5432/usersdb", "postgres", "password");
		
	}

}
