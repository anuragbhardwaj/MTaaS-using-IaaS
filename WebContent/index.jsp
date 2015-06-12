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
    <title>Login</title>
      <link href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">
      <link href="http://getbootstrap.com/examples/signin/signin.css" rel="stylesheet">
</head>
<body>  

 <div class="container">
      <form class="form-signin" method="POST" action='login' name="formLogin">
        <h2 class="form-signin-heading">User Sign In</h2>
        <input type="text" class="form-control" placeholder="Username"  required name="username" 
        	value="<c:out value="${user.username}" />" /> ${err}
        <input type="password" class="form-control" placeholder="Password"  name="password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
      </form>
      <div class="form-signin">
      	<button class="btn btn-lg btn-primary btn-block" onclick="location.href='/CMPE281_Proj/Users?action=insert'">Sign up</button>
      </div>
    </div> 
</body>
</html>