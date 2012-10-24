<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="lutstyle.css">
<title>Country info added!</title>
</head>
<body>
	<h1>Country info added!</h1>
	<p>
		Short name: ${param.short_name} <br>
		Full name: ${param.full_name} <br>
	</p>
</body>
</html>
