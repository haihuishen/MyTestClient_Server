<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
</head>
<body>

	<center>
		<!-- /MyTestServer/upload.do -->
		<%-- 
			multi-  多样性，复杂性
		--%>
		
		<!-- action ==  表格提交的 "网站" -->
		<!-- enctype="multipart/form-data" -- 告诉服务器"上传"的不是简单的"字符串"，不行默认是简单字符串 
			如果不设置是拿不到字符串的(因为这里同同时上传了图片类型)-->
		<!-- ${pageContext.request.contextPath} ==>  /MyTestServer -->
		<form action="${pageContext.request.contextPath}/upload.do"
			method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>
						Name
					</td>
					<td>
						<input type="text" name="Name">
					</td>
				</tr>
				<tr>
					<td>
						Gender
					</td>
					<td>
						<input type="text" name="Gender">		<!-- 性别 -->
					</td>
				</tr>
				<tr>
					<td>
						请选择一个上传文件
					</td>
					<td>
						<input type="file" name="Image">			<!-- 注意这个类型是"文件类型" -->
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="上传">
					</td>
					<td>
						<input type="reset" value="重置">
					</td>
				</tr>
			</table>
		</form>
	</center>

</body>
</html>