package com.oscarparty.servlets

import com.oscarparty.servlets.winners.Categories
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.oscarparty.servlets.selection.AllOscarNominees
import scala.collection.JavaConversions._
import scala.util.parsing.json.{JSONArray, JSONObject}

class WinnerPickerServlet extends HttpServlet {
  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val orderedCatsWithoutWinners: Array[String] = Categories.categoriesWithoutWinners()

    val aon = new AllOscarNominees()
    //want a map of remaining category names to the nominees for that category to make things easy in the jsp and for fun
    val catsToNomsMap = (for (categoryName <- orderedCatsWithoutWinners)
        yield categoryName -> aon.findCategory(categoryName).nominees).toMap

//    val catsToNomsMapJsonArrays : java.util.Map[String, Array[String]] = mapAsJavaMap(catsToNomsMap)
    val catsToNomsJsonArrays = for ((categoryName, nomineesArray) <- catsToNomsMap)
        yield categoryName -> new JSONArray(nomineesArray.toList)
    val catsToNomsJson = new JSONObject(catsToNomsJsonArrays).toString()

    req.setAttribute("orderedCatsWithoutWinners", orderedCatsWithoutWinners)
    req.setAttribute("catsToNomsMap", catsToNomsJson)

    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
  }
}