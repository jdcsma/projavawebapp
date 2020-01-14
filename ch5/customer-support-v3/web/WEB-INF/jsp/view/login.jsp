<html>
<head>
    <title>Customer Support v3</title>
</head>
<body>
    <h2>登陆</h2>
    您需要登陆才能访问客户支持站点。<br/><br/>
    <%
        boolean loginFailed = (boolean)
                request.getAttribute("loginFailed");

        if (loginFailed) {
    %>
            您输入的账户或密码不正确，请重新输入。<br/><br/>
    <%
        }
    %>

    <form method="post" action="login">
        账户
        <input type="text" name="username" value=""><br/>
        密码
        <input type="password" name="password" value=""><br/><br/>
        <input type="submit" value="登陆">
    </form>
</body>
</html>
