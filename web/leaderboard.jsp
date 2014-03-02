<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.leaderboard.PlayerWithPoints" %>
    <% List usersAndPoints = (List) request.getAttribute("usersAndPoints");
    for (int i = 0; i < usersAndPoints.size(); i++) {
        PlayerWithPoints playerWithPoints = (PlayerWithPoints) usersAndPoints.get(i);
        %>
        <div style="border: solid 1px;">
            <div style="display: inline-block; width: 300px">
                <%= playerWithPoints.userName() %>
            </div>
            <div style="display: inline-block; width: 100px">
                <%= playerWithPoints.points() %>
            </div>
        </div>
        <%
    }
    %>




</body>
</html>