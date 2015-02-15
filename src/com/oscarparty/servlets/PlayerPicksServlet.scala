package com.oscarparty.servlets

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.servlets.data.PlayerPicksDAO.PlayerPick
import com.oscarparty.servlets.data.WinnersDAO.Winner
import com.oscarparty.servlets.data.nominees.{AllOscarNominees2015, Nominee}
import com.oscarparty.servlets.data.{PlayerPicksDAO, WinnersDAO}
import com.oscarparty.servlets.playerpicks.Calculator

import scala.collection.JavaConverters._

/**
 * Servlet for showing the picks that a player has made.
 */
class PlayerPicksServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val playerName = req.getParameter("playerName")
    val player = PlayerPicksDAO.getPlayer(playerName).get
    val playerPicks = PlayerPicksDAO.picksByPlayer(player.id)
    val playerPoints = Calculator.calculatePickPoints(playerPicks)

    val displayablePicks = new DisplayablePlayerPicks(playerName, playerPoints.toString, playerPicks)
    req.setAttribute("playerPicks", displayablePicks)

    getServletContext.getRequestDispatcher("/playerPicks.jsp").forward(req, resp)
  }
}

class DisplayablePlayerPicks (val playerName : String, val playerPoints : String, playerPicks : List[PlayerPick]) {

  private val aon = new AllOscarNominees2015()
  val categories: Array[DisplayableCategory] = {
    aon.getCategories.flatMap { cat =>
      val winningNominee = WinnersDAO.findCategoryWinner(cat.id)
      val displayablePicksOption: Option[Seq[DisplayablePick]] = playerPicks.find(_.category == cat.id).map { picksForCat =>
        val orderedPoints = List(cat.points1, cat.points2, cat.points3)
        val orderedPicks = List(picksForCat.topPick, picksForCat.midPick, picksForCat.botPick)
        val picksAndPoints = orderedPicks zip orderedPoints

        picksAndPoints.map { case (pick, points) =>
          val nomineePicked = aon.getNominee(pick)
          new DisplayablePick(nomineePicked.name, isAWinner(winningNominee, nomineePicked), points)
        }
      }

      displayablePicksOption.map { displayablePicks =>
        val displayableCategory = new DisplayableCategory(cat.name, displayablePicks)
        displayableCategory.categoryAlreadySelected = winningNominee.isDefined
        displayableCategory.pickedAWinnerInThisCategory = displayablePicks.exists(_.isAWinner)
        displayableCategory
      }

    }.toArray
  }

  private def isAWinner(winnerOption: Option[Winner], picked: Nominee): Boolean = {
    winnerOption match {
      case None => false
      case Some(winner) =>
        winner.winningNominee.name == picked.name
    }
  }


  def getDisplayCategoriesJava: java.util.List[DisplayableCategory] = {
    categories.toList.asJava
  }
}

class DisplayableCategory(val categoryName : String,
                          val topPick : DisplayablePick,
                          val midPick : DisplayablePick,
                          val botPick : DisplayablePick) {
  var pickedAWinnerInThisCategory = false
  var categoryAlreadySelected = false

  def this(categoryName : String, picksInOrder : Seq[DisplayablePick]) = {
    this(categoryName, picksInOrder(0), picksInOrder(1), picksInOrder(2))
  }
}

class DisplayablePick(val nomineeSelected : String,
                      val isAWinner : Boolean,
                      val pointValue : Int) {
}