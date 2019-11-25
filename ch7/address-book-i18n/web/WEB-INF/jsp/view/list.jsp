<%--
  Created by IntelliJ IDEA.
  User: liujun
  Date: 2019/11/17
  Time: 2:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="contacts" type="java.util.Set<jun.projavawebapp.Contact>"--%>
<%@ page import="java.time.format.TextStyle" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><fmt:message key="title.browser"/></title>
</head>
<body>
<h1></h1>
<h2><fmt:message key="title.page"/></h2>
<c:choose>
    <c:when test="${fn:length(contacts) == 0}">
        <i><fmt:message key="message.noContacts"/></i>
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
                <fmt:message key="label.birthday"/>:
                ${contact.birthDay.month.getDisplayName(TextStyle.FULL,
                        pageContext.response.locale)}&nbsp;${contact.birthDay.dayOfMonth}<br/>
            </c:if>
            <fmt:message key="label.creationDate"/>:
            <fmt:formatDate value="${contact.oldDateCreated}"
                            type="both" dateStyle="long" timeStyle="long"/>
            <br/><br/>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
