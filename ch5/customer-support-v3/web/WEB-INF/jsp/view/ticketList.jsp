<%@ page import="java.util.Collection" %>

<html>
<head>
    <title>Customer Support v3</title>
</head>
<body>
    <%
        @SuppressWarnings("unchecked")
        Collection<Ticket> tickets =
                (Collection) request.getAttribute("tickets");
    %>
    <a href="<c:url value="login">
        <c:param name="action" value="logout"/>
    </c:url>">退出</a><br/>
    <h2>票据列表</h2>
    <%
        if (tickets.size() == 0) {
    %>
            <i>目前没有存在的票据。</i>
    <%
        } else {

            for (Ticket ticket : tickets) {
                String ticketId = Integer.toString(ticket.getId());
    %>
        Ticket #<%= ticketId %>: <a href="<c:url value="ticket">
                <c:param name="ticketId" value="<%= ticketId %>"/>
            </c:url>"><%= ticket.getSubject() %></a>（客户：
            <%= ticket.getCustomer() %>）<br/>
    <%
            }
        }
    %>
    <br/><br/>
    <a href="<c:url value="ticket"/>">创建票据</a>
</body>
</html>
