<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://custom.tags" prefix="lct" %>
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
		
		Server Version: <%= application.getServerInfo() %><br>
Servlet Version: <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %>
JSP Version: <%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %> <br>
<lct:emptyTag/>
<lct:paramTag 
	a="10"
	b="101"
/>

<lct:textBodyTag iterations="3">1</lct:textBodyTag>

<lct:dateFormatTag locale="uk" date="${now}"></lct:dateFormatTag>

<lct:dateFormatTag date="${now}"></lct:dateFormatTag>

</body>
</html>
