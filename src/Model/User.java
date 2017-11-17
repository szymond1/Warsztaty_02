package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		this.id=0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId=0;
	}
	
	public User(String username, String email, String password) {
		super();
		this.id=0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId=0;
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
		if(this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id) VALUES (?,?,?,?);";
		
			String[] generatedColumns = {"ID"}; // kolumna jest AUTOINCREMENT, wiec tworzymy tabele, która generuje ID
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
			String sql = "UPDATE users SET username=?, email=?, password=?, user_group_id=? "
					+ "WHERE id=?;";
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
	
	public static User getById(long id) {
		String sql = "";
		//execute sql
		User u = new User();
		return u;
	}
}
