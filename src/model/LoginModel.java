package model;


import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

public class LoginModel
{
    Connection connection;
    
    public LoginModel() {
        this.connection = SQLiteConnection.Connector();
        if (this.connection == null) {
            System.exit(1);
        }
    }
    
    public boolean isDbConnected() {
        try {
            return !this.connection.isClosed();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isLoggedIn(final String user, final String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        final String query = "select * from users where username = ? and password = ?";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        finally {
           // preparedStatement.close();
            //resultSet.close();
        }
    }
    
    
    
    //
   
    
}