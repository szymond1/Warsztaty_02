package Model;

public class User {

	private long id;
	private String username; 
	private String email; 
	private String password; 
	private int useGroupId;
	
	public User(String username, String email, String password) {
		super();
		this.id=0l;
		this.username = username;
		this.email = email;
		this.password = password;
		this.useGroupId=0;
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
		this.password = password;
	}

	public int getUseGroupId() {
		return useGroupId;
	}

	public void setUseGroupId(int useGroupId) {
		this.useGroupId = useGroupId;
	}

	public long getId() {
		return id;
	} 
	
	
		
	
}
