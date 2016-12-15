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
@WebServlet("/upload.do")								// ����ӳ���ַ�� �����޸�     // ��ǰ���� web.xml���õģ�����3.0��д��
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
		
		// ������ commons-fileupload-1.2.1.jar �� commons-io-1.4.jar

		// �жϱ��������Ƿ���һ����"�ϴ��ļ�"��"�����Ե�����"
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);	

		if (isMultipart) {
			
			/**
			 * �����ϴ����ݵ�׼������
			 * ����һ���ϴ��Ļ�����
			 * λ��|��С
			 */
			DiskFileItemFactory factory = new DiskFileItemFactory();	// ʹ��"����"����ļ�
			factory.setSizeThreshold(1024 * 1024 * 2);					// �������Ĵ�С
			File temp = new File("e:\\temp");							// �����ļ�
			if(!temp.exists()) {
				temp.mkdir();
			}
			factory.setRepository(temp);								
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");							// ����ͷ�ı���
			upload.setFileSizeMax(1024 * 1024 * 5);						// ָ��"�ϴ��ļ�"��С
			upload.setSizeMax(1024 * 1024 * 6);
			
			
			try {
				
				out = response.getWriter();
				
				List<FileItem> items = upload.parseRequest(request);
				if(items != null) {										// ���ϴ�����������
					for (FileItem item : items) {						// �ó�ÿ������
						
						if(item.isFormField()) {						// ��һ�㡱���ݣ��ַ���
							
							System.out.println(item.getFieldName());				// ��ȡ���������"name"��ֵ
							System.out.println(item.getSize());						// ��С
							System.out.println(item.getString());
							
							if(item.getFieldName().equals("Name")) {
								System.out.println("���֣�" + item.getString());
							} else if(item.getFieldName().equals("Gender")) {
								System.out.println("�Ա�" + item.getString());
							}
							
						} else {										// �����ӡ����ݣ��ļ�
							System.out.println(item.getName());						// ��ȡ�ļ���
							item.write(new File("D:\\" + item.getName()));			// д�ļ�(��itemд�� d��)
							
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
