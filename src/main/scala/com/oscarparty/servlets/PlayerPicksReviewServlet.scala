//package com.oscarparty.servlets
//
//import javax.inject.Inject
//import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
//
//import com.oscarparty.data.dao.{PlayerDAO, PlayerPicksDAO}
//import com.oscarparty.servlets.playerpicks.Calculator
//
//import scala.collection.JavaConverters._
//
///**
// * Servlet for showing the picks that a player has made.
// */
//class PlayerSelectionServlet @Inject() (val playerDao: PlayerDAO,
//                                        val playerPicksDao: PlayerPicksDAO,
//                                        val calculator: Calculator) extends HttpServlet {
//
//  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
//    resp.setContentType("text/html; charset=UTF-8")
//    resp.setCharacterEncoding("UTF-8")
//
//    val playerName = req.getParameter("playerName")
//    val player = playerDao.getPlayerByName(playerName).get
//    val playerPicks = playerPicksDao.getPlayerPicks(player.id)
//    val playerPoints = calculator.calculatePickPoints(playerPicks)
//
//    val displayablePicks = new DisplayablePlayerPicks(playerName, playerPoints.toString, playerPicks)
//    req.setAttribute("playerPicks", displayablePicks)
//
//    getServletContext.getRequestDispatcher("/playerPicks.jsp").forward(req, resp)
//  }
//}
//
//class DisplayablePlayerPicks (val playerName : String, val playerPoints : String, playerPicks : List[PlayerPick]) {
//
//  val categories: Array[DisplayableCategory] = {
//    OscarNomineesDAO.getCategories.flatMap { cat =>
//      val winningNominee = WinnersDAO.findCategoryWinner(cat.id)
//      val displayablePicksOption: Option[Seq[DisplayablePick]] = playerPicks.find(_.category == cat.id).map { picksForCat =>
//        val orderedPoints = List(cat.points1, cat.points2, cat.points3)
//        val orderedPicks = List(picksForCat.topPick, picksForCat.midPick, picksForCat.botPick)
//        val picksAndPoints = orderedPicks zip orderedPoints
//
//        picksAndPoints.map { case (pick, points) =>
//          val nomineePicked = OscarNomineesDAO.getNominee(pick)
//          new DisplayablePick(nomineePicked.name, isAWinner(winningNominee, nomineePicked), points)
//        }
//      }
//
//      displayablePicksOption.map { displayablePicks =>
//        val displayableCategory = new DisplayableCategory(cat.name, displayablePicks)
//        displayableCategory.categoryAlreadySelected = winningNominee.isDefined
//        displayableCategory.pickedAWinnerInThisCategory = displayablePicks.exists(_.isAWinner)
//        displayableCategory
//      }
//
//    }.toArray
//  }
//
//  private def isAWinner(winnerOption: Option[Winner], picked: Nominee): Boolean = {
//    winnerOption match {
//      case None => false
//      case Some(winner) =>
//        winner.winningNominee.name == picked.name
//    }
//  }
//
//
//  def getDisplayCategoriesJava: java.util.List[DisplayableCategory] = {
//    categories.toList.asJava
//  }
//}
//
//class DisplayableCategory(val categoryName : String,
//                          val topPick : DisplayablePick,
//                          val midPick : DisplayablePick,
//                          val botPick : DisplayablePick) {
//  var pickedAWinnerInThisCategory = false
//  var categoryAlreadySelected = false
//
//  def this(categoryName : String, picksInOrder : Seq[DisplayablePick]) = {
//    this(categoryName, picksInOrder(0), picksInOrder(1), picksInOrder(2))
//  }
//}
//
//class DisplayablePick(val nomineeSelected : String,
//                      val isAWinner : Boolean,
//                      val pointValue : Int) {
//}