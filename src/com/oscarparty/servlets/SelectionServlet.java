//package com.oscarparty.servlets;
//
//import com.oscarparty.servlets.data.nominees.Category;
//import com.oscarparty.servlets.data.nominees.OscarNomineesDAO;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//
//public class SelectionServlet extends HttpServlet {
//
//    public void doGet(HttpServletRequest req, HttpServletResponse res)
//            throws ServletException, IOException {
//
//        //This is necessary to get the response JSP to use the right encoding on variables
//        // inserted into the page, like the "-" in the nominees names.  Can do this in a filter if we need to:
//        // see http://stackoverflow.com/questions/138948/how-to-get-utf-8-working-in-java-webapps
//        res.setContentType("text/html; charset=UTF-8");
//        res.setCharacterEncoding("UTF-8");
//
//        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selection.jsp");
//        LinkedList<Category> allCategories = new LinkedList<>();
//        //play the JSP game, moving the categories into a LinkedList
//        for (Category eachCategory: OscarNomineesDAO.categoriesJava()) {
//          allCategories.add(eachCategory);
//        }
//
//        req.setAttribute("allCategories", allCategories);
//        requestDispatcher.forward(req, res);
//    }
//}
