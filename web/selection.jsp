<html>
<head>
    <%@include file="resources.jsp"%>
    <style>
        .expander-area {width: 70%;}
        .accordion-section {
            height: 600px;
        }
        .selection-area {float: left; height: 600px;}
        .selection {
            width: 200px;
            height: 90px;
            margin: 20px;
            border: 3px outset;
            padding-left: 5px;
            text-align: center;
            vertical-align: middle;
        }
        .drop-area {float: right;}
        .drop {
            border-style: inset;
            border-width: 5px;
            width: 200px;
            height: 90px;
            margin: 20px;
            padding-left: 5px;
            z-index: -1;
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
            alert("dropped " + this.elementDropped);
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
<body>
    <%@include file="header.jsp"%>
    <h2>Welcome to the Oscar Party!</h2>
    <%= new String("Hello from the (index) JSP code!") %>

    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*" %>

    <jsp:useBean id="allNominees" scope="request" class="com.oscarparty.servlets.selection.AllOscarNominees" />
    Hello from the nominees bean: <%= allNominees.hiString() %>

    <%
    List<OscarCategory> categories = allNominees.categoriesJava();
    List<String> category1Nominees = categories.get(0).nomineesJava();
    for (String eachNominee : category1Nominees) {
        %>
        A nominee: <%=eachNominee%><br/>
        <%
    }
    %>

    <div class="expander-area">
    <div id="WinnerSelectionAccordion" class="accordion">
        <h3>Best Picture</h3>
        <div class="accordion-section">
            <div class="selection-area">
                <div style="float: left;">
                    <div class="selection">American Hustle</div>
                    <div class="selection">Captain Phillips</div>
                    <div class="selection">Dallas Buyers Club</div>
                    <div class="selection">Gravity</div>
                    <div class="selection">Her</div>
                </div>
                    <div style="float: left;">
                    <div class="selection">Nebraska</div>
                    <div class="selection">Philomena</div>
                    <div class="selection">12 Years a Slave</div>
                    <div class="selection">The Wolf of Wall Street</div>
                </div>
            </div>
            <div class="drop-area">
                <div class="drop 3points">3 points pick</div>
                <div class="drop 2points">2 points pick</div>
                <div class="drop 1points">1 point pick</div>
            </div>
        </div>
        <h3>Best Actor in a Leading Role</h3>
        <div class="accordion-section">
            <div class="selection">Christian Bale (American Hustle)</div>
            <div class="selection">Bruce Dern (Nebraska)</div>
            <div class="selection">Leonardo DiCaprio (The Wolf of Wall Street)</div>
            <div class="selection">Chiwetel Ejiofor (12 Years a Slave)</div>
            <div class="selection">Matthew McConaughey (Dallas Buyers Club)</div>
        </div>
    </div>
    </div>

    <button type="Submit"/>
</body>
</html>