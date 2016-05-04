<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 01.05.2016
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP</title>
</head>
<body>
<form action="/helloServlet" method="post">
    Number of package: <input type="text" name="number"><br/>
    Checksum(MD5): <input type="text" name="checksum"><br/>
    Date(base64): <input type="text" name="data"><br/>
    isLast(boolean): <input type="text" name="isLast"><br/>
    <input type="submit" value="Submit" /><br/>
</form>
<form action="/helloServlet" method="get">

    <input type="submit" value="submit get" /><br/>
</form>
</body>
</html>
