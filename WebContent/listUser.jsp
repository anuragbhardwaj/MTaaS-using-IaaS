<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="edu.sjsu.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>List User</title>
      <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
      <link href="starter-template.css" rel="stylesheet">
</head>
<body>  
 <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">TestMaster</a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="VmController?action=listVm">Virtual Machines</a></li>
            <li><a href="Hubs?action=listHub">Hub</a></li>
            <li><a href="Devices?action=listDevice">Device</a></li>
            <li class="active"><a href="Users?action=listUser">User</a></li>
            <li><a href="BillingController?action=listBilling">Billing</a></li>
          </ul>
            <div class="navbar-form navbar-right">
         <p style="color:white;">Welcome,<c:out value="${currentSessionUser.username}" />
            <button  class="btn btn-success" onclick="location.href='/Cmpe281_Project/Login?action=logout'">Logout</button>
          </p>
        </div>
        </div>
      </div>
    </div>
     <div class="container theme-showcase" >
      <div class="page-header">
      <br>
       <h1>Users</h1>
      </div>
    <table class="table">
        <thead>
            <tr>
                <th>User ID</th>
                <th>User Name</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.userId}" /></td>
                    <td><c:out value="${user.username}" /></td>
                    <td>
                    	<% if (((User)session.getAttribute("currentSessionUser")).getUserId() == ((User)pageContext.getAttribute("user")).getUserId()) { %>
                    		<a href="Users?action=edit&userId=<c:out value="${user.userId}"/>">Update</a>
                    	<% } %>
                    </td>
                    <td>
                        <% if (((User)session.getAttribute("currentSessionUser")).getUserId() == ((User)pageContext.getAttribute("user")).getUserId()) { %>
                    		<a href="Users?action=delete&userId=<c:out value="${user.userId}"/>">Delete</a></td>
                    	<% } %>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
</body>
</html>