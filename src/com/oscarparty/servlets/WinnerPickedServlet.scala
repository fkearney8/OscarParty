//package com.oscarparty.servlets
//
//import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
//
//import com.oscarparty.servlets.data.WinnersDAO.WinnerData
//import com.oscarparty.servlets.data.nominees.OscarNomineesDAO
//import com.oscarparty.servlets.data.{NextCategory, WinnersDAO}
//
//class WinnerPickedServlet extends HttpServlet {
//  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
//    resp.setContentType("text/html; charset=UTF-8")
//    resp.setCharacterEncoding("UTF-8")
//
//    val winnerCatId = req.getParameter("winnerCategory")
//    val winnerSelectedId = req.getParameter("winnerSelected")
//    val nextCatString = req.getParameter("nextCategory")
//
//    req.setAttribute("nextCategory", "-None Selected-")
//    //validate the next Category and set if we have one
//    if (!nextCatString.equals("None")) {
//      val nextCategoryId: Int = Integer.parseInt(nextCatString)
//      OscarNomineesDAO.getCategoryMaybe(nextCategoryId).map { category =>
//        NextCategory.setNextCategory(nextCategoryId)
//        req.setAttribute("nextCategory", category.name)
//      }
//    }
//
//    val winnerCategoryOption = parseIdStringToObject(idInt => OscarNomineesDAO.getCategoryMaybe(idInt))(winnerCatId)
//    val winnerSelectedOption = parseIdStringToObject(idInt => OscarNomineesDAO.getNomineeMaybe(idInt))(winnerSelectedId)
//
//    (winnerCategoryOption, winnerSelectedOption) match {
//      case (Some(winnerCategory), Some(winnerSelected)) =>
//
//        val winnerObject = WinnerData(winnerCategory.id, winnerSelected.id)
//        val nomsForCat = winnerCategory.nominees
//
//        if (!nomsForCat.exists(_.name == winnerSelected.name)) {
//          throw new IllegalArgumentException(s"Winner ${winnerSelected.name} is not a nominee for ${winnerCategory.name}")
//        }
//        //save
//        WinnersDAO.saveWinner(winnerObject)
//        req.setAttribute("winner", winnerSelected.name)
//        req.setAttribute("category", winnerCategory.name)
//
//      case _ =>
//        req.setAttribute("winner", "-None Selected-")
//        req.setAttribute("category", "-None Selected-")
//    }
//
//    req.getServletContext.getRequestDispatcher("/winnerPicked.jsp").forward(req, resp)
//  }
//
//  /* parse this id string with the given function if it is really an integer. */
//  private def parseIdStringToObject[T] (convertIntToObject: (Int) => Option[T])(idString: String): Option[T] = {
//    try {
//      val idInt = Integer.parseInt(idString)
//      convertIntToObject(idInt)
//    } catch {
//      case nfe: NumberFormatException => None
//    }
//  }
//}
