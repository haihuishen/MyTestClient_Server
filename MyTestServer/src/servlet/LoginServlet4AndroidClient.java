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
@WebServlet("/login.do")     // 这是映射地址： 可以修改     // 以前是在 web.xml配置的，这是3.0新写法
public class LoginServlet4AndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--get--");
		this.doPost(request, response);

	}

	/**
	 * URL地址?参数名=参数值&参数名=参数值&参数名=参数值<br>
	 * http://localhost:8080/MyTestServer/login.do?LoginName=shen&LoginPassword=123456
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--post--");

		request.setCharacterEncoding("UTF-8");			// 处理请求对象的"字符集"，以防止乱码
		

		/**
		 * 取值 
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
		response.setContentType("text/html;charset=UTF-8");		// 设置返回响应的"字符集"

		/**
		 * 返回值<br>
		 * PrintWriter out = response.getWriter();<br>
		 * 可以向"客户端(浏览器/android)"输出字符串<br>
		 */
		PrintWriter out = null;	
		
		
		/***
		 * 字符串
		 */
		try {
			out = response.getWriter();		// 可以向"客户端(浏览器/android)"输出字符串
			/**
			 * 登录的业务判断
			 */
			if (loginName.equals("shen") && loginPassword.equals("123456")) {
				// 登录正确
				out.print("success");
			} else {
				// 登录不正确
				out.print("failed");
			}
		} finally {							// 完成了就关闭
			if (out != null) {
				out.close();			
			}
		}

	}

}
