package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import Model.Solution;

public class SolutionApp {
	
	public static void main(String[] args) {
		
		Connection conn=null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/warsztat2?useSSL=false", "root","coderslab");
//			Solution solution =new Solution("stworzyc petle, ktora wyswietla poszczegolne wyrazy",1,7);
//			solution.save(conn);
			
			Solution solution5 = new Solution();
			solution5 = Solution.loadSolutionById(conn, 5);
			System.out.println(solution5.getId() + " | " + solution5.getDescription() + " |");

//			List<Solution> list = Solution.loadAllSolution(conn);
//			
//			for (Solution s: list) {
//				System.out.println(s.getId());
//				System.out.println(s.getDescription()); 
//			}

			Solution.loadAllByExerciseId(conn, 1);
			
			conn.close();
			// User u2 loadbyId
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	}