package com.oscarparty.servlets;

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
import java.util.Map;
import java.util.Set;

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
        PrintWriter writer = res.getWriter();
        writer.println("Your picks have been submitted!");
        for (Map.Entry<String, String[]> paramKey : paramEntries) {
            writer.println("Parameter key: " + paramKey.getKey() + " with value " +
                    Arrays.toString(paramKey.getValue()));
        }
        try {
            storePicks(parameterMap);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        
    }

    static final String USER = "postgres";
    static final String PASS = "Amasa1217";
    private void storePicks(Map<String, String[]> parameterMap) throws SQLException {
        System.out.println("Connecting to database...");
        String dbUrl = "jdbc:postgresql:OscarParty";
        Connection conn = DriverManager.getConnection(dbUrl, USER, PASS);
        conn.setAutoCommit(true);
        Statement storePicksStatement = conn.createStatement();
        String insertPicksSql = insertPicksSql(parameterMap);
        storePicksStatement.executeUpdate(insertPicksSql);
    }

    private String insertPicksSql(Map<String, String[]> parameterMap) {
        StringBuilder insertStatement = new StringBuilder("INSERT INTO userpicks");
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        boolean first = true;
        Set<Map.Entry<String, String[]>> paramEntries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> paramEntry : paramEntries) {
            String colName = paramEntry.getKey().replace(" ", "_");
            colName = colName.replace(".", "_");
            if (!first) {
                columns.append(", ");
                values.append(", ");
            }
            columns.append(colName);
            String[] valuesArray = paramEntry.getValue();
            if (valuesArray.length > 1) {
                throw new RuntimeException("Incorrect values array size: " + valuesArray.length);
            } else if (valuesArray.length == 1 && valuesArray[0].trim().length() > 0) {
                values.append("'").append(valuesArray[0]).append("'");
            } else {
                values.append("'").append("none").append("'");
            }
            first = false;
        }

        columns.append(")");
        values.append(")");
        insertStatement.append(columns).append(" values ").append(values).append(";");
        return insertStatement.toString();
    }
}
