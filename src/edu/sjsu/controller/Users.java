package edu.sjsu.controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.sjsu.model.User;
import edu.sjsu.dao.UserDao;

public class Users extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/registerUser.jsp";
    private static String USER_DASH = "/user_DashBoard.jsp";
    private static String LIST_USER = "/listUser.jsp";
    private static String LOGIN = "/index.jsp";

    public Users() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        User cuser = (User)request.getSession().getAttribute("currentSessionUser");
        if (cuser != null && cuser.getAuthenticated()){
	        if (action != null)
	        {
		        if (action.equalsIgnoreCase("delete")){
		            int userId = Integer.parseInt(request.getParameter("userId"));
		            UserDao.deleteUser(userId);
		            forward = LIST_USER;
		            request.setAttribute("users", UserDao.getAllUsers());    
		        } else if (action.equalsIgnoreCase("edit")){
		            forward = INSERT_OR_EDIT;
		            int userId = Integer.parseInt(request.getParameter("userId"));
		            User user = UserDao.getUserById(userId);
		            request.setAttribute("user", user);
		        } else if (action.equalsIgnoreCase("listUser")){
		            forward = LIST_USER;
		            request.setAttribute("users", UserDao.getAllUsers());
		        } else {
		            forward = INSERT_OR_EDIT;
		        }
	        } else {
	        	forward = INSERT_OR_EDIT;
	        }
        } else if(action.equalsIgnoreCase("insert")){
        	forward = INSERT_OR_EDIT;
        } else {
        	System.out.println(1);
        	forward = LOGIN;
        }
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
    	User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        
        String userId = request.getParameter("userId");
        if(userId == null || userId.isEmpty()){
            UserDao.addUser(user);
        } else {
        	user.setUserId(Integer.parseInt(userId));
            UserDao.updateUser(user);
        }
        
        HttpSession session = request.getSession();
        user.setAuthenticated(true);
		session.setAttribute("currentSessionUser", user);
		forward = USER_DASH;
        
        RequestDispatcher view = request.getRequestDispatcher(forward);
        
        if(userId != null){
        	request.setAttribute("User", UserDao.getAllUsers());
        }
        view.forward(request, response);
    }
}
