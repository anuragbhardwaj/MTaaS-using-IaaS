package rackspace.openstack;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.concurrent.TimeoutException;
import java.sql.Timestamp;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.trove.v1.TroveApi;
import org.jclouds.openstack.trove.v1.domain.Flavor;
import org.jclouds.openstack.trove.v1.domain.Instance;
import org.jclouds.openstack.trove.v1.features.FlavorApi;
import org.jclouds.openstack.trove.v1.utils.TroveUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class ReleaseInstance
{
   private  TroveApi troveApi;
   private  FlavorApi flavorApi;
   public String provider = "openstack-nova";
   public String identity = "admin:admin"; // tenantName:userName
   public String credential = "Passw0rd";
   public String url = "http://192.168.49.129";
   
   private String vmId;
   
   public ReleaseInstance(String vmId){
	   this.vmId = vmId;
   }

   public void releaseInstance(){
     
       String JDBC_Driver = "com.mysql.jdbc.Driver";
       String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
       String user = "b21bee797543fe";
       String password = "213fadeb";

       Connection conn = null;
       Statement stmt = null;

       try{
           String tempVirtualMachineID = vmId;
           Class.forName(JDBC_Driver);
           conn = (Connection) DriverManager.getConnection(DB_URl,user,password);
           //System.out.println("Done");
           String sqlDelete = "update virtualmachine set isTaken = ?, endTime = ? where vmId = ?";
           stmt = (Statement) conn.createStatement();
           //ResultSet rs = stmt.executeQuery(sqlDelete);
           PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(sqlDelete);
           preparedStatement.setString(1, "0");
           preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
           preparedStatement.setString(3,tempVirtualMachineID);
           preparedStatement.execute();

           //serverApi.delete(tempVirtualMachineID.toString());
           System.out.println("VM Released.");
           conn.close();
       }

       catch(Exception e){
           System.out.println(e);
       }
       finally{
           System.out.println("");
       }
   }
   
   public void close() throws IOException {
      Closeables.close(troveApi, true);
   }
}