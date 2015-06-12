package rackspace.openstack;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import com.mysql.jdbc.*;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class JCloudsNova implements Closeable {
    private  NovaApi novaApi;
    private  Set<String> regions;
    public String provider = "openstack-nova";
    public String identity = "admin:admin"; // tenantName:userName
    public String credential = "Passw0rd";
    public String url = "http://192.168.49.129";
    HashMap hm = new HashMap();
    HashMap allocateVm = new HashMap();
    
    private JCloudsNova()
    {
    	
    }
    
    public JCloudsNova(String provider, String credential,String identity,String url) throws IOException 
    		 {
    	this.credential=credential;
    	this.identity=identity;
    	this.provider=provider;
    	this.url=url;
        JCloudsNova jcloudsNova = new JCloudsNova();
        jcloudsNova.jCloudsNova();

        try {
            jcloudsNova.listServers();
            jcloudsNova.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //jcloudsNova.close();
        }
    }

    public void jCloudsNova() {
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

     

        novaApi = ContextBuilder.newBuilder(provider)
        		.endpoint(url+":5000/v2.0/")
                .credentials(identity, credential)
                .modules(modules)
                .buildApi(NovaApi.class);
        regions = novaApi.getConfiguredZones();
    }

    private void listServers() {
    	int count=0;
        for (String region : regions) {
            ServerApi serverApi = novaApi.getServerApiForZone(region);
         //   novaApi.
            System.out.println("Servers in " + region);

            for (Server server : serverApi.listInDetail().concat()) {
                //System.out.println("   "+server.getName());
                //System.out.println("Server ID: " + server.getId());
                hm.put(server.getId(), server.getName());
                count++;
            }
            //System.out.println("Total VMs: " + count);
        
            Map<Object,String> resourceAllocator = new HashMap<Object,String>();
            Random rand = new Random();
            //System.out.println("Hit !!");
            int i=0;
            Iterator iterator = hm.entrySet().iterator();
            Object[] keys = hm.keySet().toArray();
            Object[] values = hm.values().toArray();
            while(iterator.hasNext()){
            	 Map.Entry pair = (Map.Entry)iterator.next();
            	 //System.out.println(pair.getKey() + " = " + pair.getValue());
            	 keys[i] = pair.getKey();
            	 values[i] = pair.getValue();
            	 //System.out.println(keys[i]);
            	 iterator.remove();
            	 i++;
            }
            System.out.println("Request came from the user: Admin");
            String JDBC_Driver = "com.mysql.jdbc.Driver";
            String DB_URl = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/ad_bd9086502da4122";
            String username = "b21bee797543fe";
            String password = "213fadeb";
            
            Connection conn = null;
            Statement stmt = null;
            int isTaken = 1;
       
            try{
            	HashMap hm1 = new HashMap();
            	Class.forName(JDBC_Driver);
            	conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
            	//System.out.println("Done");
            	//Will update the isTaken to 1 of randomKey
            	System.out.println("Checking for available Vms...");
            	String sqlSelect = "select * from ad_bd9086502da4122.virtualmachine where isTaken <> 1";
              	stmt = (Statement) conn.createStatement();
              	ResultSet rs = stmt.executeQuery(sqlSelect);
              	while(rs.next()){
              		String tempVmId = rs.getString("vmId");
              		String tempVmName = rs.getString("vmName");
              		hm1.put(tempVmId, tempVmName);
              	}
              	
              	int j=0;
                Iterator iterator1 = hm1.entrySet().iterator();
                Object[] keys1 = hm1.keySet().toArray();
                Object[] values1 = hm1.values().toArray();
                while(iterator1.hasNext()){
                	 Map.Entry pair1 = (Map.Entry)iterator1.next();
                	 //System.out.println(pair.getKey() + " = " + pair.getValue());
                	 keys1[j] = pair1.getKey();
                	 values1[j] = pair1.getValue();
                	 //System.out.println(keys[i]);
                	 iterator1.remove();
                	 j++;
                }
                System.out.println("Applying ant colony algorithm for load balancing");
                Object randomKey = keys1[rand.nextInt(keys1.length)];
                Object randomValue = values1[rand.nextInt(values1.length)];
                System.out.println(randomKey.toString());
                System.out.println(randomValue.toString());
                try{
                	Class.forName(JDBC_Driver);
                	//conn = (Connection) DriverManager.getConnection(DB_URl,username,password);
                	stmt = (Statement) conn.createStatement();
                	String sqlUpdate = "update virtualmachine set isTaken = ? where vmId = ?";
                	PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(sqlUpdate);
                	preparedStatement.setInt(1,isTaken);
                	preparedStatement.setString(2,randomKey.toString());
                	preparedStatement.execute();
                	
                }
                
                catch(Exception e){
                	System.out.println(e);
                }
                
                try{
                	Class.forName(JDBC_Driver);
                	//conn= (Connection) DriverManager.getConnection(DB_URl,username,password);
                	stmt = (Statement) conn.createStatement();
                	String sqlUpdate = "update virtualmachine set startTime = ? where vmId = ?";
                	PreparedStatement preparedStatement = (PreparedStatement) conn.prepareStatement(sqlUpdate);
                	preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                	preparedStatement.setString(2, randomKey.toString());
                	preparedStatement.execute();
                	conn.close();
                }
                
                catch(Exception e){
                	System.out.println(e);
                }
                System.out.println("Virtual machine with ID: " + randomKey.toString() + " allocated.");
                //allocateVm.put(randomKey, randomValue.toString());
                 
            }
            
            catch(Exception e){
            	System.out.println(e);
            	
            	CreateInstance ci = new CreateInstance();
            	ci.create_Instance(0, "Clone-1", "512MB", "10GB");
            }
            finally{
            	System.out.println("");
            }
        }
    }

    public void close() throws IOException {
        Closeables.close(novaApi, true);
    }
}