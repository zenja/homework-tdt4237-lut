
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%-- 
<sql:query var="users" dataSource="jdbc/lut2">
    SELECT * FROM admin_users
    WHERE  uname = ? <sql:param value="${param.username}" /> 
    AND pw = ${param.password}
</sql:query>
--%>
    
    
<c:set var="userDetails" value="${users.rows[0]}"/>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="lutstyle.css">
        <title>LUT Admin pages</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${ empty userDetails }">
                Login failed<br>
                <a href="lutadmin.jsp">back</a>
            </c:when>
            <c:otherwise>
                <h1>Login succeeded</h1> 
                Welcome ${ userDetails.uname}.<br> 
                <c:redirect url="admin/admin_console.jsp"/>
            </c:otherwise>
        </c:choose>
        </body>
    </html>
