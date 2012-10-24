<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Failed</title>
</head>
<body>

<p>
Sorry, your have to login.
</p>

<form method="post" action="../UserLoginServlet">
	Username:<input type="text" name="username_j" size="20"><br>
	Password:<input type="password" name="password_j" size="20"><br>
	<input type="submit" value="submit" name="login"><br>
</form>

</body>
</html>