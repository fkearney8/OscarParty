<html>
<head>
    <%@include file="resources.jsp"%>
    <style>
        .categoryDiv {
            width: 30%;
            display: inline-block;
            padding: 10px;
        }
        .winnerSelection {
            color: #EFEC9F;
            font-weight: bold;
        }
        .loserSelection {
            color: #DDDDDD;
        }
        .selectedWithWinner {
            color: #32FF64;
        }
        .selectedButMissed {
            text-decoration: line-through;
            color: #881111;
        }
        .pendingSelection {
            color: #FFFFFF;
        }
        .playerSelectionUl {
            list-style-type: none;
        }
    </style>
</head>
<body style="font-size: 16px;">
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.*" %>
    <%
    DisplayablePlayerPicks playerPicks = (DisplayablePlayerPicks) request.getAttribute("playerPicks");
    %>

    <%!
        public String selectionClass(boolean pickedAWinner, boolean catSelected) {
            if (catSelected && pickedAWinner)
                return "selectedWithWinner";
            else if (catSelected && !pickedAWinner)
                return "selectedButMissed";
            else //not selected yet
                return "pendingSelection";
        }
    %>

    <h1>Player: <%= playerPicks.playerName() %></h1>
    <h2>Point Total: <%= playerPicks.playerPoints() %></h2>
    <%
    List<DisplayableCategory> displayCats = playerPicks.getDisplayCategoriesJava();
    for (DisplayableCategory catPicks : displayCats) {

        %>
        <div class="categoryDiv">
            <%
            boolean catSelected = catPicks.categoryAlreadySelected();
            boolean pickedAWinner = catPicks.pickedAWinnerInThisCategory();
            String headerClass = selectionClass(pickedAWinner, catSelected);
            %>
            <h3 class="<%=headerClass%>"><%= catPicks.categoryName() %></h3>
            <ul class="playerSelectionUl">
                <%
                DisplayablePick topPick = catPicks.topPick();
                String cssClass = selectionClass(topPick.isAWinner(), catSelected);
                %>
                <li class="<%=cssClass%>">
                    <%= topPick.pointValue() %> pts: <%= topPick.nomineeSelected() %>
                </li>
                <%
                DisplayablePick midPick = catPicks.midPick();
                cssClass = selectionClass(midPick.isAWinner(), catSelected);
                %>
                <li class="<%=cssClass%>">
                    <%= midPick.pointValue() %> pts: <%= midPick.nomineeSelected() %>
                </li>
                <%
                DisplayablePick botPick = catPicks.botPick();
                cssClass = selectionClass(botPick.isAWinner(), catSelected);
                %>
                <li class="<%=cssClass%>">
                    <%= botPick.pointValue() %> pts: <%= botPick.nomineeSelected() %>
                </li>
            </ul>
        </div>
        <%
    }
    %>

</body>