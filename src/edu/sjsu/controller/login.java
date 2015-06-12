package edu.sjsu.controller;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sjsu.dao.DashboardDao;
import edu.sjsu.model.User;
import edu.sjsu.dao.UserDao;


public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String LOGIN = "/index.jsp";
    private DashboardDao dao;
    public login() {
        super();
        dao = new DashboardDao();
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = null;
    	User user = null;
    	String page = null;
    	if((action = request.getParameter("action")) == null) {
	    	if((user = (User)request.getSession().getAttribute("currentSessionUser")) == null) {
		        page = LOGIN;
	    	} else if (user.getAuthenticated()){
		        page = "/dashBoard.jsp";
		        request.setAttribute("VmDatas", dao.getVmList(user.getUserId()));
	    	} else {
	    		request.setAttribute("user", user);
	    		page = LOGIN;
	    	}
    	} else if (action.equals("logout")) {
    		if(request.getSession() != null && (User)request.getSession().getAttribute("currentSessionUser") != null) {
    			user = (User)request.getSession().getAttribute("currentSessionUser");
    			user.setAuthenticated(false);
    			request.getSession().removeAttribute("currentSessionUser");
    			request.setAttribute("user", user);
    		}
    		page = LOGIN;
    	} else {
    		page = LOGIN;
    	}
    	RequestDispatcher view = request.getRequestDispatcher(page);
    	view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher view = null;
    	
    	User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        HttpSession session = request.getSession();
        
        if (UserDao.authenticate(user)) {
        	user = UserDao.getUserByUsername(user.getUsername());
        	user.setAuthenticated(true);
	        
			session.setAttribute("currentSessionUser", user);
			System.out.println("User ID: " + user.getUserId());
			if(user.getUserId() == 0){
				view = request.getRequestDispatcher("/dashBoard.jsp");
			}
			else
				view = request.getRequestDispatcher("/user_Dashboard.jsp");
			
        } else {
        	request.setAttribute("err", "Username and/or password is Wrong.");
        	view = request.getRequestDispatcher(LOGIN);
        }
        request.setAttribute("VmDatas", dao.getVmList(user.getUserId()));
       
      
        view.forward(request, response);
    }
}
