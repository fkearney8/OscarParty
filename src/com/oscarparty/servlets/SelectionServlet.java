package com.oscarparty.servlets;

import com.oscarparty.servlets.data.nominees.AllOscarNominees2014;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SelectionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selection.jsp");
        AllOscarNominees2014 allOscarNominees = new AllOscarNominees2014();

        req.setAttribute("allNominees", allOscarNominees);
        requestDispatcher.forward(req, res);
    }
}
