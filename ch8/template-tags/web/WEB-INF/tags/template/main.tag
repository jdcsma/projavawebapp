<%@ tag body-content="scriptless" dynamic-attributes="dynamicAttributes"
    trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>

<!DOCTYPE html>
<html lang="en"
<c:forEach items="${dynamicAttributes}" var="a">
    <c:out value=' ${a.key} = "${fn:escapeXml(a.value)}"' escapeXml="false"/>
</c:forEach>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${fn:trim(htmlTitle)}"/></title>
</head>
<body>
    <jsp:doBody/>
</body>
</html>