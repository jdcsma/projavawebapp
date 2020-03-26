<%--@elvariable id="schools" type="java.util.List<jun.projavawebapp.site.entities.School>"--%>
<!DOCTYPE html>
<html>
<head>
    <title>Demo ManyToMany</title>
</head>
<body>
<c:forEach items="${schools}" var="school">
    school: ${school.name} <br/>
    <c:if test="${school.students.size() > 0}">
        <c:forEach items="${school.students}" var="student">
        <ul>
            <li>
                student: ${student.name}
            </li>
        </ul>
        </c:forEach>
    </c:if>
</c:forEach>
</body>
</html>
