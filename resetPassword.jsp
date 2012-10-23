
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
        <title>Login page</title>
    </head>
    <body>
        <h1> Username or/and Password incorrect </h1>
        <a href="index.jsp">Try again</a>
        <h1>Reset Password</h1>
                <table border="0">
                    <thead>
                        <tr>
                            <th>reset password</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                            <form method="post" action="ResetPasswordServlet">
                                    <p>Username: </font><% out.print(user.getUsername()); %></p>
                                	<input type="hidden" name="username_j" value=<% out.print('"'+user.getUsername()+'"'); %> />
                                    <p>New Password:</font><input type="password" name="password_j" size="20"></p>
                                    <p>Security Question:</font><% out.print(user.getQuestion()); %></p>
                                    <p>Answer:</font><input type="text" name="answer" size="20"></p>
                                    <p><input type="submit" value="submit" name="login"></p>
                                </form>
                            </td>
                        </tr>
                    </tbody>
                </table>
        </body>
    </html>
