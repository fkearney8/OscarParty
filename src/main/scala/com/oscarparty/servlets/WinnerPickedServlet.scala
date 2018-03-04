package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.data.{NextCategory, Winner}
import com.oscarparty.data.dao.WinnersDAO
import com.oscarparty.data.nominees.{CategoryName, Nominee, Nominees2018}

import scala.util.Try

@Singleton
class WinnerPickedServlet @Inject() (winnersDao: WinnersDAO) extends HttpServlet {

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    val winnerCatString = req.getParameter("winnerCategory")
    val winnerSelectedString = req.getParameter("winnerSelected")

    val nextCatString = req.getParameter("nextCategory")

    req.setAttribute("nextCategory", "-None Selected-")
    //validate the next Category and set if we have one
    if (!nextCatString.equals("None")) {

      val nextCategory = CategoryName.withName(nextCatString)
      NextCategory.nextCategory = nextCategory
      req.setAttribute("nextCategory", nextCategory.displayName)
    }

    val maybeWinnerCategory: Option[CategoryName.Value] = Try(CategoryName.withName(winnerCatString)).toOption
    val maybeWinnerSelected: Option[Nominee] = maybeWinnerCategory.flatMap { winnerCategory =>
      Try(Integer.parseInt(winnerSelectedString)).toOption.map { winnerNomIndex =>
        Nominees2018.nomineesForCategory(winnerCategory).nominee(winnerNomIndex)
      }
    }

    (maybeWinnerCategory, maybeWinnerSelected) match {
      case (Some(winnerCategory), Some(winnerSelected)) =>
        val winnerObject = Winner(winnerCategory, winnerSelected)

        winnersDao.saveWinner(winnerObject)
        req.setAttribute("category", winnerCategory.displayName)
        req.setAttribute("winner", winnerSelected.name)

      case _ =>
        req.setAttribute("category", "-None Selected-")
        req.setAttribute("winner", "-None Selected-")
    }

    req.getServletContext.getRequestDispatcher("/winnerPicked.jsp").forward(req, resp)
  }
}
