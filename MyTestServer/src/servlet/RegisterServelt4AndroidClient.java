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
@WebServlet("/register.do")			 // ����ӳ���ַ�� �����޸�     // ��ǰ���� web.xml���õģ�����3.0��д��
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

		
		/***** JSON���ݽ��������� ****/
		/**
		 * json-lib����JSON���ݵĺ��Ĵ���
		 * 
		 */
		JSONObject object = JSONObject.fromObject(data);				// ��Ϊ���������"Object"

		String loginName = object.getString("LoginName");
		System.out.println("ע��ĵ�¼���ǣ�" + loginName);

		JSONArray interesing = object.getJSONArray("Interesting");
		System.out.println("ע�����Ȥ�����У�");
		if (interesing != null) {
			for (Object obj : interesing) {
				System.out.print(obj.toString() + "\t");
			}
		}
		System.out.println();
		/***** JSON���ݽ������****/

		/**
		 * �����ҵ����
		 * �罫�����������ݣ�����"���ݿ�"
		 */

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");		// ���÷�����Ӧ��"�ַ���"

		/**
		 * ����ֵ<br>
		 * PrintWriter out = response.getWriter();<br>
		 * ������"�ͻ���(�����/android)"����ַ���<br>
		 */
		PrintWriter out = null;	

		try {
			out = response.getWriter();

			/**
			 * JSON�����ݷ�װ
			 * 
			 *  ��Ӧ�� ��װJSON��� { "result" : "success", "errorMsg" : "" }
			 * { "result" : "failed", "errorMsg" : "�������������ע��ʧ�ܡ�" }
			 */
			ResultJSONBean jsonBean = new ResultJSONBean();
			// ����������ݿ�"�ɹ�"
			 jsonBean.setResult("success");
			 jsonBean.setErrorMsg("");

			// ����������ݿ�"ʧ��"
			// jsonBean.setResult("failed");
			// jsonBean.setErrorMsg("�������������ע��ʧ�ܡ�");

			JSONObject obj = JSONObject.fromObject(jsonBean);		// �� JAVABean��װ�� "json"

			System.out.println(obj.toString());

			out.print(obj.toString());								// ��"�ͻ���(�����/android)"��ӡ(��ʾ)

		} finally {													// ��ɾ͹ر�
			if (out != null) {
				out.close();
			}
		}

	}

}
