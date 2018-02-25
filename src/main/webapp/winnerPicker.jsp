<html>
<head>
    <%@include file="resources.jsp"%>
    <script>
        $(function() {
            $(".categorySelector").val("<%= request.getAttribute("nextCategory") %>");
            //in case it didn't get set to something still available
            var catPreselection = $(".categorySelector").val();
            if (catPreselection == null || catPreselection.trim().length() == 0) {
                $(".categorySelector").val("None");
            }

            <%
                String categoriesWithoutWinners = (String) request.getAttribute("categoriesWithoutWinners");
            %>
            var categoriesWithoutWinners = <%= categoriesWithoutWinners %>;
            $.each(categoriesWithoutWinners, function(index, category) {
                $(".categorySelector")
                    .append($("<option></option>")
                        .attr("value", category.id)
                        .text(category.name))
            });
            categorySelected();
        });


        <% String catsToNomsMap = (String) request.getAttribute("catsToNomsMap"); %>
        var catsToNomsMap = <%= catsToNomsMap %>;
        function categorySelected() {
            var catSelected = $("#winnerCategorySelector").val();
            $("#winnerSelector").empty();
            var nominees = catsToNomsMap[catSelected];
            if (!nominees) nominees = [{name: "None", id: -1}];

            $.each(nominees, function(index, nominee) {
                 $('#winnerSelector')
                     .append($("<option></option>")
                         .attr("value", nominee.id)
                         .text(nominee.name));
            });
        }
    </script>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*, com.oscarparty.servlets.playerpicks.*" %>
    <div style="padding: 10px;font-size: 20px;">
        <form method="POST" action="winnersPicked.do">
            Select Winner for Category:
            <select id="winnerCategorySelector" class="categorySelector" name="winnerCategory" onChange="javascript:categorySelected()">
            </select>

            <select id="winnerSelector" name="winnerSelected">
                <option value="None">None</option>
            </select>
            <br/>
            Select the next category, if we know it:
            <select class="categorySelector" name="nextCategory">
            </select>
            <br/>
            <input type="submit" value="Submit">
        </form>
    </div>

</body>
</html>