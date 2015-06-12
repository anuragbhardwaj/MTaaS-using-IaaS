package edu.sjsu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.model.VmData;
import DbUtil.DbUtil;
import rackspace.openstack.*;
import edu.sjsu.model.*;

public class DashboardDao {
	private Connection connection;

    public DashboardDao() {
        connection = DbUtil.getConnection();
    }
    
    public void addVm(int userId, String vmName, String ramSize, String diskSize) {
        try {
            CreateInstance CI = new CreateInstance();
        	CI.create_Instance(userId, vmName,ramSize,diskSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void allocateVm(String provider, String credential, String identity, String url){
    	try{
    		JCloudsNova jcn = new JCloudsNova(provider,credential,identity,url);
    	    
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
   }
    
    public void stopInstance(String vmId){
    	StopVm svm = new StopVm();
    	svm.stopInstance(vmId);
    }
    
    public void startInstance(String vmId){
    	StartVm svm = new StartVm();
    	svm.startInstance(vmId);
    }
    
    public void rebootInstance(String vmId){
    	RebootVm reboot = new RebootVm();
    	reboot.rebootInstance(vmId);
    }
    
    public void takeSnapshot(String vmId, String vmName){
    	CreateSnapshot csnap = new CreateSnapshot();
    	csnap.createSnapshot(vmId,vmName);
    }
    
    public void releaseVm(String vmId){
    	try{
    		ReleaseInstance release = new ReleaseInstance(vmId);
        	release.releaseInstance();
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
    	
    }
    
    public List<VmData> getVmDetail(String vmId) {
    	Statement stmt = null;
        List<VmData> vmDatas = new ArrayList<VmData>();
        try {
        	VmData vmData = new VmData();
        	String sqlSelect = "select * from ad_bd9086502da4122.virtualmachine where vmId = '" + vmId + "'";
        	stmt = (Statement) connection.createStatement();
          	ResultSet rs = stmt.executeQuery(sqlSelect);
          	
          	//DefaultTableModel model = new DefaultTableModel(); 
          	//JTable table = new JTable(model); 
          	
          	

          	while(rs.next()){
          		
          		vmData.setInstance_ID(rs.getString("vmId"));
          		vmData.setInstance_Name(rs.getString("vmName"));
          		vmData.setInstance_Taken(rs.getString("isTaken"));
          		vmData.setStartTime(rs.getString("startTime"));
          		vmData.setRam(rs.getString("ram"));
          		vmData.setDiskSize(rs.getString("diskSize"));
          		vmDatas.add(vmData);
          	}
          	
        }
        catch(Exception e){
        	System.out.println(e);
        }
        finally{
        	System.out.println("");
        }
        
        return vmDatas;
    }
    
    public List<Billing> calculateBilling(String vmId){
    	Double charges = 0.0;
    	Double duration = 0.0;
    	String userId = null;
    	String vmName = null;
    	Statement stmt = null;
    	//Connection conn = null;
        List<Billing> billing = new ArrayList<Billing>();
        try{
        	String sqlSelect = "select TIMESTAMPDIFF(MINUTE,startTime,endTime) as duration, userId, vmName from ad_bd9086502da4122.virtualmachine where vmId = '"+vmId+"'";
        	stmt = (Statement) connection.createStatement();
          	ResultSet rs = stmt.executeQuery(sqlSelect);
          	//DefaultTableModel model = new DefaultTableModel(); 
          	//JTable table = new JTable(model); 
          	
          	while(rs.next()){
          		duration = rs.getDouble("duration");
          		userId = rs.getString("userId");
          		vmName = rs.getString("vmName");
          	}
          	
          	charges = duration * 0.1;
          	System.out.println(duration);
          	System.out.println(charges);
          	try{
          		PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO ad_bd9086502da4122.billing (billId,userId,vmId,vmName,billAmt) VALUES (?,?,?,?,?)");
              	preparedStatement.setInt(1, 0);
              	preparedStatement.setString(2,userId);
              	preparedStatement.setString(3,vmId);
              	preparedStatement.setString(4, vmName);
              	preparedStatement.setDouble(5,charges);
              	preparedStatement.execute();
              	
          	}
          	catch(Exception e){
          		System.out.println(e);
          	}
          	try{
          		String sqlSelect2 = "select * from ad_bd9086502da4122.billing where vmId = '" + vmId + "'";
          		stmt = connection.createStatement();
          		ResultSet rs2 = stmt.executeQuery(sqlSelect2);
          		while(rs2.next()){
          			Billing bill = new Billing();
          			bill.setBillId(rs2.getInt("billId"));
          			bill.setUserId(rs2.getString("userId"));
          			bill.setVmId(rs2.getString("vmId"));
          			bill.setVmName(rs2.getString("vmName"));
          			bill.setBillAmt(rs2.getDouble("billAmt"));
          			billing.add(bill);
          		/* 
          			int billId = rs2.getInt("billId");
          			userId = rs2.getString("userId");
          			vmId = rs2.getString("vmId");
          			Double billAmt = rs2.getDouble("billAmt");
          			
          		*/
          		}
          		//connection.close();
          		
          	}
          	catch(Exception e){
          		System.out.println(e);
          	}
        }
        catch(Exception e){
        	System.out.println(e);
        }
    	return billing;
    }

    public void deleteVm(String vmId) {
        try {
        	DeleteInstance DI = new DeleteInstance(vmId);
        	DI.delete_Instance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VmData> getVmList(int userId) {
        Statement stmt = null;
        List<VmData> vmDatas = new ArrayList<VmData>();
        try {
        	if(userId == 0){
        		String sqlSelect = "select * from ad_bd9086502da4122.virtualmachine";
        	    stmt = (Statement) connection.createStatement();
        	    ResultSet rs = stmt.executeQuery(sqlSelect);

        	    while(rs.next()){
        	    	VmData vmData = new VmData();
        	        vmData.setInstance_ID(rs.getString("vmId"));
        	        vmData.setInstance_Name(rs.getString("vmName"));
        	        vmData.setInstance_Taken(rs.getString("isTaken"));
        	        vmData.setUserId(rs.getString("userId"));
        	        vmData.setStartTime(rs.getString("startTime"));
        	        vmData.setInstanceStatus(rs.getString("isStopped"));
        	        vmDatas.add(vmData);
        	    }
        	          	//connection.close();
        	}
        	else{
        		String sqlSelect = "select * from ad_bd9086502da4122.virtualmachine where userId = " + userId;
        	    stmt = (Statement) connection.createStatement();
        	    ResultSet rs = stmt.executeQuery(sqlSelect);

        	    while(rs.next()){
        	    	VmData vmData = new VmData();
        	        vmData.setInstance_ID(rs.getString("vmId"));
        	        vmData.setInstance_Name(rs.getString("vmName"));
        	        vmData.setInstance_Taken(rs.getString("isTaken"));
        	        vmData.setUserId(rs.getString("userId"));
        	        vmData.setStartTime(rs.getString("startTime"));
        	        vmData.setInstanceStatus(rs.getString("isStopped"));
        	        vmDatas.add(vmData);
        	    }

        	}
        	
        }
        catch(Exception e){
        	System.out.println(e);
        }
        finally{
        	System.out.println("");
        }
        
        return vmDatas;
	}
    
}
