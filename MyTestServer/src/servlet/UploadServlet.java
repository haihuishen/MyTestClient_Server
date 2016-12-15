package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 * URL: http://localhost:8080/MyTestServer/upload.do
 * 
 * @author shen
 * 
 */
@WebServlet("/upload.do")								// 这是映射地址： 可以修改     // 以前是在 web.xml配置的，这是3.0新写法
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-upload - post-");

		request.setCharacterEncoding("UTF-8");

		// String name = request.getParameter("Name");
		// String gender = request.getParameter("Gender");
		//
		// System.out.println(name);
		// System.out.println(gender);
		//
		// request.getInputStream();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = null;
		
		// 依赖包 commons-fileupload-1.2.1.jar 和 commons-io-1.4.jar

		// 判断本次请求是否是一个有"上传文件"的"复杂性的请求"
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);	

		if (isMultipart) {
			
			/**
			 * 接受上传数据的准备工作
			 * 设置一个上传的缓冲区
			 * 位置|大小
			 */
			DiskFileItemFactory factory = new DiskFileItemFactory();	// 使用"磁盘"存放文件
			factory.setSizeThreshold(1024 * 1024 * 2);					// 缓冲区的大小
			File temp = new File("e:\\temp");							// 缓冲文件
			if(!temp.exists()) {
				temp.mkdir();
			}
			factory.setRepository(temp);								
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");							// 设置头的编码
			upload.setFileSizeMax(1024 * 1024 * 5);						// 指定"上传文件"大小
			upload.setSizeMax(1024 * 1024 * 6);
			
			
			try {
				
				out = response.getWriter();
				
				List<FileItem> items = upload.parseRequest(request);
				if(items != null) {										// 表单上传的所有数据
					for (FileItem item : items) {						// 拿出每项数据
						
						if(item.isFormField()) {						// “一般”数据，字符串
							
							System.out.println(item.getFieldName());				// 获取表单中这项的"name"的值
							System.out.println(item.getSize());						// 大小
							System.out.println(item.getString());
							
							if(item.getFieldName().equals("Name")) {
								System.out.println("名字：" + item.getString());
							} else if(item.getFieldName().equals("Gender")) {
								System.out.println("性别：" + item.getString());
							}
							
						} else {										// “复杂”数据，文件
							System.out.println(item.getName());						// 获取文件名
							item.write(new File("D:\\" + item.getName()));			// 写文件(将item写到 d盘)
							
						}
					}
				}
				
				out.print("Upload Success.");
				
			} catch (Exception e) {
				e.printStackTrace();
				out.print("Upload failed");
			}
			
		}

	}

}
