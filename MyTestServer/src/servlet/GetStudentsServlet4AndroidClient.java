package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Student;

import net.sf.json.JSONArray;


/**
 * URL: http://localhost:8080/MyTestServer/getStudents.do
 * 
 * @author shen
 * 
 */
@WebServlet("/getStudents.do")					// ����ӳ���ַ�� �����޸�     // ��ǰ���� web.xml���õģ�����3.0��д��
public class GetStudentsServlet4AndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(100L, "Tom", 20));
		students.add(new Student(101L, "Jack", 21));
		students.add(new Student(102L, "Lisa", 22));
		students.add(new Student(103L, "Jerry", 23));
		
		
		/**
		 * ת��JSON��ʽ��json-lib
		 */
		JSONArray array = JSONArray.fromObject(students);	// ��student�����飬Ū��json   // �� JAVABean��װ�� "json"
		
		System.out.println(array.toString());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = null;

		try {
			out = response.getWriter();						

			out.print(array.toString());					// ��"�ͻ���(�����/android)"��ӡ(��ʾ)

		} finally {
			if (out != null) {
				out.close();
			}
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
