package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private long id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;

	public User() {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		this.setPassword(password);
		this.userGroupId = 0;
	} 

	public User(String username, String email, String password) {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = 0; 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void checkPassword(String password) {
		BCrypt.checkpw(password, this.password);
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int useGroupId) {
		this.userGroupId = useGroupId;
	}

	public long getId() {
		return id;
	}

	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?,?,?,?);";

			String[] generatedColumns = { "ID" }; // kolumna jest AUTOINCREMENT, wiec tworzymy tabele, która generuje ID
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, this.userGroupId);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys(); // baza zwraca wynik
			if (rs.next()) {
				this.id = rs.getLong(1);
			}
			ps.close();
			rs.close();

		} else {
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? " + "WHERE id=?;";
			PreparedStatement ps1 = conn.prepareStatement(sql);
			ps1.setString(1, this.username);
			ps1.setString(2, this.email);
			ps1.setString(3, this.password);
			ps1.setInt(4, this.userGroupId);
			ps1.setLong(5, this.id);
			ps1.executeUpdate();
			ps1.close();
		}
	}

	public static User loadUserById(Connection conn, long id) throws SQLException {
		String sql = "SELECT * FROM users WHERE id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			User loadedUser = new User();
			loadedUser.id = rs.getLong("id");
			loadedUser.username = rs.getString("username");
			loadedUser.email = rs.getString("email");
			loadedUser.password = rs.getString("password");
			loadedUser.userGroupId = rs.getInt("user_group_id");
			return loadedUser;
		}
		return null;
	}

	public static List<User> loadAllUser(Connection conn) throws SQLException {
		String sql = "SELECT * FROM users";
		List<User> allUserList = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User loadUser = new User();
			loadUser.id = rs.getLong("id");
			loadUser.username = rs.getString("username");
			loadUser.email = rs.getString("email");
			loadUser.password = rs.getString("password");
			loadUser.userGroupId = rs.getInt("user_group_id");
			allUserList.add(loadUser);
		}
		return allUserList;
	}

	public void deleteUser(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM users WHERE id= ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
			this.id = 0;
		}
	}

	public static void loadAllByGrupId (Connection conn, int userGroupId) throws SQLException {
		String sql = " SELECT id, username FROM users WHERE user_group_id = ?;";
		List<User> listOfGroups = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userGroupId);
		ResultSet rs = ps.executeQuery();
		System.out.println("Wszyscy czlonkowie grupy o id " + userGroupId);
		while (rs.next()) {
			User u = new User();
			u.id = rs.getInt("id");
			u.username = rs.getString("username");
			listOfGroups.add(u);
		}

		for (User u : listOfGroups) {
			System.out.println(u.id + " | " + u.username + " |");
		}
		
	}
	
	
}
