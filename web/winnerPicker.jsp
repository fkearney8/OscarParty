<html>
<head>
    <%@include file="resources.jsp"%>
    <script>
        $(function() {
            $("#categorySelector").val("<%= request.getAttribute("nextCategory") %>");
        });


        <% String catsToNomsMap = (String) request.getAttribute("catsToNomsMap"); %>
        var catsToNomsMap = <%= catsToNomsMap %>
        function categorySelected() {
            var catSelected = $("#categorySelector").val();
            $("#winnerSelector").empty();
            var nominees = catsToNomsMap[catSelected];
            if (!nominees) nominees = ["None"];

            $.each(nominees, function(index, nominee) {
                 $('#winnerSelector')
                     .append($("<option></option>")
                     .attr("value", nominee)
                     .text(nominee));
            });
        }
    </script>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*, com.oscarparty.servlets.playerpicks.*" %>
    <%
    String[] orderedCats = (String[]) request.getAttribute("orderedCatsWithoutWinners");
     %>
    <div style="padding: 10px; font-size: 18px;">
        <form method="POST" action="winnersPicked.do">
            Select Winner for Category:
            <select id="categorySelector" name="winnerCategory" onChange="javascript:categorySelected()">
                <option value="None">None</option>
                <%
                for (String categoryName : orderedCats) {
                    %><option value="<%= categoryName %>"><%= categoryName %></option><%
                }
                %>
            </select>

            <select id="winnerSelector" name="winnerSelected">
                <option value="None">None</option>
            </select>
            <br/>
            Select the next category, if we know it:
            <select id="categorySelector" name="nextCategory">
                <option value="None">None</option>
                <%
                for (String categoryName : orderedCats) {
                    %><option value="<%= categoryName %>"><%= categoryName %></option><%
                }
                %>
            </select>
            <br/>
            <input type="submit" value="Submit">
        </form>
    </div>

</body>
</html>