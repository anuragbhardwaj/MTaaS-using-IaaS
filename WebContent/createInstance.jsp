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
    <title>Create Instance</title>
      <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
      <link href="starter-template.css" rel="stylesheet">     
</head>
<body >  
 <div class="navbar navbar-inverse navbar-fixed-top" >
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
            <li class="active"><a href="VmController?action=listVm">Virtual Machine</a></li>
            <li><a href="Hubs?action=listHub">Hub</a></li>
            <li><a href="Devices?action=listDevice">Device</a></li>
            <li><a href="Users?action=listUser">User</a></li>
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
       <h1>Create Instance</h1>
      </div>
    <form method="POST" action='VmController' name="frmAddProject" class="form-horizontal">
     <fieldset>
      	
        <div class="control-group">
            <label class="control-label" for="focusedInput">Instance Name</label>
            <div class="controls">
            	<input class="input-xlarge" type="text" name = "instanceName"/>
            </div>
        </div>
            
           
        <div class="control-group success">
            <label class="control-label">RAM</label>
			<div class="controls">
         	  <select name="ramSize" id="ram_size">
					<option value="512MB" name="ramSize">512MB</option>
					<option value="1024MB" name="ramSize">1024MB</option>
				</select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="focusedInput">Disk Size</label> (GB)
            <div class="controls">
              <select name="diskSize" id="disk_size">
					<option value="5GB" name = "diskSize">5GB</option>
					<option value="10GB" name = "diskSize">10GB</option>
				</select>
				<br>
				<br>
            </div>
        </div>
       
            <div class="form-actions">
            <button type="submit" class="btn btn-primary">Submit</button>
            <input class="btn btn-primary" type="button" value="Back" onClick="history.go(-1);return true;">
        </div>
     </fieldset>
    </form>
    </div>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://getbootstrap.com/dist/js/bootstrap.min.js"></script>
     <script src="http://getbootstrap.com/assets/js/docs.min.js"></script>
</body>
</html>