package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetImageServlet
 * URL: http://localhost:8080/MyTestServer/getImage.jpeg
 * 
 * @author shen
 * 
 */
@WebServlet("/getImage.jpeg")					// ����ӳ���ַ�� �����޸�     // ��ǰ���� web.xml���õģ�����3.0��д��
public class GetImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetImageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("---get----");
		
		
		String id = request.getParameter("id");

		// ������
		InputStream in = null;
		// �����
		OutputStream out = null;

		try {
			// �ļ�������
			in = new FileInputStream(new File("D://" + id + ".jpg"));
			// ������Ӧͷ��������Ӧ���ݵĳ��� -- �ͻ�����Ҫ֪���������ݵĳ���
			response.setContentLength((int) in.available());	// �����out.write���ܳ���
			// ������Ӧͷ�����߿ͻ��������Ӧ�������ݵ�����
			response.setContentType("image/jpeg");				// �ͻ��˸���ִ�У���Ϊ"������"֮ǰ��֪����

			// ��Ӧ�����
			out = response.getOutputStream();

			/**
			 * ����һ���ô���
			 * byte[] b = new byte[in.available()];
			 * ���"ͼƬ"5M �Ǿ�Ҫnew�� 5M��byte[],����
			 */
			// byte[] b = new byte[in.available()];
			// in.read(b);
			// out.write(b);

			// byte[] b = new byte[1024];
			// while (in.read(b) != -1) {
			// out.write(b);
			// }

			byte[] b = new byte[1024];
			int read = 0;
			while ((read = in.read(b)) != -1) {		// ����� -1
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

}