package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.User;

public class UserApp {
	
public static void main(String[] args) {
	
	Connection conn=null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root","coderslab");
//		User u =new User();
//		u.setUsername("Ola");
//		u.setEmail("ola.stryczniewiczz@gmail.com");
//		u.setPassword("gagagaga");
//		u.setUserGroupId(1);
//		u.save(conn);
		User user1 = User.loadUserById(conn, 8);
		System.out.println(user1.getUsername() + " " + user1.getEmail() + " " + user1.getPassword() );
		
		user1.setUsername("joannastr");
		user1.setEmail("joannastr@gmail.com");
		user1.setPassword("askakolaska");
		
		user1.save(conn);
		
		

		conn.close();
		// User u2 loadbyId
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
}
