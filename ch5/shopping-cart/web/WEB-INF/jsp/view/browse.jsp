<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h2>Product List</h2>
<a href="<c:url value="/shop?action=viewCart" />">View Cart</a><br/><br/>
<%
    Map<Integer, String> products =
            (Map<Integer, String>) request.getAttribute("products");

    for (Integer id : products.keySet()) {
%>
        <a href="<c:url value="/shop">
                <c:param name="action" value="addToCart"/>
                <c:param name="productId" value="<%= id.toString()%>"/>
            </c:url>">
            <%= products.get(id) %>
        </a><br/>
<%
    }
%>
</body>
</html>
