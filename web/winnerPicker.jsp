<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*, com.oscarparty.servlets.playerpicks.*" %>
    <%
    Map<String, String[]> catsToNomsMap = (Map<String, String[]>) request.getAttribute("catsToNomsMap");
    String[] orderedCats = (String[]) request.getAttribute("orderedCatsWithoutWinners");
     %>
    <form>
        Select Winner for Category:
        <select>
            <%
            for (String categoryName : orderedCats) {
                %><option value="<%= categoryName %>"><%= categoryName %></option><%
            }
            %>
        </select>

        <select>
        <%
            //offer the nominees for the selected category, TODO do this for real on selection of a category
            String[] noms = catsToNomsMap.get("Best Live Action Short Film");
            for (String nom : noms) {
                %>
                <option value="<%= nom %>"><%= nom %></option>
                <%
            }
        %>

        </select>

    <input type="button" value="Submit"></input>
    </form>

</body>
</html>