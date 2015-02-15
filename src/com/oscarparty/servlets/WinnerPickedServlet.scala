package com.oscarparty.servlets

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.servlets.data.WinnersDAO.WinnerData
import com.oscarparty.servlets.data.nominees.AllOscarNominees2015
import com.oscarparty.servlets.data.{NextCategory, WinnersDAO}

class WinnerPickedServlet extends HttpServlet {
  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val winnerCat = req.getParameter("winnerCategory")
    val winnerSelectedString = req.getParameter("winnerSelected")
    val nextCat = req.getParameter("nextCategory")

    req.setAttribute("nextCategory", "-None Selected-")
    //validate the next Category and set if we have one
    if (!nextCat.equals("None")) {
      new AllOscarNominees2015().findCategoryByName(nextCat)
      NextCategory.nextCategory = nextCat
      req.setAttribute("nextCategory", nextCat)
    }

    //validate the winner category and nominee, and save
    req.setAttribute("winner", "-None Saved-")
    req.setAttribute("category", "-None Saved-")
    if (!winnerCat.equals("None") && !winnerSelectedString.equals("None")) {

      val aon = new AllOscarNominees2015()
      val winnerObject = WinnerData(aon.findCategoryByName(winnerCat).id, aon.findNomineeByName(winnerSelectedString).id)
      val winnerOscarCategory = aon.findCategoryByName(winnerCat)
      val nomsForCat = aon.categoryNominees(winnerOscarCategory)
      if (!nomsForCat.contains(winnerSelectedString)) {
        throw new IllegalArgumentException("Winner " + winnerSelectedString + " is not a nominee for " + winnerCat)
      }
      //save
      WinnersDAO.saveWinner(winnerObject)
      req.setAttribute("winner", winnerCat)
      req.setAttribute("category", winnerSelectedString)
    }

    req.getServletContext.getRequestDispatcher("/winnerPicked.jsp").forward(req, resp)
  }
}
