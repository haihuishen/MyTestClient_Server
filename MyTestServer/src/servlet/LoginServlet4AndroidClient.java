package servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * URL: http://localhost:8080/MyTestServer/login.do
 * 
 * @author shen
 * 
 */
@WebServlet("/login.do")     // ����ӳ���ַ�� �����޸�     // ��ǰ���� web.xml���õģ�����3.0��д��
public class LoginServlet4AndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--get--");
		this.doPost(request, response);

	}

	/**
	 * URL��ַ?������=����ֵ&������=����ֵ&������=����ֵ<br>
	 * http://localhost:8080/MyTestServer/login.do?LoginName=shen&LoginPassword=123456
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--post--");

		request.setCharacterEncoding("UTF-8");			// ������������"�ַ���"���Է�ֹ����
		

		/**
		 * ȡֵ 
		 */
		String loginName = request.getParameter("LoginName");
		String loginPassword = request.getParameter("LoginPassword");

		System.out.println(loginName);
		System.out.println(loginPassword);

		response.setCharacterEncoding("UTF-8");
		
		
		//		try {
		//			Thread.sleep(5000);
		//		} catch (InterruptedException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		/**
		 * text/html
		 */
		response.setContentType("text/html;charset=UTF-8");		// ���÷�����Ӧ��"�ַ���"

		/**
		 * ����ֵ<br>
		 * PrintWriter out = response.getWriter();<br>
		 * ������"�ͻ���(�����/android)"����ַ���<br>
		 */
		PrintWriter out = null;	
		
		
		/***
		 * �ַ���
		 */
		try {
			out = response.getWriter();		// ������"�ͻ���(�����/android)"����ַ���
			/**
			 * ��¼��ҵ���ж�
			 */
			if (loginName.equals("shen") && loginPassword.equals("123456")) {
				// ��¼��ȷ
				out.print("success");
			} else {
				// ��¼����ȷ
				out.print("failed");
			}
		} finally {							// ����˾͹ر�
			if (out != null) {
				out.close();			
			}
		}

	}

}
