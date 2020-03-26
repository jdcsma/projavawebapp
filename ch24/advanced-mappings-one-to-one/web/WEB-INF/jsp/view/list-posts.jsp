<%--@elvariable id="posts" type="java.util.List<jun.projavawebapp.site.entities.Post>"--%>
<!DOCTYPE html>
<html>
<head>
    <title>Demo OneToOne</title>
    <style type="text/css">
        table {
            border-collapse: collapse;
        }

        table th, table td {
            padding: 5px;
        }
    </style>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>CreatedOn</th>
    </tr>
    <c:forEach items="${posts}" var="post">
        <tr>
            <td>${post.id}</td>
            <td>${post.name}</td>
            <td><time:formatDate value="${post.detail.createdOn}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
