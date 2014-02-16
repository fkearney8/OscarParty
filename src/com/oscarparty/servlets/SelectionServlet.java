package com.oscarparty.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SelectionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selection.jsp");
        requestDispatcher.forward(req, res);


//        res.setContentType("text/html");
//        PrintWriter out = res.getWriter();
//
//        out.println("<HTML>");
//        out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
//        out.println("<BODY>");
//        out.println("<BIG>Hello World</BIG>");
//        out.println("</BODY></HTML>");
    }
}
