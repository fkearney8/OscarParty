package com.oscarparty.servlets

import com.oscarparty.servlets.data.nominees.AllOscarNominees2015
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import scala.util.parsing.json.{JSONArray, JSONObject}
import com.oscarparty.servlets.data.NextCategory

class WinnerPickerServlet extends HttpServlet {
  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val aon = new AllOscarNominees2015()
    val orderedCatsWithoutWinners: Array[String] = aon.categoriesWithoutWinners.map {_.name}.toArray

    //want a map of remaining category names to the nominees for that category to make things easy in the jsp and for fun
    val catsToNomsMap = {
      for {
        categoryName <- orderedCatsWithoutWinners
      } yield categoryName -> aon.categoryNominees(aon.findCategoryByName(categoryName))
    }.toMap

    val catsToNomsJsonArrays = for ((categoryName, nominees) <- catsToNomsMap)
        yield categoryName -> new JSONArray(nominees)
    val catsToNomsJson = new JSONObject(catsToNomsJsonArrays).toString()

    req.setAttribute("orderedCatsWithoutWinners", orderedCatsWithoutWinners)
    req.setAttribute("catsToNomsMap", catsToNomsJson)
    req.setAttribute("nextCategory", NextCategory.nextCategory)

    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
  }
}