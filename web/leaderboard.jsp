<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
    <%@include file="resources.jsp"%>
</head>
<body style="font-size: 20px;">
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