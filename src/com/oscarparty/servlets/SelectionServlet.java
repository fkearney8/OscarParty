package com.oscarparty.servlets;

import com.oscarparty.servlets.data.nominees.AllOscarNominees2015;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelectionServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {


        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selection.jsp");
        AllOscarNominees2015 allOscarNominees = new AllOscarNominees2015();

        req.setAttribute("allNominees", allOscarNominees);
        requestDispatcher.forward(req, res);
    }
}
