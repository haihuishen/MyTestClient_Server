package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * URL: http://localhost:8080/MyTestServer/register.do
 * 
 * @author shen
 * 
 */
@WebServlet("/register.do")			 // 这是映射地址： 可以修改     // 以前是在 web.xml配置的，这是3.0新写法
public class RegisterServelt4AndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		/**
		 * Data={"LoginName" : "shen", "Interesting" : ["game", "music", "sports"]}
		 */
		String data = request.getParameter("Data");
		System.out.println(data);

		
		/***** JSON数据解析，剥离 ****/
		/**
		 * json-lib解析JSON数据的核心代码
		 * 
		 */
		JSONObject object = JSONObject.fromObject(data);				// 因为最外面的是"Object"

		String loginName = object.getString("LoginName");
		System.out.println("注册的登录名是：" + loginName);

		JSONArray interesing = object.getJSONArray("Interesting");
		System.out.println("注册的兴趣爱好有：");
		if (interesing != null) {
			for (Object obj : interesing) {
				System.out.print(obj.toString() + "\t");
			}
		}
		System.out.println();
		/***** JSON数据解析完成****/

		/**
		 * 做你的业务处理：
		 * 如将，解析的数据，插入"数据库"
		 */

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");		// 设置返回响应的"字符集"

		/**
		 * 返回值<br>
		 * PrintWriter out = response.getWriter();<br>
		 * 可以向"客户端(浏览器/android)"输出字符串<br>
		 */
		PrintWriter out = null;	

		try {
			out = response.getWriter();

			/**
			 * JSON的数据封装
			 * 
			 *  响应： 封装JSON结果 { "result" : "success", "errorMsg" : "" }
			 * { "result" : "failed", "errorMsg" : "服务器处理错误，注册失败。" }
			 */
			ResultJSONBean jsonBean = new ResultJSONBean();
			// 如果插入数据库"成功"
			 jsonBean.setResult("success");
			 jsonBean.setErrorMsg("");

			// 如果插入数据库"失败"
			// jsonBean.setResult("failed");
			// jsonBean.setErrorMsg("服务器处理错误，注册失败。");

			JSONObject obj = JSONObject.fromObject(jsonBean);		// 将 JAVABean封装成 "json"

			System.out.println(obj.toString());

			out.print(obj.toString());								// 在"客户端(浏览器/android)"打印(显示)

		} finally {													// 完成就关闭
			if (out != null) {
				out.close();
			}
		}

	}

}
