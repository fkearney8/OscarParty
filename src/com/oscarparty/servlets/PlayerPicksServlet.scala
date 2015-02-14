package com.oscarparty.servlets

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.oscarparty.servlets.data.nominees.AllOscarNominees2015
import com.oscarparty.servlets.playerpicks.{PlayerPicks, Calculator, PlayerPicksDAO}
import scala.collection.mutable.ArrayBuffer
import com.oscarparty.servlets.winners.{Winner, WinnerDAO}
import collection.JavaConverters._

/**
 * Servlet for showing the picks that a player has made.
 */
class PlayerPicksServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val playerName = req.getParameter("playerName")
    val playerPicks = new PlayerPicksDAO().readLastPicksForUsername(playerName)
    val playerPoints = Calculator.calculatePickPoints(playerPicks)

    val displayablePicks = new DisplayablePlayerPicks(playerName, playerPoints.toString)
    displayablePicks.setPicks(playerPicks)
    req.setAttribute("playerPicks", displayablePicks)

    getServletContext.getRequestDispatcher("/playerPicks.jsp").forward(req, resp)
  }
}

class DisplayablePlayerPicks (val playerName : String, val playerPoints : String) {
  val categories = new ArrayBuffer[DisplayableCategory]()
  val aon = new AllOscarNominees2015()

  def setPicks(playerPicks : PlayerPicks) {
    val cats = playerPicks.categoryPicksInOrder()
    for (cat <- cats) {
      var selectedWinnerInCategory = false
      val oscarCat = aon.findCategory(cat.categoryName)
      val orderedPoints = oscarCat.points
      val picksAndPoints = cat.getOrderedPicks zip orderedPoints

      val displayableOrderedPicks = for ((eachPick, points) <- picksAndPoints) yield new DisplayablePick(eachPick,
      //is this pick a winner?
      {
        val winner: Winner = WinnerDAO.findCategoryWinner(cat.categoryName)
        if (winner != null) {
          if (winner.winner.equals(eachPick)) {
            selectedWinnerInCategory = true
            true
          } else false
        } else false
      },
      points)

      val displayCat: DisplayableCategory = new DisplayableCategory(cat.categoryName, displayableOrderedPicks)
      displayCat.categoryAlreadySelected = WinnerDAO.findCategoryWinner(cat.categoryName) != null
      displayCat.pickedAWinnerInThisCategory = selectedWinnerInCategory

      categories += displayCat
    }
  }

  def getDisplayCategoriesJava: java.util.List[DisplayableCategory] = {
    categories.asJava
  }
}

class DisplayableCategory(val categoryName : String,
                          val topPick : DisplayablePick,
                          val midPick : DisplayablePick,
                          val botPick : DisplayablePick) {
  var pickedAWinnerInThisCategory = false
  var categoryAlreadySelected = false

  def this(categoryName : String, picksInOrder : Array[DisplayablePick]) = {
    this(categoryName, picksInOrder(0), picksInOrder(1), picksInOrder(2))
  }
}

class DisplayablePick(val nomineeSelected : String,
                      val isAWinner : Boolean,
                      val pointValue : Int) {
}