package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Model.Group;

public class GroupsApp {
	
public static void main(String[] args) {
	
	Connection conn=null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root","coderslab");
//		Group g =new Group();
//		g.setName("Grupa 7");
//		g.save(conn);

		
		Group group1 = Group.loadGroupById(conn, 4);
		System.out.println(group1.getId() + " | " + group1.getName() + " |");

		List<Group> list = Group.loadAllGroup(conn);
		
		for (Group g: list) {
			System.out.println(g.getId());
			System.out.println(g.getName());
		}

		group1.deleteGroup(conn);
		
		conn.close();
		// User u2 loadbyId
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}
}
