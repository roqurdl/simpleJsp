<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="bbs.Bbs" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="veiwport" content="width=device-width">
<link rel="stylesheet" href="../css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
<style type="text/css">
	a, a:hover {
		color: #000000;
		text-decoration: none;
	}
</style>
</head>
<body>
	<%
	//login이 된 user를 식별.
	String userID = null;
	if(session.getAttribute("userID") != null){
		userID = (String) session.getAttribute("userID");
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
				<li class="active"><a href="bbs.jsp">게시판</a></li>
				<li><a href="upload.jsp">파일업로드</a></li>
				<li><a href="fileList.jsp">파일목록</a></li>
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
	
	<%-- 게시글 목록 --%>
	<div class ="container">
		<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd; ">
			<thead>
				<tr>
					<th style="background-color: #eeeeee; text-align: center;"><a href="order.jsp?columnName=bbsID&sortOrder=0">번호</a></th> <!-- bbsID -->
					<th style="background-color: #eeeeee; text-align: center;"><a href="order.jsp?columnName=bbsTitle&sortOrder=0">제목</a></th> <!-- bbsTitle -->
					<th style="background-color: #eeeeee; text-align: center;"><a href="order.jsp?columnName=userID&sortOrder=0">작성자</a></th> <!-- userID -->
					<th style="background-color: #eeeeee; text-align: center;"><a href="order.jsp?columnName=bbsDate&sortOrder=0">작성일</a></th> <!-- bbsDate -->
				</tr>
			</thead>
			<tbody>
				<%
					BbsDAO bbsDAO = new BbsDAO();
					ArrayList<Bbs> list =bbsDAO.getSearch(request.getParameter("searchField"),request.getParameter("searchText"));
					if (list.size() == 0) {
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('검색결과가 없습니다.')");
						script.println("history.back()");
						script.println("</script>");
					 }
					for(int i =0; i < list.size(); i++){
				%>
				<tr>
					<td><%= list.get(i).getBbsID() %></td>
					<!-- 제목을 통해 Content를 확인. -->
					<td><a href="veiw.jsp?bbsID=<%= list.get(i).getBbsID()%>"><%= list.get(i).getBbsTitle() %></a></td>
					<td><%= list.get(i).getUserID() %></td>
					<td><%= list.get(i).getBbsDate().substring(0,11) + list.get(i).getBbsDate().substring(11,13)+ "시" + list.get(i).getBbsDate().substring(14,16) + "분" %></td>  
				</tr>
				<% 
					}
				%>
			</tbody>
		</table>

		<div class= container>
			<form method="get" name="search" action="searchBbs.jsp">
				<table class="table table-striped">
					<tr>
						<td>
							<select class="form-control" name="searchField">
									<option value="0">선택</option>
									<option value="bbsTitle">제목</option>
									<option value="userID">작성자</option>
							</select>
						</td>
						<td>
						<input type="text" class="form-control" placeholder="검색어 입력" name="searchText" maxlength="100" value=<%=request.getParameter("searchText") %>>
						</td>
						<td><button type="submit" class="btn btn-success">검색</button></td>
						<td><a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div>
			
		</div>
		<%--
			PrintWriter script = response.getWriter();
			script.println("<div>");
			script.println("검색 내용: "+request.getParameter("searchText"));
			script.println("<div>");
		--%>
		
	</div>
	
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="../js/bootstrap.js"></script>
</body>
</html>