package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.Exercise;
import Model.User;

public class UserApp {
	
public static void main(String[] args) {
	
	Connection conn=null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root","coderslab");
		
		User u =new User();
		u.setUsername("Magda");
		u.setEmail("magda.baba@gmail.com");
		u.setPassword("gagagaga");
		u.setUserGroupId(2);
//		u.save(conn);
		
		Exercise.loadAllByUserId(conn, 8);
		User.loadAllByGrupId(conn, 2);
		
		
		conn.close(); 
		
		
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
}