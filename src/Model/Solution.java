package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution {

	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int excercises_id;
	private long users_id;
	
	public Solution() {
		this.id =0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excercises_id = excercises_id;
		this.users_id =users_id;

	}
	
	public Solution(String description, int excercises_id, long users_id) {
		this.id =0;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excercises_id =excercises_id;
		this.users_id = users_id;
	}

	public int getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getDescription() {
		return description;
	}

	public int getExcercise_id() {
		return excercises_id;
	}

	public long getUsers_id() {
		return users_id;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExcercise_id(int excercise_id) {
		this.excercises_id = excercise_id;
	}

	public void setUsers_id(long users_id) {
		this.users_id = users_id;
	}
	
	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solutions(created, updated, description, excercises_id, users_id) VALUES (NOW(), NOW(), ?, ?, ?);";

			String[] generatedColumns = { "ID" }; // kolumna jest AUTOINCREMENT, wiec tworzymy tabele, która generuje ID
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.description);
			ps.setInt(2, this.excercises_id);
			ps.setLong(3, this.users_id);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys(); // baza zwraca wynik
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			ps.close();
			rs.close();

		} else {
			String sql = "UPDATE solutions SET updated=NOW(), description=?, excercise_id=?, users_id=? " + "WHERE id=?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, this.description);
			ps1.setInt(2, this.excercises_id);
			ps1.setLong(3, this.users_id);
			ps1.setInt(4, this.id);
			ps1.executeUpdate();
			ps1.close();
		}
	}

	public static Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solutions WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = rs.getInt("id");
			loadedSolution.created = rs.getDate("created");
			loadedSolution.updated = rs.getDate("updated");
			loadedSolution.description = rs.getString("description");
			loadedSolution.excercises_id = rs.getInt("excercises_id");
			loadedSolution.users_id = rs.getInt("users_id");
			return loadedSolution;
		}
		return null;
	}

	public static List<Solution> loadAllSolution(Connection conn) throws SQLException {
		String sql = "SELECT * FROM solutions";
		List<Solution> allSolutionList = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Solution loadSolution = new Solution();
			loadSolution.id = rs.getInt("id");
			loadSolution.created = rs.getDate("created");
			loadSolution.updated = rs.getDate("updated");
			loadSolution.description = rs.getString("description");
			loadSolution.excercises_id = rs.getInt("excercises_id");
			loadSolution.users_id = rs.getInt("users_id");
			allSolutionList.add(loadSolution);
		}
		return allSolutionList;
	}

	public void deleteUser(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solutions WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		}
	}
	
	
	
	
}
