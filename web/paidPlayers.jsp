<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.playerpicks.*, scala.collection.convert.*" %>


    <form method="POST" action="paidPlayers">
    <%
    List playersPaid = (List) request.getAttribute("playerNames");
    for (int i=0; i < playersPaid.size(); i++) {
        PlayerPicks playerPicks = (PlayerPicks) playersPaid.get(i);
    %>
        <div>
            <div style="display: inline-block; width: 300px">
                <%= playerPicks.userName() %>
            </div>
            <div style="display: inline-block;">
                <input type="checkbox" name="<%= playerPicks.userName() %>" value="<%= playerPicks.userName() %>"
                        <% if (playerPicks.paid()) {
                            %>checked<%
                        }%> >
                    Paid</input>
            </div>
        </div>
    <%
    }
    %>
    <input type="Submit" value="Update Payers"/>
    </form>

</body>
</html>