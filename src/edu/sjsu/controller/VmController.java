package edu.sjsu.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rackspace.OpenstackLaunch;
import edu.sjsu.dao.DashboardDao;
import edu.sjsu.model.VmData;
import edu.sjsu.model.User;

public class VmController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/createInstance.jsp";
    private static String LIST_VM_ADMIN = "/dashBoard.jsp";
    private static String LIST_VM_USER = "/user_Dashboard.jsp";
    private static String DETAIL_VM = "/instanceOverview.jsp";
    private static String LOGIN = "/index.jsp";
    private static String BILLING = "/billing.jsp";
    
    public String provider = "openstack-nova";
	public String identity = "admin:admin"; // tenantName:userName
	public String credential = "Passw0rd";
	public  String url= "http://192.168.200.173";
	static OpenstackLaunch OSL;
    
    private DashboardDao dao;

    public VmController() {
        super();
        dao = new DashboardDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        
        String action = request.getParameter("action");
        User user = (User)request.getSession().getAttribute("currentSessionUser");
        int userId = user.getUserId();
        if (user != null && user.getAuthenticated()){
	        if (action.equalsIgnoreCase("delete")){
	            String vmId = request.getParameter("vmId");
	            dao.deleteVm(vmId);
	            if(userId == 0)
	            	forward = LIST_VM_ADMIN;
	            else
	            	forward = LIST_VM_USER;
	            request.setAttribute("VmDatas", dao.getVmList(userId));    
	        } else if(action.equalsIgnoreCase("detail")) {
	        	String vmId = request.getParameter("vmId");
	        	//dao.getVmDetail(vmId);
	        	forward = DETAIL_VM;
	            request.setAttribute("VmDatas", dao.getVmDetail(vmId));
	        } else if(action.equalsIgnoreCase("stop")) {
	        	String vmId = request.getParameter("vmId");
	            dao.releaseVm(vmId);
	            request.setAttribute("Billings",dao.calculateBilling(vmId));
	            forward = BILLING;
	        	
	        } else if (action.equalsIgnoreCase("listProject")){
	        	if(userId == 0)
	            	forward = LIST_VM_ADMIN;
	            else
	            	forward = LIST_VM_USER;
	            request.setAttribute("VmDatas", dao.getVmList(userId));
	        } else if(action.equalsIgnoreCase("allocate")){
	        	System.out.println("Allocate");
	        	dao.allocateVm(provider, identity, credential, url);
	        	request.setAttribute("VmDatas", dao.getVmList(userId));
	        	if(userId == 0)
	            	forward = LIST_VM_ADMIN;
	            else
	            	forward = LIST_VM_USER;
	        }
	        else if(action.equalsIgnoreCase("stopInstance")){
	        	System.out.println("Stop Instance");
	        	String vmId = request.getParameter("vmId");
	        	dao.stopInstance(vmId);
	        	request.setAttribute("VmDatas", dao.getVmList(userId));
	        	if(userId == 0)
	        		forward = LIST_VM_ADMIN;
	        	else
	        		forward = LIST_VM_USER;
	        }
	        else if(action.equalsIgnoreCase("startInstance")){
	        	System.out.println("Start Instance");
	        	String vmId = request.getParameter("vmId");
	        	dao.startInstance(vmId);
	        	request.setAttribute("VmDatas", dao.getVmList(userId));
	        	if(userId == 0)
	        		forward = LIST_VM_ADMIN;
	        	else
	        		forward = LIST_VM_USER;
	        }
	        else if(action.equalsIgnoreCase("reboot")){
	        	String vmId = request.getParameter("vmId");
	        	dao.rebootInstance(vmId);
	        	request.setAttribute("VmDatas", dao.getVmList(userId));
	        	if(userId == 0)
	        		forward = LIST_VM_ADMIN;
	        	else
	        		forward = LIST_VM_USER;
	        }
	        else if(action.equalsIgnoreCase("snapshot")){
	        	String vmId = request.getParameter("vmId");
	        	String vmName = request.getParameter("vmName");
	        	dao.takeSnapshot(vmId,vmName);
	        	request.setAttribute("VmDatas", dao.getVmList(userId));
	        	if(userId == 0)
	        		forward = LIST_VM_ADMIN;
	        	else
	        		forward = LIST_VM_USER;
	        }
	        else {
	            forward = INSERT_OR_EDIT;
	        }
        } else {
        	forward = LOGIN;
        }	
	
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward="";
    	User user = (User)request.getSession().getAttribute("currentSessionUser");
    	String action = request.getParameter("action");
        if (user != null && user.getAuthenticated()) {
        	if (user != null && user.getAuthenticated()){   
    	        	VmData vm = new VmData();
    		        vm.setInstance_Name(request.getParameter("projectName"));
    		        //System.out.println("User ID: " + userId);
    		        int userId = user.getUserId();
    		        String projectid = request.getParameter("projectid");
    		        String vmName = request.getParameter("instanceName");
    		        String ramSize = request.getParameter("ramSize");
    		        String diskSize = request.getParameter("diskSize");
    		        if(projectid == null || projectid.isEmpty())
    		        {
    		            dao.addVm(userId,vmName,ramSize,diskSize);
    		        }
    		        
    		        request.setAttribute("VmDatas", dao.getVmList(userId));
    		        if(userId == 0)
    	            	forward = LIST_VM_ADMIN;
    	            else
    	            	forward = LIST_VM_USER;
        	}
    		        
    	        else {
    	        	forward = LOGIN;
    	        }   
        	  
        	}
 
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
    
}

