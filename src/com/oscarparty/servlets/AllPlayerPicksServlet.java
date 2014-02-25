package com.oscarparty.servlets;

import com.oscarparty.servlets.playerpicks.PlayerPicks;
import com.oscarparty.servlets.playerpicks.PlayerPicksDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AllPlayerPicksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get all the player picks
        PlayerPicksDAO playerPicksDao = new PlayerPicksDAO();
        List<PlayerPicks> allPlayerPicks = playerPicksDao.readAllPlayerPicks();
        req.setAttribute("allPlayerPicks", allPlayerPicks);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/allPicks.jsp");
        requestDispatcher.forward(req, resp);
    }
}
