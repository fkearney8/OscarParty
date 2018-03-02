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

    <h1>Player: <%= request.getAttribute("playerName") %></h1>
    <h2>Point Total: <%= request.getAttribute("totalPoints") %></h2>
    <%
    List<DisplayableCategory> playerPicks = (List<DisplayableCategory>) request.getAttribute("displayableCategoryPicks");
    for (DisplayableCategory catPicks : playerPicks) {
        %>
        <div class="categoryDiv">
            <%
            boolean catSelected = catPicks.categoryHasAWinner();
            boolean pickedAWinner = catPicks.pickedAWinnerInThisCategory();
            String headerClass = selectionClass(pickedAWinner, catSelected);
            %>
            <h3 class="<%=headerClass%>"><%= catPicks.categoryName() %></h3>
            <ul class="playerSelectionUl">
                <%
                DisplayablePick topPick = catPicks.pickAt(0);
                String cssClass = selectionClass(topPick.isAWinner(), catSelected);
                %>
                <li class="<%=cssClass%>">
                    <%= topPick.pointValue() %> pts: <%= topPick.nomineeSelected() %>
                </li>
                <%
                DisplayablePick midPick = catPicks.pickAt(1);
                cssClass = selectionClass(midPick.isAWinner(), catSelected);
                %>
                <li class="<%=cssClass%>">
                    <%= midPick.pointValue() %> pts: <%= midPick.nomineeSelected() %>
                </li>
                <%
                DisplayablePick botPick = catPicks.pickAt(2);
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