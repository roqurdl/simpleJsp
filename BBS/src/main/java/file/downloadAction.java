package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fileName = request.getParameter("file");
		String saveName = request.getParameter("sys");
		String directory = this.getServletContext().getRealPath("/upload");
		
		//파일 찾아오기.
		File file = new File(directory +"/"+saveName);
		
		//Content-type 확인.
		String mimeType = getServletContext().getMimeType(file.toString());
		
		if(mimeType == null) {
			response.setContentType("application/octet-stream");
		}
		
		//한글 파일명 깨짐 방지
		String downloadName = null;
		if(request.getHeader("user-agent").indexOf("WOW64") == -1) {
			downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");
		}else {
			//IE일 떄
			downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1");
		}
		
		//다운로드시 파일명 지정.
		response.setHeader("Content-Disposition", "attachment;filename=\""+downloadName+"\";");
		
		//스트림생성
		FileInputStream fileInputStream =new FileInputStream(file); //입력
		ServletOutputStream servletOutputStream = response.getOutputStream();//출력
		
		//출력스트림에 파일내용 출력.
		byte b[] = new byte[1024]; //쪼개는 단위 1KB
		int data = 0;
		while((data = (fileInputStream.read(b, 0, b.length))) != -1) {
			servletOutputStream.write(b,0,data);
		}
		
		servletOutputStream.flush(); //남이있는 데이터 모두 보냄.
		servletOutputStream.close();//출력 스트림 닫기.
		fileInputStream.close();// 입력 스트림 닫기
		
		}
	}

