<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id ="user" class="user.User" scope="page"/>
<jsp:setProperty name="user" property="userID"/>
<jsp:setProperty name="user" property="userPassword"/>
<jsp:setProperty name="user" property="userName"/>
<jsp:setProperty name="user" property="userGender"/>
<jsp:setProperty name="user" property="userEmail"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="veiwport" content="width=device-width">
<link rel="stylesheet" href="../css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
<%
	//login이 된 user를 식별.
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
	}
	if(userID != null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('Already Logged-IN')");
		script.println("location.href = '../views/main.jsp'");
		script.println("</script>");
	} 
	//join
	if (user.getUserID() == null || user.getUserPassword() == null|| user.getUserName() == null || user.getUserEmail() == null || user.getUserGender() == null){
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력되지 않은 값이 존재합니다.')");
		script.println("history.back()");
		script.println("</script>");
	}else{
		UserDAO userDAO = new UserDAO();
		int result = userDAO.join(user);
		if(result == -1){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('ID is already exists!')");
			script.println("history.back()"); // 이전 창으로 돌아감.
			script.println("</script>");
			}
		else{
			session.setAttribute("userID",user.getUserID());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = '../views/main.jsp'");
			//script.println("location.href = '../views/login.jsp'");
			//script.println("alert('로그인 해주세요.')");  이 상황에서는 SessionID도 주석처리필요. test시 편의성으로 다음 과정을 생략.
			script.println("</script>");
		}
	}
%>
</body>
</html>