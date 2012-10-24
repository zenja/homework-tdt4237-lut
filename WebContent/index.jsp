<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<sql:query var="country" dataSource="jdbc/lut2">
    SELECT full_name FROM country
</sql:query>


<%@page import="core.UserSession"	
%>

<% 
	UserSession  user = (UserSession) session.getAttribute("currentUser");
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="lutstyle.css">
        <title>LUT 2.0 - Help Students Conquer the World</title>
    </head>
    <body>
        <h1>Hi student!</h1>
        <table border="0">
            <thead>
                <tr>
                    <th>LUT 2.0 provides information about approved international schools</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>To view information about schools in a country, please select a country below:</td>
                </tr>
                <tr>
                    <td><form action="schools.jsp">
                            <strong>Select a country:</strong>
                            <select name="country">
                                <c:forEach var="row" items="${country.rowsByIndex}">
                                    <c:forEach var="column" items="${row}">
                                        <option value="<c:out value="${column}"/>"><c:out value="${column}"/></option>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                            <input type="submit" value="submit" />
                        </form></td>
                </tr>
            </tbody>
        </table>
        <br/>
        <%
        	if(user == null || !user.isLoggedIn() ){
        		%>
        		<h1>Login</h1>
                <table border="0">
                    <thead>
                        <tr>
                            <th>Log on here to be logged in</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                            <form method="post" action="UserLoginServlet">
                                    <p>Username:</font><input type="text" name="username_j" size="20"></p>
                                    <p>Password:</font><input type="password" name="password_j" size="20"></p>
                                    <p><input type="submit" value="submit" name="login"></p>
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br/>
                
        		<h1>User Registration</h1>
                <table border="0">
                    <thead>
                        <tr>
                            <th>registrate user</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                            <form method="post" action="UserRegistrationServlet">
                                    <p>Username:</font><input type="text" name="username_j" size="20"></p>
                                    <p>Password:</font><input type="password" name="password_j" size="20"></p>
                                    <p>Security Question:</font><input type="text" name="question" size="20"></p>
                                    <p>Answer:</font><input type="text" name="answer" size="20"></p>
                                    <p><input type="submit" value="submit" name="login"></p>
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
            		<%
    			out.print("<p>Not logged in</p>");
    		}else if (user.isLoggedIn()) {
    			out.print("<p>Logged in as " + user.getUsername() + "</p>");
    			out.print("<a href=logout.jsp>logout</a>");
    		}
        %>
    </body>
</html>
