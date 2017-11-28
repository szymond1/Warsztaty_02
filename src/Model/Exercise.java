package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exercise {

	private int id = 0;
	private String title;
	private String description;

	public Exercise() {
		this.id = 0;
		this.title = title;
		this.description = description;
	}

	public Exercise(String title, String description) {
		this.id = 0;
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO excercises(title, description) VALUES (?,?);";

			String[] generatedColumns = { "ID" }; // kolumna jest AUTOINCREMENT, wiec tworzymy tabele, która generuje ID
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.title);
			ps.setString(2, this.description);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys(); // baza zwraca wynik
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			ps.close();
			rs.close();

		} else {
			String sql = "UPDATE excercises SET title=?, description=? " + "WHERE id=?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, this.title);
			ps1.setString(2, this.description);
			ps1.setInt(3, this.id);
			ps1.executeUpdate();
			ps1.close();
		}
	}

	public static Exercise loadExerciseById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM excercises WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Exercise loadedExcercise = new Exercise();
			loadedExcercise.id = rs.getInt("id");
			loadedExcercise.title = rs.getString("title");
			loadedExcercise.description = rs.getString("description");

			return loadedExcercise;
		}
		return null;
	}

	public static List<Exercise> loadAllExcercise(Connection conn) throws SQLException {
		String sql = "SELECT * FROM excercises";
		List<Exercise> allExcerciseList = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Exercise loadExcercise = new Exercise();
			loadExcercise.id = rs.getInt("id");
			loadExcercise.title = rs.getString("title");
			loadExcercise.description = rs.getString("description");

			allExcerciseList.add(loadExcercise);
		}
		return allExcerciseList;
	}

	public void deleteExercise(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM excercises WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		}
	}

	public static void loadAllByUserId(Connection conn, int id) throws SQLException {
		ArrayList<String> solutions = new ArrayList<String>();
		String sql = "SELECT excercises.id, solutions.description FROM excercises JOIN solutions ON excercises.id = solutions.excercises_id "
				+ "WHERE solutions.users_id=?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		System.out.println("Opisy zadan przekazane przez użytkownika " + id);
		while (rs.next()) {
			int exerciseId = rs.getInt("excercises.id");
			String solutionDesc = rs.getString("solutions.description");
			solutions.add("zadanie " + exerciseId + ". " + solutionDesc);
		}
		ps.close();
		rs.close();

		for (String s : solutions) {
			System.out.println(s);
		}

	}

}
