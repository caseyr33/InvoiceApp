package model;


import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class UserMainModel
{
    Connection connection;
    
    public UserMainModel() {
        this.connection = SQLiteConnection.Connector();
        if (this.connection == null) {
            System.exit(1);
        }
    }
    
    public boolean addNewUser(final String name, final String username, final String password, final String isAdmin) throws SQLException {
        PreparedStatement preparedStatement = null;
        final String query = "insert into users (Name, username, password, isAdmin) values(?,?,?,?)";
        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, isAdmin);
            preparedStatement.executeUpdate();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally {
            preparedStatement.close();
        }
    }
}
