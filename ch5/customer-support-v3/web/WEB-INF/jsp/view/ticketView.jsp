
<html>
<head>
    <title>Customer Support v3</title>
</head>
<body>
    <%
        Ticket ticket = (Ticket) request.getAttribute("ticket");
        String ticketId = Integer.toString(ticket.getId());
    %>
    <a href="<c:url value="login">
        <c:param name="action" value="logout"/>
    </c:url>">退出</a><br/>
    <h2>票据#<%= ticketId %>：<%= ticket.getSubject() %></h2>
    <i>客户：<%= ticket.getCustomer() %></i><br/>
    <%= ticket.getBody() %><br/><br/>
    <%
        if (ticket.getNumberOfAttachments() > 0) {
    %>
    附件：
    <%
            int i = 0;
            for (Attachment a : ticket.getAttachments()) {

                if (i++ > 0) {
                    out.print(", ");
                }
    %>
    <a href="<c:url value="attachment">
                <c:param name="ticketId" value="<%= ticketId %>"/>
                <c:param name="attachment" value="<%= a.getName() %>"/>
            </c:url>">
                <%= a.getName() %></a><%
            }
    %><br/><br/>
        <form method="post" action="attachment" enctype="multipart/form-data">
            <input type="hidden" name="ticketId" value="<%= ticketId %>"/>
            添加附件
            <input type="file" name="file1"><br/><br/>
            <input type="submit" value="添加">
        </form>
    <%
        }
    %>
    <br/>
    <a href="<c:url value="ticketList"/>">票据列表</a>
</body>
</html>