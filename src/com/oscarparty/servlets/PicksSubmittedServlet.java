package com.oscarparty.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class PicksSubmittedServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Map<String, String[]> parameterMap = req.getParameterMap();
        Set<Map.Entry<String, String[]>> paramEntries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> paramKey : paramEntries) {
            res.getWriter().println("Parameter key: " + paramKey.getKey() + " with value " + Arrays.toString(paramKey.getValue()));
        }

    }
}
