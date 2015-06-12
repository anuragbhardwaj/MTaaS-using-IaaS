package rackspace.openstack;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Admin 
{
	

    String JDBC_Driver = "com.mysql.jdbc.Driver";
    String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
    String username = "b21bee797543fe";
    String password = "213fadeb";
    ResultSet dbList;
    
    Connection conn = null;
    Statement stmt = null;
   
       
    
    public ResultSet getAllvmList() throws SQLException
    {
    	
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();
	    	System.out.println("Done");
    		dbList = stmt.executeQuery("select * FROM virtualmachine");
    		while ( dbList.next() ) 
    		{
                String vmId = dbList.getString("vmId");
                String vmName = dbList.getString("vmName");
                System.out.println(vmId);
                System.out.println(vmName);
            }
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
    	}
    	
    	return dbList;
    	
    }
    
    
    public ResultSet getUnOccupiedvmList() throws SQLException
    {
    	
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();
	    	System.out.println("Done");
    		dbList = stmt.executeQuery("select * FROM virtualmachine where isTaken =0");
    		while ( dbList.next() ) 
    		{
                String vmId = dbList.getString("vmId");
                String vmName = dbList.getString("vmName");
                System.out.println(vmId);
                System.out.println(vmName);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
    	}
    	
    	return dbList;
    	
    }
   
    
    public ResultSet getOccupiedvmList() throws SQLException
    {
    	
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();
	    	System.out.println("Done");
    		dbList = stmt.executeQuery("select * FROM virtualmachine where isTaken =0");
    		while ( dbList.next() ) 
    		{
                String vmId = dbList.getString("vmId");
                String vmName = dbList.getString("vmName");
                System.out.println(vmId);
                System.out.println(vmName);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
    	}
    	
    	return dbList;
    	
    }
    
    
    public ResultSet getUsers() throws SQLException
    {
    	
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();
	    	System.out.println("Done");
    		dbList = stmt.executeQuery("select * FROM Users");
    		while ( dbList.next() ) 
    		{
                String userId = dbList.getString("userId");
                String password = dbList.getString("password");
                System.out.println(userId);
                System.out.println(password);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
    	}
    	
    	return dbList;
    	
    }
    
    
    public ResultSet createARatePlan(String ratePlanID,String ratePlanName, double charges) throws SQLException
    {
    	
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	
	    	PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement("INSERT INTO RatePlan(ratePlanID, ratePlanName, charges) VALUES (?, ?, ?)");

	    	pstmt.setString(1, ratePlanID);
	    	pstmt.setString(2, ratePlanName);
	    	pstmt.setDouble(3, charges);
	    	pstmt.executeUpdate();
	    	
	    	Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	    	
    		dbList = stmt.executeQuery("select * FROM RatePlan");
    		while ( dbList.next() ) 
    		{
                String userId = dbList.getString("ratePlanID");
                String password = dbList.getString("ratePlanName");
                System.out.println(userId);
                System.out.println(password);
            }
    		pstmt.close();
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
	    	
    	}
    	
    	return dbList;
    	
    }
    
    public ResultSet getAllRatePlan(String ratePlanID,String ratePlanName, double charges) throws SQLException
    {
    	
    	try
    	{
    		
	    	
	    	Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	    	
    		dbList = stmt.executeQuery("select * FROM RatePlan");
    		while ( dbList.next() ) 
    		{
                String userId = dbList.getString("ratePlanID");
                String password = dbList.getString("ratePlanName");
                System.out.println(userId);
                System.out.println(password);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
	    	
    	}
    	
    	return dbList;
    	
    }
    
    public ResultSet getARatePlan(String ratePlanID) throws SQLException
    {
    	
    	try
    	{
    		
	    	
	    	Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	   
	    	String q="select * FROM RatePlan where "+ratePlanID+"";
    		dbList = stmt.executeQuery(q);
    		while ( dbList.next() ) 
    		{
                String userId = dbList.getString("ratePlanID");
                String password = dbList.getString("ratePlanName");
                System.out.println(userId);
                System.out.println(password);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
	    	
    	}
    	
    	return dbList;
    	
    }
    
    public ResultSet updateRatePlanCharges(String ratePlanID,double charges) throws SQLException
    {
    	
    	try
    	{
    		
	    	
	    	Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	   
	    	String q= "UPDATE RatePlan SET charges="+ charges+" WHERE ratePlanID ="+ratePlanID;
    		stmt.execute(q);
    		q="select * FROM RatePlan where ratePlanID="+ratePlanID+"";
    		dbList = stmt.executeQuery(q);
    		while ( dbList.next() ) 
    		{
                String userId = dbList.getString("ratePlanID");
                String password = dbList.getString("ratePlanName");
                System.out.println(userId);
                System.out.println(password);
            }
    	}
    	catch(Exception e)	
    	{
    		System.out.println(e.getMessage());
    	}
    	finally 
    	{
    		conn.close();
	    	stmt.close();
	    	
    	}
    	
    	return dbList;
    	
    }
       
     
    

}
