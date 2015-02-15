package com.oscarparty.servlets

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.servlets.data.nominees.AllOscarNominees2015
import com.oscarparty.servlets.data.{WinnersDAO, NextCategory, PlayerPicksDAO}
import com.oscarparty.servlets.playerpicks.Calculator

import scala.collection.JavaConversions._

class LeaderboardServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val aon = new AllOscarNominees2015
    //we'll show best picture picks if there's no next category assigned
    var nextCategoryToDisplay: String = NextCategory.nextCategory
    if (nextCategoryToDisplay == null || nextCategoryToDisplay.equals("None") || nextCategoryToDisplay.trim.length == 0) {
      nextCategoryToDisplay = "Best Picture"
    }

    //read up all the picks
    val playerPicks = PlayerPicksDAO.picksPerPlayer

    val playersAndPointsUnsorted = playerPicks.map { case (player, listOfPicks) =>
      val playerName = player.name
      val points = Calculator.calculatePickPoints(listOfPicks)
      val nextCatPicks = PlayerPicksDAO.playerPicksForCategory(player.id, new AllOscarNominees2015().findCategoryByName(nextCategoryToDisplay).id)
      (playerName, points, nextCatPicks)
    }.toArray

    //to make things easier on the leaderboard page, we want the users in order by points
    val playersAndPoints = playersAndPointsUnsorted.sortWith(_._2 > _._2)
    val playerWithPointsArray = for ((player, points, nextCatPicks) <- playersAndPoints) yield {
      new PlayerWithPoints(player, points, {
        if (nextCatPicks != null)
          List(nextCatPicks.topPick, nextCatPicks.midPick, nextCatPicks.botPick).mkString(", ")
        else
          ""
      })
    }
    val javaPlayersWithPointsList : java.util.List[PlayerWithPoints] = asJavaList(playerWithPointsArray)
    req.setAttribute("usersAndPoints", javaPlayersWithPointsList)

    //get the winners list to put on the leaderboard
    //get the categories in order
    val categories = aon.getCategories
    //add winners where they exist
    val categoryWinners = categories.map { category =>
      new CategoryAndWinner(category.name, WinnersDAO.findCategoryWinner(category.id).fold("")(_.winningNominee.name))
    }
    val javaCategoryWinners : java.util.List[CategoryAndWinner] = asJavaList(categoryWinners)

    req.setAttribute("nextCategory", nextCategoryToDisplay)
    req.setAttribute("categoryWinners", javaCategoryWinners)

    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    resp.setDateHeader("Expires", 0); // Proxies.

    getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)
  }
}

//TODO make these case classes if the JPSs will work nicely with them
class PlayerWithPoints (val userName : String, val points : Integer, val nextCategoryWinners : String) {
}

class CategoryAndWinner(val categoryName : String, val winner : String) {
}
