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
		
		//���� ã�ƿ���.
		File file = new File(directory +"/"+saveName);
		
		//Content-type Ȯ��.
		String mimeType = getServletContext().getMimeType(file.toString());
		
		if(mimeType == null) {
			response.setContentType("application/octet-stream");
		}
		
		//�ѱ� ���ϸ� ���� ����
		String downloadName = null;
		if(request.getHeader("user-agent").indexOf("WOW64") == -1) {
			downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");
		}else {
			//IE�� ��
			downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1");
		}
		
		//�ٿ�ε�� ���ϸ� ����.
		response.setHeader("Content-Disposition", "attachment;filename=\""+downloadName+"\";");
		
		//��Ʈ������
		FileInputStream fileInputStream =new FileInputStream(file); //�Է�
		ServletOutputStream servletOutputStream = response.getOutputStream();//���
		
		//��½�Ʈ���� ���ϳ��� ���.
		byte b[] = new byte[1024]; //�ɰ��� ���� 1KB
		int data = 0;
		while((data = (fileInputStream.read(b, 0, b.length))) != -1) {
			servletOutputStream.write(b,0,data);
		}
		
		servletOutputStream.flush(); //�����ִ� ������ ��� ����.
		servletOutputStream.close();//��� ��Ʈ�� �ݱ�.
		fileInputStream.close();// �Է� ��Ʈ�� �ݱ�
		
		}
	}

