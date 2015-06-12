package edu.sjsu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.model.User;
import DbUtil.DbUtil;

public class UserDao {
    private static Connection connection;

	public static Boolean authenticate(User user){
		if(user.getUsername().equals("team") && user.getPassword().equals("team")){
			user.setUserId(1);
			user.setAuthenticated(true);
		} else {
			connection = DbUtil.getConnection();
	        try {
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from users where username=?");
	            preparedStatement.setString(1, user.getUsername());
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
	                if (user.getPassword().equals(rs.getString("password"))) {
	                	user.setUserId(rs.getInt("userid"));
	                    user.setAuthenticated(true);
	                }
	                else
	                	user.setAuthenticated(false);
	            } 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		}
		
		return user.getAuthenticated();
	}

    public static void addUser(User user) {
		connection = DbUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection
            		.prepareStatement("insert into users(username,password) values (?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int userId) {
		connection = DbUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from users where userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(User user) {
		connection = DbUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection
            		.prepareStatement("update users set username=?, password=? where userid=?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
		connection = DbUtil.getConnection();
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static User getUserById(int userId) {
		connection = DbUtil.getConnection();
        User user = new User();
        user.setUserId(userId);
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where userid=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public static User getUserByUsername(String username) {
		connection = DbUtil.getConnection();
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from users where username=?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	user.setUserId(rs.getInt("userid"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
}
