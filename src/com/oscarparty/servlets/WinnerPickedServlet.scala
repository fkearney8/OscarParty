package com.oscarparty.servlets

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.oscarparty.servlets.winners.{WinnerDAO, Winner}
import com.oscarparty.servlets.selection.AllOscarNominees

class WinnerPickedServlet extends HttpServlet {
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val winnerCat = req.getParameter("winnerCategory")
    val winnerSelectedString = req.getParameter("winnerSelected")
    val nextCat = req.getParameter("nextCategory")

    req.setAttribute("nextCategory", "-None Selected-")
    //validate the next Category and set if we have one
    if (!nextCat.equals("None")) {
      new AllOscarNominees().findCategory(nextCat)
      //TODO save the next cat when we have a table for it
      req.setAttribute("nextCategory", nextCat)
    }

    //validate the winner category and nominee, and save
    req.setAttribute("winner", "-None Saved-")
    req.setAttribute("category", "-None Saved-")
    if (!winnerCat.equals("None")) {
      val winnerObject = new Winner(winnerCat, winnerSelectedString)
      val winnerOscarCategory = new AllOscarNominees().findCategory(winnerCat)
      if (!winnerOscarCategory.nominees.contains(winnerSelectedString)) {
        throw new IllegalArgumentException("Winner " + winnerSelectedString + " is not a nominee for " + winnerCat)
      }
      //save
      new WinnerDAO().saveWinner(winnerObject)
      req.setAttribute("winner", winnerObject.winner)
      req.setAttribute("category", winnerObject.category)
    }

    req.getServletContext.getRequestDispatcher("/winnerPicked.jsp").forward(req, resp)
  }
}
