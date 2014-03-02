<html>
<head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
    <%@include file="resources.jsp"%>
    <style>
        .leaderrow {
            border: solid 1px;
            margin: 10px;
            padding 5px;
            max-width: 1000px;
        }
        .leaderrow-odd {
            background: url("jquery-ui-1.10.4.custom/css/swanky-purse/images/ui-bg_diamond_25_675423_10x8.png") repeat scroll 50% 50% #443113;
        }
        .leaderrow-even {
            background: url("jquery-ui-1.10.4.custom/css/swanky-purse/images/ui-bg_diamond_10_4f4221_10x8.png") repeat scroll 50% 50% #4F4221;
        }
        .leadername {
            display: inline-block;
            min-width: 300px;
        }
        .leaderpoints {
            display: inline-block;
            max-width: 100px
        }
    </style>
    <script>
        $(function() {
            $(".leaderrow:odd").addClass("leaderrow-odd");
            $(".leaderrow:even").addClass("leaderrow-even");
        });
    </script>
</head>
<body style="font-size: 20px;">
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.leaderboard.PlayerWithPoints" %>
    <% List usersAndPoints = (List) request.getAttribute("usersAndPoints");
    for (int i = 0; i < usersAndPoints.size(); i++) {
        PlayerWithPoints playerWithPoints = (PlayerWithPoints) usersAndPoints.get(i);
        %>
        <div class="leaderrow">
            <div class="leadername">
                <%= playerWithPoints.userName() %>
            </div>
            <div class="leaderpoints">
                <%= playerWithPoints.points() %>
            </div>
        </div>
        <%
    }
    %>




</body>
</html>