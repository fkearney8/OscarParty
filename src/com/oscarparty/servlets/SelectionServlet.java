package com.oscarparty.servlets;

import com.oscarparty.servlets.selection.AllOscarNominees;
import com.oscarparty.servlets.selection.OscarCategory;

import java.io.*;
import java.util.List;
import java.util.Locale;
import javax.servlet.*;
import javax.servlet.http.*;

public class SelectionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selection.jsp");
        AllOscarNominees allOscarNominees = new AllOscarNominees();

        List<OscarCategory> categories = allOscarNominees.categoriesJava();

        req.setAttribute("allNominees", allOscarNominees);
        requestDispatcher.forward(req, res);
    }
}
