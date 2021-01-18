package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.data.dao.{PlayerDao, PlayerPicksDao, WinnersDao}
import com.oscarparty.data.nominees.{CategoryName, Nominee}
import com.oscarparty.data.{CategoryPicks, PlayerPicks, Winner}
import com.oscarparty.servlets.playerpicks.Calculator

import scala.collection.JavaConverters._

/**
 * Servlet for showing the picks that a player has made.
 */
@Singleton
class PlayerPicksReviewServlet @Inject() (playerDao: PlayerDao,
                                          playerPicksDao: PlayerPicksDao,
                                          winnersDao: WinnersDao,
                                          calculator: Calculator) extends HttpServlet {


  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    val playerId = req.getParameter("playerId")
    val player = playerDao.getPlayerById(playerId)
    val playerPicks = playerPicksDao.getPlayerPicks(player.id)
    val playerPoints = calculator.calculatePickPoints(playerPicks)

    val displayableCategories: Seq[DisplayableCategory] = createDisplayableCategories(playerId, playerPoints, playerPicks)

    req.setAttribute("displayableCategoryPicks", displayableCategories.asJava)
    req.setAttribute("playerName", player.name)
    req.setAttribute("totalPoints", playerPoints)

    getServletContext.getRequestDispatcher("/playerPicksReview.jsp").forward(req, resp)
  }

  private def isAWinner(winnerOption: Option[Winner], picked: Nominee): Boolean = {
    winnerOption match {
      case None => false
      case Some(winner) =>
        winner.winningNominee.name == picked.name
    }
  }

  private def displayableCategoryPicksFromCategoryPicks(picksForCat: CategoryPicks): DisplayableCategory = {
    val winningNominee = winnersDao.winnerForCategory(picksForCat.categoryName)
    val picksAndPoints = picksForCat.picksInOrder zip picksForCat.categoryName.points.pointsInOrder
    val displayablePicks = picksAndPoints.map { case (pick, points) =>
      DisplayablePick(pick.name, isAWinner(winningNominee, pick), points)
    }

    DisplayableCategory(picksForCat.categoryName.toString, winningNominee.isDefined, displayablePicks)
  }

  def createDisplayableCategories(playerId: String,
                                  playerPoints: Int,
                                  playerPicks: PlayerPicks): Seq[DisplayableCategory] = {

    CategoryName.values.toSeq.sorted.flatMap { cat =>
      val maybePicksForCat = playerPicks.picksForCategory(cat)

      val maybeDisplayableCat: Option[DisplayableCategory] = maybePicksForCat.map { picksForCat: CategoryPicks =>
        displayableCategoryPicksFromCategoryPicks(picksForCat)
      }
      maybeDisplayableCat
    }

  }

}

case class DisplayableCategory(categoryName : String,
                               categoryHasAWinner: Boolean,
                               picks: List[DisplayablePick]) {

  val pickedAWinnerInThisCategory: Boolean = picks.exists(_.isAWinner)

  def pickAt(index: Int): DisplayablePick = picks(index)
}

case class DisplayablePick(nomineeSelected: String,
                           isAWinner: Boolean,
                           pointValue: Int)