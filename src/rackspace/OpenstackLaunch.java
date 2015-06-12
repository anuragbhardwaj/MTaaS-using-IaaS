package rackspace;


import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.trove.v1.TroveApi;
import org.jclouds.openstack.trove.v1.domain.Flavor;
import org.jclouds.openstack.trove.v1.domain.Instance;
import org.jclouds.openstack.trove.v1.features.FlavorApi;
import org.jclouds.openstack.trove.v1.utils.TroveUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import rackspace.openstack.*;

public class OpenstackLaunch 
{
	public String provider = "openstack-nova";
	public String identity = "admin:admin"; // tenantName:userName
	public String credential = "Passw0rd";
	public  String url= "http://192.168.200.173";
	static OpenstackLaunch OSL;
    
	public static void  main(String args[])throws IOException
	{
		try
		{
			 OSL =new OpenstackLaunch();
		}
		finally
		{
			
		}
		
	}
	
	public OpenstackLaunch ()throws IOException
	{		
		try
		{
			//Authentication a =new  Authentication(provider, credential, identity, url);
			JCloudsNova jcn = new JCloudsNova(provider, credential, identity, url);
		}
		finally
		{
			
		}
	}

}
