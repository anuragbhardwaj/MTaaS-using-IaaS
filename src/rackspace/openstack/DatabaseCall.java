package rackspace.openstack;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import edu.sjsu.model.*;

public class DatabaseCall {
	
	public VmData fetchUsername(VmData vmData,String vmId){
		
		String JDBC_Driver = "com.mysql.jdbc.Driver";
	      String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
	      String user = "b21bee797543fe";
	      String password = "213fadeb";
	      
	      Connection conn = null;
	      Statement stmt = null;
		
		try{
			Class.forName(JDBC_Driver);
	      	conn = (Connection) DriverManager.getConnection(DB_URl,user,password);
	      	stmt = (Statement) conn.createStatement();
  			String sqlSelect = "select b.username from ad_bd9086502da4122.virtualmachine a, ad_bd9086502da4122.users b where a.userId = b.userId and vmId = '" + vmId + "'";
  			ResultSet rs = stmt.executeQuery(sqlSelect);
  			while(rs.next()){
          		vmData.setUsername(rs.getString("username"));
          	}
  		}
  		catch(Exception e){
  			System.out.println(e);
  		}
		return vmData;
	}

}
