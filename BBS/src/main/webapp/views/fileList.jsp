<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="file.FileDAO" %>
<%@ page import="file.FileDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>

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
	int pageNumber = 1;
	if(request.getParameter("pageNumber") != null){
		pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
	}
	%>
	<nav class="navebar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" 
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-bollapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<li><a href="bbs.jsp">게시판</a></li>
				<li><a href="upload.jsp">파일업로드</a></li>
				<li class="active"><a href="fileList.jsp">파일목록</a></li>
			</ul>
			<%
				if(userID==null){ //비로그인
			%> 
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-hashpopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
						</ul>
				</li>
			</ul>
			<%
				}else{ //로그인
					%>
				<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-hashpopup="true"
					aria-expanded="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="../actions/logoutAction.jsp">로그아웃</a></li>
						</ul>
				</li>
			</ul>	
			<%
				}
			%>
		</div>
	</nav>
	
	<%-- 파일목록 --%>
	<div class ="container">
		<a href="upload.jsp">[파일 업로드]</a>
		<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd; ">
			<thead>
				<tr>
					<th style="background-color: #eeeeee; text-align: center;">번호</th>
					<th style="background-color: #eeeeee; text-align: center;">파일명</th>
					<th style="background-color: #eeeeee; text-align: center;">저장된 파일명</th>
					<th style="background-color: #eeeeee; text-align: center;">작성일</th>
					<th style="background-color: #eeeeee; text-align: center;">다운로드</th>
				</tr>
			</thead>
			<tbody>
				<%					
					FileDAO fileDAO = new FileDAO();
					ArrayList<FileDTO> list = fileDAO.getList(pageNumber);
					for(int i =0; i < list.size(); i++){
				%>
					<tr>
					<td><%= list.get(i).getFileID() %></td>
					<td><%= list.get(i).getFileName() %></td>
					<td><%= list.get(i).getSysName() %></td>  
					<td><%= list.get(i).getPostDate() %></td>
					<td><a href="<%= request.getContextPath() %>/downloadAction?file=<%= java.net.URLEncoder.encode(list.get(i).getFileName(), "UTF-8") %>&sys=<%= java.net.URLEncoder.encode(list.get(i).getSysName(), "UTF-8") %>">[다운로드]</a></td> 
				</tr>
				<% 
					}
				%>
			</tbody>
		</table>
		
		<%-- 페이징 처리 --%>
		<%
			if(pageNumber != 1){
		%>
			<a href="fileList.jsp?pageNumber=<%=pageNumber-1 %>"
				class="btn btn-success btn-arraw-left">이전</a>
		<%
			}if(fileDAO.nextPage(pageNumber + 1)){
		%>
			<a href="fileList.jsp?pageNumber=<%=pageNumber+1 %>"
				class="btn btn-success btn-arraw-left">다음</a>
		<%
			}
		%>
	
	</div>
	
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.js"></script>
</body>
</html>