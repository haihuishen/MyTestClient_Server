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
@WebServlet("/getImage.jpeg")					// 这是映射地址： 可以修改     // 以前是在 web.xml配置的，这是3.0新写法
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

		// 输入流
		InputStream in = null;
		// 输出流
		OutputStream out = null;

		try {
			// 文件输入流
			in = new FileInputStream(new File("D://" + id + ".jpg"));
			// 设置响应头，设置响应内容的长度 -- 客户端需要知道返回内容的长度
			response.setContentLength((int) in.available());	// 下面的out.write的总长度
			// 设置响应头，告诉客户端这次响应返回数据的类型
			response.setContentType("image/jpeg");				// 客户端更快执行，因为"读完流"之前就知道了

			// 响应输出流
			out = response.getOutputStream();

			/**
			 * 不是一个好代码
			 * byte[] b = new byte[in.available()];
			 * 如果"图片"5M 那就要new个 5M的byte[],不好
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
			while ((read = in.read(b)) != -1) {		// 读完后 -1
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