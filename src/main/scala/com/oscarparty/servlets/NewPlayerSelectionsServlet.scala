package com.oscarparty.servlets

import java.util.{List => JList}
import java.util.LinkedList
import javax.inject.Singleton
import javax.servlet.RequestDispatcher
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.data.nominees.CategoryName

import collection.JavaConverters._

@Singleton
class NewPlayerSelectionsServlet extends HttpServlet {

  override def doGet(req: HttpServletRequest, res: HttpServletResponse): Unit = { //This is necessary to get the response JSP to use the right encoding on variables
    // inserted into the page, like the "-" in the nominees names.  Can do this in a filter if we need to:
    // see http://stackoverflow.com/questions/138948/how-to-get-utf-8-working-in-java-webapps
    res.setContentType("text/html; charset=UTF-8")
    res.setCharacterEncoding("UTF-8")

    val allCategories: JList[CategoryName.Value] = CategoryName.values.toSeq.asJava
    //play the JSP game, moving the categories into a LinkedList
    req.setAttribute("allCategories", allCategories)

    val requestDispatcher = getServletContext.getRequestDispatcher("/playerSelection.jsp")
    requestDispatcher.forward(req, res)
  }
}
