<html>
<head>
    <%@include file="resources.jsp"%>
    <style>
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
            text-align: center;
            z-index: -1;
            background: #443113;
        }
        .drop-ready {
            border-color: #FFFFFF;
            background: #554224;
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
                    $(this).find("input").val("");
                }
            });

            $("#modalPopover").dialog({
                autoOpen: false,
                draggable: false,
                modal: true,
                position: {my: "bottom", at: "bottom", of: $(".accordion")}
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
            this.textDropped = ui.draggable.text();
            //put it in the corresponding hidden input
            $(this).find("input").val(this.textDropped);
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

        function submitForm() {
            var userName = $("#userName").val();
            if (userName.trim().length == 0) {
                $("#modalPopover").text("Don't forget to add a Player Name");
                $("#modalPopover").dialog("open");
                return;
            }
            var allValid = true;
            var inputs = $(".drop").find("input");
            var firstMissing = true;
            inputs.each(function(index) {
                var inputName = $(this).attr("name");
                var inputValue = $(this).val();
                console.log("For name " + inputName + " Value is " + inputValue);
                if (inputValue.trim().length == 0) {
                    allValid = false;
                    if (firstMissing) {
                        var categoryName = inputName.substring(0, inputName.indexOf("."));
                        $("#modalPopover").text("Picks missing for " + categoryName);
                        $("#modalPopover").dialog("open");
                        firstMissing = false;
                    }
                }
            });

            if (allValid)
                $("form").submit();
        }

    </script>
</head>
<body>
    <%@include file="header.jsp"%>
    <h2>Welcome to the Oscar Party!</h2>

    <p style="width: 40%; font-size: 18px;">
    Hello Friends,
    After last year's success, we're doing this again. Below you will find
    all the Oscar nominations listed by category. Everyone will select their favorites, pony up a fiver, and we'll
    see who knows the movies the best. Or maybe who can identify the Academy's biases the best. Anyway, it should
    make for some extra fun while watching the Oscars.<br/>
    <br/>
    <b>Instructions:</b><br/>
     Open each nomination category below and select your 3 favorite choices. Simply drag your selections from the left
     to the boxes on the right
     to make your picks. You'll see that different categories are worth different points. Also, your top choices will
     be worth more (if you're right!) than your lower choices. There will be an awesome leaderboard at the Party
     so don't worry about saving your picks. Ways to send Frank $5 will follow after you make your picks. Have fun!
    </p>

    <%@ page import="java.util.*, com.oscarparty.servlets.selection.*" %>

    <div id="modalPopover" style="text-align: center"></div>

    <jsp:useBean id="allNominees" scope="request" class="com.oscarparty.servlets.data.nominees.AllOscarNominees2015" />

    <form method="post" action="submitPicks.do">
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
                <div class="drop topPick"><%= points.get(0) %> points pick
                    <input type="hidden" name="<%=eachCategory.name()%>.topPick"/>
                </div>
                <div class="drop midPick"><%= points.get(1) %> points pick
                    <input type="hidden" name="<%=eachCategory.name()%>.midPick"/>
                </div>
                <div class="drop botPick">
                    <%
                    int botPointsVal = points.get(2);
                    String point_s_string = botPointsVal > 1 ? "points" : "point";
                    %>
                    <%= botPointsVal %> <%= point_s_string %> pick
                    <input type="hidden" name="<%=eachCategory.name()%>.botPick"/>
                </div>
            </div>
        </div>
        <%
        } //end of category
        %>

     </div>
     </div>
     <br/>
     <div>Give a player name, so we can track a winner: <input id="userName" name="userName" type="text" label"Your Name"/></div>
    <input type="button" value="Submit Picks!" onclick="submitForm()"/>
    </form>
</body>
</html>