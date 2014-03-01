<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*, com.oscarparty.servlets.playerpicks.*" %>
    <% String[] catsWithoutWinners = (String[]) request.getAttribute("catsWithoutWinners"); %>
    <form>
        Select Winner for Category:
        <select>
            <%
            for (int i=0; i < catsWithoutWinners.length; i++) {
                String category = catsWithoutWinners[i];
                %>
                <option value="<%= category %>"><%= category %></option>
            <%}%>
        </select>
        <select>

        </select>
    <button type="submit" value="Submit"></button>
    </form>

</body>
</html>