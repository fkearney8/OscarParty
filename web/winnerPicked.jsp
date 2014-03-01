<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <div style="padding: 10px; font-size: 18px;">
        Winner <%= request.getAttribute("winner") %> saved for category <%= request.getAttribute("category") %>.<br/>
        Next Category is <%= request.getAttribute("nextCategory") %>
        <br/>
        <a href="winnerPicker">Back to Winner Selection</a>
    </div>
</body>
</html>