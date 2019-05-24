<%@ page import="java.util.Collection" %>
<%@ page import="java.io.IOException" %>

<%!
    private static String writeSessionStatus(
            HttpSession session, JspWriter writer,
            long timestamp) throws IOException {

        long timeInterval = timestamp - session.getLastAccessedTime();

        String timeText;
        if (timeInterval < 1_000) {
            timeText = "小于1秒";
        } else if (timeInterval < 60_000) {
            timeText = (timeInterval / 1_000) + " 秒";
        } else {
            timeText = "大约 " + (timeInterval / 60_000) + " 分钟";
        }

        writer.print(session.getId() + " - " + session.getAttribute("username"));

        if (session.getId().equals(session.getId())) {
            writer.print(" (你)");
        }

        writer.print(" - 最后 " + timeText + "以前<br/>");
    }
%>

<html>
<head>
    <title>Consumer Support v3</title>
</head>
<body>
    <a href="<c:url value="login?logout"/>">退出</a><br/>
    <h2>会话列表</h2>
    <%
        @SuppressWarnings("unchecked")
        Collection<HttpSession> sessions =
                (Collection) request.getAttribute("sessions");
    %>
    目前在应用程序中存在<%= sessions.size() %>个活动的会话。<br/>
    <%
        long timestamp = System.currentTimeMillis();

        for (HttpSession s : sessions)
        {
            writeSessionStatus(s, out, timestamp);
        }
    %>

</body>
</html>
