<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/17
  Time: 2:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="contacts" type="java.util.Set<jun.projavawebapp.Contact>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Address Book</title>
</head>
<body>
<h2>Address Book Contacts</h2>
<c:choose>
    <c:when test="${fn:length(contacts) == 0}">
        <i>There are no contacts in the address book.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${contacts}" var="contact">
            <b>
                <c:out value="${contact.lastName} ${contact.firstName}"/>
            </b>
            <br/>
            <c:out value="${contact.address}"/>
            <br/>
            <c:out value="${contact.phoneNumber}"/>
            <br/>
            <c:if test="${contact.birthDay != null}">
                Birthday: ${contact.birthDay}<br/>
            </c:if>
            Created: ${contact.dateCreated}<br/><br/>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
