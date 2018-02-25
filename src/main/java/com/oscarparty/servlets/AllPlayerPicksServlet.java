//package com.oscarparty.servlets;
//
//
//import com.oscarparty.data.PlayerPicksDAO;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//import scala.collection.JavaConverters;
//
//public class AllPlayerPicksServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/html; charset=UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//
//
//        //get all the player picks
//        List<PlayerPicksDAO.PlayerPick> allPlayerPicks = JavaConverters.asJavaListConverter(PlayerPicksDAO.allPlayerPicks()).asJava();
//        req.setAttribute("allPlayerPicks", allPlayerPicks);
//        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/allPicks.jsp");
//        requestDispatcher.forward(req, resp);
//    }
//}
