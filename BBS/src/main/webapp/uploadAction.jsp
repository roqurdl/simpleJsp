<%@ page import="java.io.File" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="file.FileDAO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="veiwport" content="width=device-width">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
</head>
<body>
	<%
		String directory = application.getRealPath("/upload");
		int maxSize = 1024*1024*100; //100MB 업로드 상한.
		String encoding = "UTF-8";
		MultipartRequest multi = new MultipartRequest(request, directory, maxSize, encoding, new DefaultFileRenamePolicy());
		
		String fileName = multi.getOriginalFileName("file");
		String sysName = multi.getFilesystemName("file");
		
		//파일이 있는 경우를 체크
		if(fileName != null && sysName !=null){
			//업로드 형식 제한 제거//
			//if(!fileName.endsWith(".hwp") && !fileName.endsWith(".jpg")){
			//	File file = new File(directory+sysName);
			//	file.delete();
			//	PrintWriter script = response.getWriter();
			//	script.println("<script>");
			//	script.p"C:/Users/user/Desktop/주요정보통신기반시설_웹 28가지.pdf"rintln("alert('업로드 불가한 형식입니다.')");
			//	script.println("history.back()");
			//	script.println("</script>"); 
			//}else{
				//DB에 업로드
				new FileDAO().upload(fileName, sysName);
				response.sendRedirect("fileList.jsp");
			//}
		}else{
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('업로드할 파일이 없습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}
		%>
</body>
</html>