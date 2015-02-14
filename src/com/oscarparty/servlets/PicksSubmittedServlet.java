package com.oscarparty.servlets;

import com.oscarparty.servlets.playerpicks.CategoryPicks;
import com.oscarparty.servlets.playerpicks.PlayerPicks;
import com.oscarparty.servlets.playerpicks.PlayerPicksDAO;
import scala.collection.mutable.DefaultEntry;
import scala.collection.mutable.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Servlet for when a player has submitted picks to be saved.
 */
public class PicksSubmittedServlet extends HttpServlet {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> paramEntries = parameterMap.entrySet();
        PlayerPicks playerPicks = new PlayerPicks();
        PrintWriter writer = res.getWriter();
        writer.println("Your picks have been submitted!");
        for (Map.Entry<String, String[]> paramKey : paramEntries) {
            String pickKey = paramKey.getKey();
            String[] pickValues = paramKey.getValue();
            writer.println("Parameter key: " + pickKey + " with value " +
                    Arrays.toString(pickValues));

            if (pickValues.length > 1) {
                throw new ServletException("More pick values than allowed.");
            } else if (pickValues.length == 1) {
                String pick = pickValues[0];
                if (pickKey.equals("userName")) {
                    playerPicks.userName_$eq(pick);
                } else {
                    //result params are like "Best Actress in a Supporting Role.botPick" = "[Amy Adams]"
                    int indexOfDot = pickKey.indexOf(".");
                    if (indexOfDot < 0) throw new ServletException("Pick key is malformed: " + pickKey);
                    String categoryName = pickKey.substring(0, indexOfDot);
                    String pickPriority = pickKey.substring(indexOfDot + 1);
                    playerPicks.addPick(categoryName, pickPriority, pick);
                }
            }
        }
        if (!playerPicks.arePicksValid()) {
            throw new IllegalArgumentException("Picks were not valid!");
        }
        PlayerPicksDAO playerPicksDAO = new PlayerPicksDAO();
        playerPicksDAO.storePicks(playerPicks);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/picksSubmitted.jsp");
        dispatcher.forward(req, res);
    }

}
