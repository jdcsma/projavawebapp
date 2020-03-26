<%--@elvariable id="articles" type="java.util.List<jun.projavawebapp.site.entities.NewsArticle>"--%>
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
        <th>Title</th>
        <th>Content</th>
        <th>Version</th>
        <th>DateCreated</th>
        <th>DateModified</th>
    </tr>
    <c:forEach items="${articles}" var="article">
        <tr>
            <td>${article.id}</td>
            <td>${article.title}</td>
            <td>${article.content}</td>
            <td>${article.version}</td>
            <td><time:formatDate value="${article.dateCreated}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><time:formatDate value="${article.dateModified}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
