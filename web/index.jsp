<html>
<head>
    <%@include file="resources.jsp"%>
</head>
<body>
    <%@include file="header.jsp"%>
    <h1>Hello Theresa!</h1>
    <h2>Welcome to the Oscar Party!</h2>
    <%= new String("Hello from the (index) JSP code!") %>

    <a href="selection.jsp">Selection of winners</a>

    <div id="WinnerSelectionAccordion" class="accordion">
        <h3>Best Picture</h3>
        <div>There is some content here about the best picture.</div>
        <h3>Best Actor</h3>
        <div>It is probably Meryl Streep, har har</div>
    </div>

    <div id="accordion">
    <h3>First header</h3>
    <div>First content panel</div>
    <h3>Second header</h3>
    <div>Second content panel</div>
    </div>

    <script>
        $("h2").hide();
        $(".accordion").accordion();
    </script>
</body>
</html>