<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="ISO-8859-1">
<title>Translate Success</title>
</head>
<body>
	<%
	String content = (String) request.getAttribute("error");
	String context = (String) request.getAttribute("uploaded");
	%>
	<%
	if (content != null && context != null) {
	%>
	<h1 style="color: gray; text-align: center;"><%=content%><br><%=context%>
	</h1>
	<%
	} else {
	%>
	<h1></h1>
	<h3 style="color: navy; text-align: center;"><%=context%></h3>
	<%
	}
	%>


</body>
</html>