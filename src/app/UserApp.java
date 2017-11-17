package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.User;

public class UserApp {
	
public static void main(String[] args) {
	
	Connection conn=null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root", "coderslab");
		User u =new User("szymond", "dubiel.szymon@gmail.com", "onett");
		u.setUserGroupId(1);
		u.save(conn);
		conn.close();
		// User u2 loadbyId
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
}
