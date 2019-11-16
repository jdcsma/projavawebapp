<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/13
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="user" type="jun.projavawebapp.pojo.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
User ID: ${user.userID}<br/>
Username: ${user.username} (${user.username.length()} characters)<br/>
Full Name: ${fn:escapeXml(user.lastName) += ", " += fn:escapeXml(user.firstName)}
<br/><br/>
<b>Permissions (${fn:length(user.permissions)})</b><br/>
User: ${user.permissions["user"]}<br/>
Moderator: ${user.permissions["moderator"]}<br/>
Administrator: ${user.permissions["admin"]}<br/>
<br/>
</body>
</html>
