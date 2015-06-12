<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="edu.sjsu.model.User" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Billing Information</title>
      <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
      <link href="starter-template.css" rel="stylesheet">
      <script>
      function returnAndGo(){
    	  history.go(-1);
    	  
      }
      </script>
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
            <li><a href="VmController?action=listProject">Virtual Machine</a></li>
            
            <li><a href="Hubs?action=listHub">Hub</a></li>
            <li><a href="Devices?action=listDevice">Device</a></li>
            <li><a href="Users?action=listUser">User</a></li>
            <li class="active"><a href="BillingController?action=listBilling">Billing</a></li>
          </ul>
          <div class="navbar-form navbar-right">
          <p style="color:white;">Welcome,<c:out value="${currentSessionUser.username}" />
            <button  class="btn btn-success" onclick="location.href='/Cmpe281_Project/Login?action=logout'">Logout</button>
          </p>
        </div>
      </div>
      </div>
    </div>
    <div class="container theme-showcase">
      <div class="page-header">
      <br>
       <h1>Billing Info</h1>
      </div>
    <table class="table">
        <thead>
            <tr>
                <th>Billing ID</th>
                 <th>User ID</th>
                 <th>Vm ID</th>
                 <th>Vm Name</th>
                <th>Price ($)</th>
                <th>Tax</th>
                <th>CPU Utilization($)</th>
                <th>Network Utilization($)</th>
                <th>Disk Utilization($)</th>
                <th>Memory Utilization($)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${Billings}" var="billing">
                <tr>             
                    <td><c:out value="${billing.billId}" /></td>
                    <td><c:out value="${billing.userId}" /></td>
                    <td><c:out value="${billing.vmId}" /></td>
                    <td><c:out value="${billing.vmName}" /></td>
                    <td><c:out value="${billing.billAmt}" /></td>
                    <td><c:out value="${billing.billAmt*.1}" /></td>
                    <td><c:out value="${billing.billAmt*.3}" /></td>
                    <td><c:out value="${billing.billAmt*.4}" /></td>
                    <td><c:out value="${billing.billAmt*.15}" /></td>
                    <td><c:out value="${billing.billAmt*.05}" /></td>
                 <td> 
                 <%
 					//if (((User)session.getAttribute("currentSessionUser")).getUserId() == ((Billing)pageContext.getAttribute("billing")).getUserid()) {
 				 %> 
                    <a href="#">Pay Now</a> 
                 <% //} %> 
                  </td>  
                        
                </tr>
                
                
            </c:forEach>
            
            
            
   		
        </tbody>
    </table>
    <a href = "http://localhost:8080/Cmpe281_Project/login">Back</a>
    </div>
      
      <br>
</body>
</html>