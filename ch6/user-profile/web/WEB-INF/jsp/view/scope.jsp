<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/13
  Time: 3:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    pageContext.setAttribute("a", "page");
    request.setAttribute("a", "request");
    session.setAttribute("a", "session");
    application.setAttribute("a", "application");

    request.setAttribute("b", "request");
    session.setAttribute("b", "session");
    application.setAttribute("b", "application");

    session.setAttribute("c", "session");
    application.setAttribute("c", "application");

    application.setAttribute("d", "application");
%>
<html>
<head>
    <title>Scope</title>
</head>
<body>
    a = ${a}<br/>
    b = ${b}<br/>
    c = ${c}<br/>
    d = ${d}<br/>
</body>
</html>
