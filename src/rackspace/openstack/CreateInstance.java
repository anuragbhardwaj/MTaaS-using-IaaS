package rackspace.openstack;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import java.sql.Date;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.trove.v1.TroveApi;
import org.jclouds.openstack.trove.v1.domain.Flavor;
import org.jclouds.openstack.trove.v1.domain.Instance;
import org.jclouds.openstack.trove.v1.features.FlavorApi;
import org.jclouds.openstack.trove.v1.utils.TroveUtils;
import org.jclouds.rackspace.cloudidentity.*;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import edu.sjsu.dao.DashboardDao;

/**
 * This example creates a Cloud Databases instance.
 * This instance will be used to run a database later on in the Create Database example.
 */
public class CreateInstance implements Closeable {
   private  TroveApi troveApi;
   private  FlavorApi flavorApi;
   public String provider = "openstack-nova";
   public String identity = "admin:admin"; // tenantName:userName
   public String credential = "Passw0rd";
   public String url = "http://192.168.49.129";
   DashboardDao dao = new DashboardDao();
   

   public void create_Instance(int userId, String vmName, String ramSize, String diskSize) {
	   String machineName = vmName;
	   Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
	// Authentication in jclouds is lazy and happens on the first call to the cloud.
	   NovaApi novaApi = ContextBuilder.newBuilder(provider)
       		.endpoint(url+":5000/v2.0/")
            .credentials(identity, credential)
            .modules(modules)
            .buildApi(NovaApi.class);
	   ImageApi imageApi = novaApi.getImageApiForZone(novaApi.getConfiguredZones().iterator().next());
	  
	   	System.out.println("Iterator: "+ novaApi.getConfiguredZones().iterator().next());
	   	ImmutableList<? extends Image> images = imageApi.listInDetail().concat().toList();
	  
	   
	   System.out.println("Image ID: " + images.get(0).getId().toString());
	   org.jclouds.openstack.nova.v2_0.features.FlavorApi flavorApi = novaApi.getFlavorApiForZone(""+ novaApi.getConfiguredZones().iterator().next());

	   ImmutableList<? extends org.jclouds.openstack.nova.v2_0.domain.Flavor> flavors = flavorApi.listInDetail().concat().toList();
	   ServerApi serverApi = novaApi.getServerApiForZone(""+ novaApi.getConfiguredZones().iterator().next());
	   ServerCreated serverCreated = serverApi.create(machineName, images.get(0).getId().toString(), "1");
	   System.out.println("Server ID: " + serverCreated.getId());
	   
	   
	  String JDBC_Driver = "com.mysql.jdbc.Driver";
      String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
      String user = "b21bee797543fe";
      String password = "213fadeb";
      
      Connection conn = null;
      Statement stmt = null;
      int isTaken = 0;
      
      try{
      	Class.forName(JDBC_Driver);
      	conn = (Connection) DriverManager.getConnection(DB_URl,user,password);
      	//System.out.println("Done");
      	String sqlSelect = "select * from ad_bd9086502da4122.virtualmachine";
      	stmt = (Statement) conn.createStatement();
      	ResultSet rs = stmt.executeQuery(sqlSelect);
      	while(rs.next()){
      		String tempVmName = rs.getString("vmName");
      		if(tempVmName.equals(machineName)){
      			throw new Exception("Vm Name already there. Change name!!");
      		}
      	}
      	PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement("INSERT INTO ad_bd9086502da4122.virtualmachine (vmId,vmName,isTaken,userId,startTime,ram,diskSize,endTime,isStopped) VALUES (?,?,?,?,?,?,?,?,?)");
      	preparedStatement.setString(1,serverCreated.getId().toString());
      	preparedStatement.setString(2,machineName);
      	preparedStatement.setInt(3, isTaken);
      	preparedStatement.setString(4,Integer.toString(userId));
      	preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
      	preparedStatement.setString(6, ramSize);
      	preparedStatement.setString(7, diskSize);
      	preparedStatement.setString(8, null);
      	preparedStatement.setInt(9, 0);
      	preparedStatement.execute();
      	conn.close();
      	System.out.println("Pay attention");
      	if(vmName.equals("Clone-1"))
      		dao.allocateVm(provider, identity, credential, url);
      }
      
      catch(Exception e){
      	System.out.println(e);	
      }
      finally{
    	  
      	System.out.println("Finally here!!");
      } 
	   
   }

@Override
public void close() throws IOException {
	// TODO Auto-generated method stub
	
}
}