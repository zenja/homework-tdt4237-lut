<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sql:transaction dataSource="jdbc/lut2">
	<sql:update var="count">
    INSERT INTO school(short_name, full_name, place, zip, country) VALUES ('${param.short_name}', '${param.full_name}', '${param.place}', '${param.zip}', '${param.country}');
    </sql:update>
</sql:transaction>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="lutstyle.css">
<title>School info added!</title>
</head>
<body>
	<h1>School info added!</h1>
	<p>
		Short name: ${param.short_name} <br> Full name:
		${param.full_name} <br> Place: ${param.place} <br> Zip:
		${param.zip} <br> Country: ${param.country} <br>
	</p>
</body>
</html>
