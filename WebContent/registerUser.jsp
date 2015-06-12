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
    <title>User</title>
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
       		<h1>Add New User</h1>
      	</div>
    	<form method="POST" action='Users' name="formAddUser" class="form-horizontal">
    		<fieldset>
      			<div class="control-group">
            		<label class="control-label">User ID</label>
            		<div class="controls">
            			<input class="input-xlarge uneditable-input" type="text" readonly="readonly" name="userId"
             				value="<c:out value="${currentSessionUser.userId}" />" >
            		</div>
          		</div>
    	  		<div class="control-group">
            		<label class="control-label" for="focusedInput">Username</label>
            		<div class="controls">
              			<input class="input-xlarge focused" id="focusedInput" type="text" name="username"
            				value="<c:out value="${currentSessionUser.username}" />">
            		</div>
          		</div> 
            	<div class="control-group">
            		<label class="control-label" for="focusedInput">Password</label>
            		<div class="controls">
              			<input class="input-xlarge focused" id="focusedInput" type="password" name="password"
            				value="<c:out value="${currentSessionUser.password}" />">
            		</div>
          		</div>
            
        		<br>
            	<div class="form-actions">
            		<button type="submit" class="btn btn-primary" id="submit_button" >Submit</button>
            		<input class="btn btn-primary" type="button" value="Back" onClick="history.go(-1);return true;">
          		</div>
       		</fieldset>
    	</form>
    </div>
</body>
</html>