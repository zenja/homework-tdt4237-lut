<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Management</title>
</head>
<body>

<h1>User Management</h1>
<h2>User: ${ param.uname }</h2>

<form action="deleteUser" method="POST" >
	<fieldset>
		<legend>Delete User</legend>
		<input type="hidden" name="uname" value="${ param.uname }">
		<input type="submit" value="Delete">
	</fieldset>
</form>

<form action="renameUser" method="POST" >
	<fieldset>
		<legend>Rename User</legend>
		<input type="hidden" name="uname" value="${ param.uname }">
		New username: <input type="text" name="new_uname"><br>
		<input type="submit" value="Rename">
	</fieldset>
</form>

<form action="modifyPassword" method="POST" >
	<fieldset>
		<legend>Modify User Password</legend>
		<input type="hidden" name="uname" value="${ param.uname }">
		New password: <input type="text" name="new_password"><br>
		<input type="submit" value="Modify">
	</fieldset>
</form>

</body>
</html>