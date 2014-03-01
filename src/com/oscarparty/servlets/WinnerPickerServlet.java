package com.oscarparty.servlets;

import com.oscarparty.servlets.winners.Categories;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WinnerPickerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //need a list of all categories that don't have a winner
        String[] catsWithoutWinners = Categories.categoriesWithoutWinners();
        req.setAttribute("catsWithoutWinners", catsWithoutWinners);

        req.getServletContext().getRequestDispatcher("/winnerPicker.jsp").forward(req, resp);
    }
}
