<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/13
  Time: 3:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    application.setAttribute("appAttribute", "foo");
    pageContext.setAttribute("pageAttribute", "bar");
    session.setAttribute("sessionAttribute", "sand");
    request.setAttribute("requestAttribute", "castle");
%>
<html>
<head>
    <title>Info</title>
</head>
<body>
Remote Address: ${pageContext.request.remoteAddr}<br/>
Request URL: ${pageContext.request.requestURL}<br/>
Session ID: ${pageContext.request.session.id}<br/>
Application Scope: ${applicationScope["appAttribute"]}<br/>
Page Scope: ${pageScope["pageAttribute"]}<br/>
Session Scope: ${sessionScope["sessionAttribute"]}<br/>
Request Scope: ${requestScope["requestAttribute"]}<br/>
User Parameter: ${param["user"]}<br/>
Color Multi-param: ${fn:join(paramValues["colors"], ", ")}<br/>
Accept Header: ${header["Accept"]}<br/>
Content-Type Header: ${header["Content-Type"]}<br/>
Accept-Encoding Header: ${header["Accept-Encoding"]}<br/>
Session ID Cookie Value: ${cookie["JSESSIONID"].value}<br/>
</body>
</html>
