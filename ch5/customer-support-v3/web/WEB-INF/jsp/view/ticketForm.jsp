<html>
<head>
    <title>Customer Support v3</title>
</head>
<body>
    <a href="<c:url value="login">
            <c:param name="action" value="logout"/>
        </c:url>">退出</a><br/>
    <h2>创建票据</h2>
    <form method="post" action="ticket" enctype="multipart/form-data">
        主题<br/>
        <input type="text" name="subject" value=""><br/>
        内容<br/>
        <textarea name="body" rows="5" cols="30"></textarea><br/>
        <b>附件</b><br/>
        <input type="file" name="file1"><br/><br/>
        <input type="submit" value="创建">
    </form>
</body>
</html>
