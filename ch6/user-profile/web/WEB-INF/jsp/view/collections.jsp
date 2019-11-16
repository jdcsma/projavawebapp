<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/14
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="users" type="java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Collections</title>
</head>
<body>
    ${users.stream()
        .filter(user -> fn:contains(user.username, "1"))
        .sorted((lhs, rhs) -> (x = lhs.lastName.compareTo(rhs.lastName);
            x == 0 ? lhs.firstName.compareTo(rhs.firstName) : x))
        .map(user -> {"username":user.username, "first": user.firstName, "last": user.lastName})
        .toList()}
</body>
</html>
