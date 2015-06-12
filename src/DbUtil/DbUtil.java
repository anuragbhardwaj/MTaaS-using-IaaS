package DbUtil;

import java.io.*;
import java.sql.*;
import java.util.Properties;


public class DbUtil {
	private static Connection connection = null;
    
    public static Connection getConnection() {
    	if (connection != null)
            return connection;
        else {
        	// MySQL Database Connection.
            try {
                Properties prop = new Properties();
                InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("JDBC_Driver");
                String url = prop.getProperty("DB_URl");
                String user = prop.getProperty("username");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return connection;
        }
    }
}
