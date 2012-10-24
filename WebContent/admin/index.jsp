<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="core.UserSession" %>

<sql:query var="countries" dataSource="jdbc/lut2">
    SELECT * FROM country
</sql:query>

<%
	/* check if already logined,
	and the role is admin user */
	UserSession user = (UserSession)(session.getAttribute("currentUser"));
	if (user == null || user.isAdminRole() == false) {
		response.sendRedirect(request.getContextPath() +  "/admin/login.jsp");
	}
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../lutstyle.css">
<link rel="stylesheet" type="text/css" href="../css/admin_console.css">
<title>LUT Admin Console</title>
</head>
<body>
	<form action="add_country.jsp" method="POST">
		<fieldset>
			<legend>Add Country</legend>
			<label>Short Name:</label> <input type="text" name="short_name"><br>
			<label>Full Name:</label> <input type="text" name="full_name"><br>
			<input type="submit">
		</fieldset>
	</form>

	<form action="add_school.jsp" method="POST">
		<fieldset>
			<legend>Add School</legend>
			<label>Short Name:</label> <input type="text" name="short_name"><br>
			<label>Full Name:</label> <input type="text" name="full_name"><br>
			<label>Place:</label> <input type="text" name="place"><br>
			<label>Zip:</label> <input type="text" name="zip"><br> <label>Country:</label>
			<select name="country">
				<c:set var="country" value="${countries.rows[0]}" />
				<c:forEach var="country" items="${countries.rowsByIndex}">
					<option value='<c:out value="${country[0]}" />'>
						<c:out value="${country[1]}" />
					</option>
				</c:forEach>
			</select><br> <input type="submit">
		</fieldset>
	</form>

</body>
</html>
