package com.oscarparty.servlets

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.oscarparty.servlets.data.nominees.OscarNomineesDAO._

import scala.util.parsing.json.{JSONArray, JSONObject}
import com.oscarparty.servlets.data.NextCategory

class WinnerPickerServlet extends HttpServlet {
  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {

    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    val catsToNomsJsonArrays = categoriesWithoutWinners.map { category =>
      category.name -> new JSONArray(category.nominees)
    }.toMap
    val catsToNomsJson = new JSONObject(catsToNomsJsonArrays).toString()

    req.setAttribute("orderedCatsWithoutWinners", categoriesWithoutWinners.map { _.name }.toArray)
    req.setAttribute("catsToNomsMap", catsToNomsJson)
    req.setAttribute("nextCategory", NextCategory.nextCategory)

    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
  }
}