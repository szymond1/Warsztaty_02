package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {

	private int id;
	private String name;
	
	public Group() {
		this.id=0;
		this.name=name;
	}
	
	public Group(String name) {
		this.id=0;
		this.name=name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO user_group(id, name) VALUES (?,?);";
			String[] generatedColumns = { "ID" }; // kolumna jest AUTOINCREMENT, wiec tworzymy tabele, która generuje ID
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setInt(1, this.id);
			ps.setString(2, this.name);
			ResultSet rs = ps.getGeneratedKeys(); // baza zwraca wynik
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			ps.executeUpdate();
			ps.close();

		} else {
			String sql = "UPDATE user_group SET name=? WHERE id=?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, this.name);
			ps1.setInt(2, this.id);
			ps1.executeUpdate();
			ps1.close();
		}
	}
	
	
	public static Group loadGroupById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = rs.getInt("id");
			loadedGroup.name = rs.getString("name");
			return loadedGroup;
		}
		return null;
	}

	public static List<Group> loadAllGroup(Connection conn) throws SQLException {
		String sql = "SELECT * FROM user_group";
		List<Group> allGroupsList = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Group loadedGroup = new Group();
			loadedGroup.id = rs.getInt("id");
			loadedGroup.name = rs.getString("name");
			allGroupsList.add(loadedGroup);
		}
		return allGroupsList;
	}

	public void deleteGroup(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM user_group WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		}
	}
	
}
