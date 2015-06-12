package rackspace.openstack;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.jclouds.openstack.nova.v2_0.NovaApi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class UserDOA 
{
	 private  NovaApi novaApi;
	    private  Set<String> regions;
	    public String provider = "openstack-nova";
	    public String identity = "admin:admin"; // tenantName:userName
	    public String credential = "Passw0rd";
	    public String url = "http://192.168.49.129";
	
	String JDBC_Driver = "com.mysql.jdbc.Driver";
    String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
    String username = "b21bee797543fe";
    String password = "213fadeb";
    ResultSet dbList;
    
    Connection conn = null;
    Statement stmt = null;
    
    public  ResultSet getVmForUserId( String userId) throws SQLException
    {
    	try
    	{
    		JCloudsNova jcn = new JCloudsNova(provider, credential, identity, url);
    		//jcn.listServers(userId);
    		
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();
	    	
	    	
    		dbList = stmt.executeQuery("select * FROM virtualmachine where userId  = "+userId);
    		System.out.println("Done");
    		while ( dbList.next() ) 
    		{
    			 String vmId = dbList.getString("vmId");
                 String vmName = dbList.getString("userId");
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
    
    
    public  ResultSet setRatePlan( String ratePlanID,String userId) throws SQLException
    {
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	   
	    	String q= "UPDATE Users SET ratePlanID="+ ratePlanID+" WHERE userId ="+userId+"";
    		stmt.execute(q);
    		q="select * FROM Users";
    		dbList = stmt.executeQuery(q);
    		
    		
    		while ( dbList.next() ) 
    		{
                 userId = dbList.getString("userId");
                String password = dbList.getString("ratePlanID");
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
    
    public Object BillAmount( String userId) throws SQLException
    {
    	double duration=0.0;
    	double charges=0.0;
    	userId="'"+userId+"'";
    	try
    	{
    		Class.forName(JDBC_Driver);
	    	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
	    	stmt = (Statement) conn.createStatement();	   
	    	String q= "select * FROM Users WHERE userId ="+userId+"";
	    	
    		dbList = stmt.executeQuery(q);
    		
    		 String ratePlanID="";
    		while ( dbList.next() ) 
    		{
                 
                 ratePlanID = dbList.getString("ratePlanID");
                 ratePlanID="'"+ratePlanID+"'";
                System.out.println(ratePlanID);
            }	
    		
           q= "select * FROM RatePlan WHERE ratePlanID ="+ratePlanID+"";
    		
    		dbList = stmt.executeQuery(q);
    		while ( dbList.next() ) 
    		{
                 
    			 charges = dbList.getDouble("charges");
            }	
    		System.out.println( charges);
    		 q="select (NOW()-startTime)/(36000 ) as 'duration' FROM virtualmachine where userId ="+userId+"";
    		 dbList = stmt.executeQuery(q);
    		 while ( dbList.next() ) 
     		{
                  
     			 duration = dbList.getDouble("duration");
             }	
    		System.out.println(duration);
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
    	//String ret =""+((double)charges*(double)duration);
    	System.out.println(""+((double)charges*(double)duration));
    	return ((double)charges*(double)duration);
    }
  
    
    public void deAllocateVm(String userId)
    {
    	try{
        	Class.forName(JDBC_Driver);
        	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
        	stmt = (Statement) conn.createStatement();
        	String sqlUpdate = "update virtualmachine set isTaken = 0,userId='' where userId = ?";
        	PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(sqlUpdate);
        	
        	preparedStatement.setString(1, userId);
        	preparedStatement.execute();
        	conn.close();
        }
        
        catch(Exception e){
        	System.out.println(e);
        }
    }

}
