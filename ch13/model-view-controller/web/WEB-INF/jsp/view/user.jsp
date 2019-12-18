<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/12/18
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="currentUser" type="jun.projavawebapp.site.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
ID: ${currentUser.userId}<br/>
Username: ${currentUser.username}<br/>
Role: ${currentUser.role}<br/>
</body>
</html>
