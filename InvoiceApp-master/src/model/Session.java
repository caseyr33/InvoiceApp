package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Session {
	private static String user;
	private static String name;

	public static void setUserInfo(String x) {
		user = x;
		name = getName(x);
	}

	public static String getUser() {
		return user;
	}
	
	public static String getName() {
		return name;
	}

	public static String getName(String user) {
		String str = "";
		try (Connection conn = SQLiteConnection.Connector()) {
			String sql = "select * from users where username = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();
			str = rs.getString("Name");
		} catch (SQLException e) {
			System.out.println("Session Error");
		}
		return str;
	}
}
