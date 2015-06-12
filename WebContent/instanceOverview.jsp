<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Instance Overview</title>
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
            <li class="active"><a href="VmController?action=listVm">Virtual Machines</a></li>            
            <li><a href="Hubs?action=listHub">Hub</a></li>
            <li><a href="Devices?action=listDevice">Device</a></li>
            <li><a href="Users?action=listUser">User</a></li>
            <li><a href="BillingController?action=listBilling">Billing</a></li>
          </ul>
          <div class="navbar-form navbar-right">
          
          <p style="color:white;">Welcome, <c:out value="${currentSessionUser.username}" />
            <button  class="btn btn-success" onclick="location.href='/Cmpe281_Project/login?action=logout'">Logout</button>
          </p>
        </div>
        </div>
      </div>
    </div>
    
   <div class="container theme-showcase" >
 
     <div class="page-header">
      <br>
       <h1>Instance overview</h1>
      </div>
     <fieldset>
      <c:forEach var="Vm" items="${VmDatas}" >
      	<div class="control-group">
            <label class="control-label">Instance Name</label>
            <div class="controls">
            	<input class="input-xlarge uneditable-input" type="text" readonly="readonly" value = "<c:out value="${Vm.instance_Name}" />">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="focusedInput">Instance ID</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "<c:out value="${Vm.instance_ID}" />">
            </div>
        </div>
        
        <div class="control-group">
            <label class="control-label" for="focusedInput">Instance Status</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "Active">
            </div>
        </div>
        
        <div class="control-group">
            <label class="control-label" for="focusedInput">Instance Created</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "<c:out value="${Vm.startTime}" />">
            </div>
        </div>     
        
        <div class="control-group">
            <label class="control-label" for="focusedInput">RAM</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "<c:out value="${Vm.ram}" />">
            </div>
        </div>
        
        <div class="control-group">
            <label class="control-label" for="focusedInput">Disk</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "<c:out value="${Vm.diskSize}" />">
            </div>
        </div>
        
        <div class="control-group">
            <label class="control-label" for="focusedInput">IP Address</label>
            <div class="controls">
              <input class="input-xlarge uneditable-input" id="focusedInput" type="text" readonly="readonly" value = "192.168.49.129">
            </div>
        </div>
        
        <br>
		<div class="form-actions">
            <input class="btn btn-primary" type="button" value="Back" onClick="history.go(-1);return true;">
        </div>
      </c:forEach>   
     </fieldset>
    </div>
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
     <script src="http://getbootstrap.com/assets/js/docs.min.js"></script>
</body>
</html>