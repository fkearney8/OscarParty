<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*, com.oscarparty.servlets.playerpicks.*" %>
    <jsp:useBean id="allPlayerPicks" scope="request" class="java.util.LinkedList" />
    <%
    for (int i=0; i < allPlayerPicks.size(); i++) {
        PlayerPicks eachPlayerPicks = (PlayerPicks) allPlayerPicks.get(i);
        %>
        <div style="border: solid 1px;">
            <h3><%= eachPlayerPicks.userName() %></h3>
            <%
            List<CategoryPicks> categoryPicks = eachPlayerPicks.categoryPicksInOrderJava();
            for (CategoryPicks catPicks : categoryPicks) {
                %>
                <div style="display: inline-block;">
                    <h4><%= catPicks.categoryName() %></h4>
                    <ol>
                        <li><%= catPicks.getPickAtPriority("topPick") %></li>
                        <li><%= catPicks.getPickAtPriority("midPick") %></li>
                        <li><%= catPicks.getPickAtPriority("botPick") %></li>
                    </ol>
                </div>
                <%
            }
            %>
        </div>
        <%
    }
    %>

</body>
</html>