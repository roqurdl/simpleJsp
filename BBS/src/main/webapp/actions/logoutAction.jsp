<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="veiwport" content="width=device-width">
<link rel="stylesheet" href="css/bootsstrap.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
	session.invalidate();
	%>
	<script>
		location.href = '../views/main.jsp';
	</script>
</body>
</html>