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
@WebServlet("/getStudents.do")					// 这是映射地址： 可以修改     // 以前是在 web.xml配置的，这是3.0新写法
public class GetStudentsServlet4AndroidClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		List<Student> students = new ArrayList<Student>();
		students.add(new Student(100L, "Tom", 20));
		students.add(new Student(101L, "Jack", 21));
		students.add(new Student(102L, "Lisa", 22));
		students.add(new Student(103L, "Jerry", 23));
		
		
		/**
		 * 转换JSON格式，json-lib
		 */
		JSONArray array = JSONArray.fromObject(students);	// 将student类数组，弄成json   // 将 JAVABean封装成 "json"
		
		System.out.println(array.toString());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = null;

		try {
			out = response.getWriter();						

			out.print(array.toString());					// 在"客户端(浏览器/android)"打印(显示)

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
