/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package rackspace.openstack;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.NovaAsyncApi;
import org.jclouds.rest.RestContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;


public class Logging implements Closeable {
	public  ComputeService computeService;
   public  RestContext<NovaApi, NovaAsyncApi> nova;
   
   public String provider = "openstack-nova";
   public String identity = "admin:admin"; // tenantName:userName
   public String credential = "Passw0rd";
   public String url = "http://192.168.200.173";

   public Logging (String provider, String credential,String identity,String url) throws IOException 
   {
	   this.credential=credential;
   		this.identity=identity;
   		this.provider=provider;
   		this.url=url;
	   Logging logging = new Logging();

      try {
         logging.getConfiguredZones();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         logging.close();
      }
   }

   public Logging() {
  
      Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());
      String provider = "openstack-nova";
      String identity = "admin:admin"; // tenantName:userName
      String credential = "Passw0rd";
      ComputeServiceContext context = ContextBuilder.newBuilder(provider)
            .credentials(identity, credential)
            .endpoint(url+":5000/v2.0/")
            .modules(modules) // don't forget to add the modules to your context!
            .buildView(ComputeServiceContext.class);
      
      computeService = context.getComputeService();
      nova = context.unwrap();
   }

   private void getConfiguredZones() {
       // Calling getConfiguredZones() talks to the cloud which gets logged
       Set<String> zones = nova.getApi().getConfiguredZones();

       System.out.format("Zones%n");

       for (String zone : zones) {
           System.out.format("  %s%n", zone);
       }
   }

   /**
    * Always close your service when you're done with it.
    */
   public void close() throws IOException {
      Closeables.close(computeService.getContext(), true);
   }
}
