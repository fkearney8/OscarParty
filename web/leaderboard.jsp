<html>
<head>
    <meta http-equiv="refresh" content="20" />
    <%@include file="resources.jsp"%>
    <style>
        .leaderpanel {
            float: left;
            font-size: 24px;
            font-family: Arial, Helvetica, sans-serif;
            width: 55%;
        }
        .headerrow {
           margin: 10px;
        }
        .leaderpoints.header {
            font-family: Arial, Helvetica, sans-serif;
        }
        .leaderrow {
            border: solid 1px;
            margin: 10px;
            padding 5px;
        }
        .leaderrow-odd {
            background: url("jquery-ui-1.10.4.custom/css/swanky-purse/images/ui-bg_diamond_25_675423_10x8.png") repeat scroll 50% 50% #443113;
        }
        .leaderrow-even {
            background: url("jquery-ui-1.10.4.custom/css/swanky-purse/images/ui-bg_diamond_10_4f4221_10x8.png") repeat scroll 50% 50% #4F4221;
        }
        .leadername {
            display: inline-block;
        }
        .leaderpoints {
            font-family: "Comic Sans MS", cursive, sans-serif;
            display: inline-block;
            float: right;
            padding: 10px 25px 0px 0px
        }
        .leaderNextCatPicks {
            font-size: 18px;
        }

        .winnersPanel {
            float: right;
            max-width: 500px;
            font-size: 14px;
            margin-right: 50px;
            width: 45%
        }
        .catWinnerRow {
            border: 1px solid;
        }
        .categoryDiv {
            display: inline-block;
            padding: 3px 10px 3px 5px;
            width: 40%;
        }
        .winnerNameDiv {
            display: inline-block;
            padding: 3px 5px 3px 0px;
            width: 55%;
        }
    </style>
    <script>
        $(function() {
            $(".leaderrow:odd").addClass("leaderrow-odd");
            $(".leaderrow:even").addClass("leaderrow-even");
            $('.leaderrow').css('cursor', 'pointer');
        });

        function gotoPlayerPicks(playerName) {
            window.location.href = "playerPicks?playerName=" + playerName;
        }
    </script>
</head>
<body style="font-size: 20px;">
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.PlayerWithPoints, com.oscarparty.servlets.CategoryAndWinner" %>

    <div class="leaderpanel">
        <div class="headerrow">
            <div class="leadername header">
                Player
            </div>
            <div class="leaderpoints header">
                Points
            </div>
            <div class="leaderNextCatPicks header">
                Picks for <%= request.getAttribute("nextCategory") %>
            </div>
        </div>

        <% List usersAndPoints = (List) request.getAttribute("usersAndPoints");
        for (int i = 0; i < usersAndPoints.size(); i++) {
            PlayerWithPoints playerWithPoints = (PlayerWithPoints) usersAndPoints.get(i);
            %>
            <div class="leaderrow" onclick="javascript:gotoPlayerPicks(&quot;<%= playerWithPoints.userName() %>&quot;)">
                <div class="leadername">
                    <%= playerWithPoints.userName() %>
                </div>
                <div class="leaderpoints">
                    <%= playerWithPoints.points() %>
                </div>
                <div class="leaderNextCatPicks">
                    <%= playerWithPoints.nextCategoryPicks() %>
                </div>
            </div>
            <%
        }
        %>
    </div>

    <div class="winnersPanel">
        <% List winnersList = (List) request.getAttribute("categoryWinners");
        for (int i=0; i < winnersList.size(); i++) {
            CategoryAndWinner catAndWinnerName = (CategoryAndWinner) winnersList.get(i);
            %>
            <div class="catWinnerRow">
                <div class="categoryDiv"><%= catAndWinnerName.categoryName() %></div>
                <div class="winnerNameDiv"><%= catAndWinnerName.winner() %></div>
            </div>
            <%
        }
        %>
    </div>


</body>
</html>