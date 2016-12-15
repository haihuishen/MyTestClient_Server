package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DownloadServlet
 * URL: http://localhost:8080/MyTestServer/download.do
 * 
 * @author shen
 * 
 */
@WebServlet("/download.do")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		InputStream in = null;
		OutputStream out = null;

		try {
			File file = new File("D:\\��ϵ��ʽ.jpg");
			// װ�������ģʽ
			// FilterInputStream
			in = new BufferedInputStream(new FileInputStream(file));
			// ������Ӧͷ��Ϣ���ļ�����
			response.setContentType("application/octet-stream");
			// ������Ӧͷ��Ϣ���ļ����ĳ���
			response.setContentLength((int)file.length());
			// ��ҳ��������г������أ�Android���������
//			response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
			// �����
			out = new BufferedOutputStream(response.getOutputStream());

		
			byte[] b = new byte[10240];

			/**
			 * �ַ���û��ϵ��trim()
			 */
//			while (in.read(b) != -1) {
//				out.write(b);
//			}
			
			/**
			 * �淶�ı���
			 */
			int read = 0;
			while((read = in.read(b)) != -1) {
				out.write(b, 0, read);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}

			if (out != null) {
				out.close();
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
