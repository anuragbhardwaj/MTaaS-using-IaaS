package edu.sjsu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.sjsu.dao.BillingDao;
import edu.sjsu.model.Billing;
import edu.sjsu.model.User;

public class BillingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String LIST_BILLING = "/billing.jsp";
    private BillingDao dao;

    public BillingController() {
        super();
        dao = new BillingDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward="";
        String action = request.getParameter("action");
    	User user = (User)request.getSession().getAttribute("currentSessionUser");
        if (user != null && user.getAuthenticated()){
	        if (action.equalsIgnoreCase("listBilling")){
	            forward = LIST_BILLING;          
	            request.setAttribute("billings", dao.getAllBillings());
	        }
        } else {
        	forward = "/index.jsp";
        }
    	RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String forward="";
    	User user = (User)request.getSession().getAttribute("currentSessionUser");
        if (user != null && user.getAuthenticated()){
	        Billing billing = new Billing();
	
	        billing.setPrice(request.getParameter("price"));
	        String userid = request.getParameter("userid");
	        billing.setUserid(Integer.parseInt(userid));
	        String billingid = request.getParameter("billingid");
	        if(billingid == null || billingid.isEmpty())
	        {
	            dao.addBilling(billing);
	        }
	        else
	        {
	        	billing.setBillingid(Integer.parseInt(billingid));
	            dao.updateBilling(billing);
	        }
	        request.setAttribute("billings", dao.getAllBillings());
	        forward = LIST_BILLING;
        } else {
        	forward = "/index.jsp";
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }
}