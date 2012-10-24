
<%
	session.removeAttribute("currentUser");
	response.sendRedirect("index.jsp");
%>