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
import org.jclouds.openstack.nova.v2_0.domain.RebootType;
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


/**
 * This example creates a Cloud Databases instance.
 * This instance will be used to run a database later on in the Create Database example.
 */
public class RebootVm implements Closeable {
   private static TroveApi troveApi;
   private  FlavorApi flavorApi;
   public static String provider = "openstack-nova";
   public static String identity = "admin:admin"; // tenantName:userName
   public static String credential = "Passw0rd";
   public static String url = "http://192.168.49.129";
   public static RebootType SOFT=null;

   public void rebootInstance(String vmId) {
	   
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
	   //ServerCreated serverCreated = serverApi.create(machineName, images.get(0).getId().toString(), "1");
	   //System.out.println("Server ID: " + serverCreated.getId());
	   //serverApi.stop("f86191bf-911e-4e56-be38-a8a7ca93aa6f");
	   //System.out.println("Stopped");
	   serverApi.reboot(vmId, RebootType.SOFT);
	   System.out.println("Reboot"); 
   }


   	public void close() throws IOException {
   		// TODO Auto-generated method stub
	
   	}	
}