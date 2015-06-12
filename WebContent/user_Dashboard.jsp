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
    <title>DashBoard</title>
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
            <li class="active"><a href="ProjectController?action=listProject">Virtual Machines</a></li>            
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
       <h1>DashBoard</h1>
      </div>
    <table class="table">
        <thead>
            <tr>
                <th>VM ID</th>
                <th>Requested VM</th>
                <th>Instance Taken(1-Yes | 0- No)</th>
                <th>Start Time</th>
                <th colspan=3>Action</th>
            </tr>
        </thead>
        <tbody>
    
            <c:forEach var="Vm" items="${VmDatas}" >
                <tr>
                    <td><c:out value="${Vm.instance_ID}" /></td>
                    <td><a href="VmController?action=detail&vmId=<c:out value="${Vm.instance_ID}"/>"><c:out value="${Vm.instance_Name}" /></a></td>
                    <td><c:out value="${Vm.instance_Taken}" /></td>
                    <td><c:out value="${Vm.startTime}" /></td>
                    <td><a href="VmController?action=delete&vmId=<c:out value="${Vm.instance_ID}"/>">Delete</a></td>
                    <c:if test="${Vm.instance_Taken == '1'}">
                    <td><a href="VmController?action=stop&vmId=<c:out value="${Vm.instance_ID}"/>">Release</a></td>
                    </c:if>
                    <td>
                    <c:choose>
    					<c:when test="${Vm.instanceStatus == '1'}">
    						<a href="VmController?action=stopInstance&vmId=<c:out value="${Vm.instance_ID}"/>">Stop</a>
    					</c:when>
    					<c:otherwise>
      						<a href="VmController?action=startInstance&vmId=<c:out value="${Vm.instance_ID}"/>">Start</a>
    					</c:otherwise>
 					</c:choose>
 					</td>
 					<td><a href="VmController?action=reboot&vmId=<c:out value="${Vm.instance_ID}"/>">Reboot</a></td>
 					<td><a href="VmController?action=snapshot&vmId=<c:out value="${Vm.instance_ID}"/>&vmName=<c:out value="${Vm.instance_Name}" />">Snapshot</a></td>
                	<td><a href="https://192.168.49.129/admin/instances/<c:out value="${Vm.instance_ID}"/>/detail?tab=instance_details__console"> Console</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="VmController?action=create">Create Instance</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="VmController?action=allocate">Allocate Instance</a>
    </p>
    </div>
</body>
</html>