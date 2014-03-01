<html>
<head>
    <%@include file="resources.jsp"%>
    <script>
        <% String catsToNomsMap = (String) request.getAttribute("catsToNomsMap"); %>
        var catsToNomsMap = <%= catsToNomsMap %>
        function categorySelected() {
            var catSelected = $("#categorySelector").val();
            $("#winnerSelector").empty();
            var nominees = catsToNomsMap[catSelected];

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
    <form>
        Select Winner for Category:
        <select id="categorySelector" onChange="javascript:categorySelected()">
            <option value="None">None</option>
            <%
            for (String categoryName : orderedCats) {
                %><option value="<%= categoryName %>"><%= categoryName %></option><%
            }
            %>
        </select>

        <select id="winnerSelector">
            <option value="None">None</option>
        </select>
        <br/>
        Select the next category, if we know it:
        <select id="categorySelector">
            <option value="None">None</option>
            <%
            for (String categoryName : orderedCats) {
                %><option value="<%= categoryName %>"><%= categoryName %></option><%
            }
            %>
        </select>
        <input type="button" value="Submit"></input>
    </form>

</body>
</html>