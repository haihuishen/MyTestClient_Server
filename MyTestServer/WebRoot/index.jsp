<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%-- /maizi --%>
	<%-- <img alt="" src="${pageContext.request.contextPath}/image/1.jpg"> --%>
	
	<%-- get请求 --%>
<%-- 	<img alt="" src="${pageContext.request.contextPath}/getImage.jpeg"> --%>


		<img alt="" src="${pageContext.request.contextPath}/getImage.jpeg?id=1" width="136px" height="76px">
		
		<hr>
		
		<img alt="" src="${pageContext.request.contextPath}/getImage.jpeg?id=2" width="136px" height="76px">
	
</body>
</html>