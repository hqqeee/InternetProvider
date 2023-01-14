<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>JSP 2.0 Expression Language - Functions</title>
</head>
<body>
	<h1>JSP 2.0 Expression Language - Functions</h1>
	<hr>
	An upgrade from the JSTL expression language, the JSP 2.0 EL also
	allows for simple function invocation. Functions are defined by tag
	libraries and are implemented by a Java programmer as static methods.
	Text
	<br>
	<img
		src="images/arrow.svg"
		width="16">
	<br> Text
	<br>
	
	Mine type : ${pageContext.servletContext.getMimeType(".svg")}
	<blockquote>
		<u><b>Change Parameter</b></u>
</body>
</html>
