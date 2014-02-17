<html>
<head>
    <%@include file="resources.jsp"%>
    <style>
        .body {
            background: black;
            color: #EFEC9F;
            font-size: 12px;
        }
        .expander-area {max-width: 1000px; min-width: 800px;}
        .accordion-section {
            height: 320px;
        }
        .selection-area {float: left; width: 520px;}
        .selection {
            width: 125px;
            height: 70px;
            margin: 10px;
            border: 3px outset;
            padding: 5px;
            text-align: center;
            vertical-align: middle;
            display: inline-block;
            background: #000000;
        }
        .drop-area {float: right;}
        .drop {
            border-style: inset;
            border-width: 5px;
            width: 124px;
            height: 70px;
            margin: 20px;
            padding-left: 5px;
            z-index: -1;
            background: #443113;
        }
        .drop-ready {
            border-color: #FFFFFF;
        }
    </style>

    <script>
        $(function() {
            $(".accordion").accordion({
                heightStyle: "content"
            });

            $(".selection").draggable({
                containment: "accordion-section",
                revert: shouldRevert,
                //snap: ".drop",
                snapMode: "inner",
                cursor: "move",
                snapTolerance: 20
            });

            $(".drop").each(function(i) {this.elementDropped = "none"});

            $(".drop").droppable({
                accept: ".selection",
                drop: droppedSelection,
                hoverClass: "drop-ready",
                out: function (event, ui) {
                    //when a draggable is removed, we can take a draggable input again
                    $(this).droppable( "option", "accept", ".selection" );
                    this.elementDropped = "none";
                }
            });

        });

        function droppedSelection(event, ui) {
            //ui is the element that was dropped, so, a draggable
            ui.draggable.position({ //put the draggable directly over the droppable
                of: $(this),
                my: 'left top',
                at: 'left top'
            });
            //set this droppable not to take any more selection draggables, but keep it aware of taking one out
            $(this).droppable( "option", "accept", ui.draggable );

            //now track the dropped element
            this.elementDropped = ui.draggable.text();
        }

        //whether to revert a draggable back to its original position
        function shouldRevert(event, ui) {
            $(this).data("uiDraggable").originalPosition = {
                top : 0,
                left : 0
            };
            // return boolean
            return !event;
        }

    </script>
</head>
<body class="body">
    <%@include file="header.jsp"%>
    <h2>Welcome to the Oscar Party!</h2>

    <p style="width: 30%; font-size: 15px;">
    Instructions:<br/>
    Open each nomination category below and select your 3 favorite choices. Simply drag your selections to the boxes
     to make your picks. You'll see that different categories are worth different points. Also, your top choices will
     be worth more (if you're right!) than your lower choices. Have fun!
    </p>

    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*" %>

    <jsp:useBean id="allNominees" scope="request" class="com.oscarparty.servlets.selection.AllOscarNominees" />

    <div class="expander-area">
    <div id="WinnerSelectionAccordion" class="accordion">
        <%
        List<OscarCategory> categories = allNominees.categoriesJava();
        for (OscarCategory eachCategory : categories) {
            %>
        <h3><%=eachCategory.name()%></h3>
        <div class="accordion-section">
            <div class="selection-area">
            <%
            List<String> categoryNominees = eachCategory.nomineesJava();
            for (String eachNominee : categoryNominees) {
                %>
                <div class="selection"><%=eachNominee%></div>
                <%
            } //end of each nominee
            %>
            </div>
            <%
            List<Integer> points = eachCategory.pointsJava();
            %>
            <div class="drop-area">
                <div class="drop top-pick"><%= points.get(0) %> points pick</div>
                <div class="drop mid-pick"><%= points.get(1) %> points pick</div>
                <div class="drop bot-pick"><%= points.get(2) %> point pick</div>
            </div>
        </div>
        <%
        } //end of category
        %>

     </div>
     </div>

    <button type="Submit"/>
</body>
</html>