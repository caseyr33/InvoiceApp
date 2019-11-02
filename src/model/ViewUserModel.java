package model;


/*Class to represent a USER in the system*/
public class ViewUserModel {
	
	private String name, username;
	private boolean isAdmin;
	
	public ViewUserModel(String name, String username, boolean isAdmin) {
		this.name = name;
		this.username = username;
		this.isAdmin = isAdmin;
	}
	 
	public String getName() {
		return this.name;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getAdmin() {
		if(this.isAdmin) {
			return "Yes";
		}
		return "No";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}