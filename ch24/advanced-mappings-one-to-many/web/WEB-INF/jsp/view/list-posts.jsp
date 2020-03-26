<%@ taglib prefix="time" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="posts" type="java.util.List<jun.projavawebapp.site.entities.Post>"--%>
<!DOCTYPE html>
<html>
<head>
    <title>Demo OneToMany</title>
</head>
<body>
<c:forEach items="${posts}" var="post">
    ID: ${post.id} Name: ${post.name}<br/>
    <c:if test="${post.comments.size() > 0}">
        <ol>
            <c:forEach items="${post.comments}" var="comment">
                <li>${comment.review} [<time:formatDate value="${comment.createdOn}" pattern="yyyy-MM-dd HH:mm:ss"/>]
                </li>
            </c:forEach>
        </ol>
    </c:if>
</c:forEach>
</body>
</html>
