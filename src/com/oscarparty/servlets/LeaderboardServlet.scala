package com.oscarparty.servlets

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.servlets.data.nominees.OscarNomineesDAO
import com.oscarparty.servlets.data.{WinnersDAO, NextCategory, PlayerPicksDAO}
import com.oscarparty.servlets.playerpicks.Calculator

import scala.collection.JavaConverters._


class LeaderboardServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    //we'll show best picture picks if there's no next category assigned
    var nextCategoryToDisplay = NextCategory.nextCategory
    if (nextCategoryToDisplay == 0) {
      nextCategoryToDisplay = 1
    }

    //read up all the picks
    val playerPicks = PlayerPicksDAO.picksPerPlayer

    val playersAndPointsUnsorted = playerPicks.map { case (player, listOfPicks) =>
      val playerName = player.name
      val points = Calculator.calculatePickPoints(listOfPicks)
      val nextCatPicks = PlayerPicksDAO.playerPicksForCategory(player.id, OscarNomineesDAO.getCategory(nextCategoryToDisplay).id)
      (playerName, points, nextCatPicks)
    }

    //to make things easier on the leaderboard page, we want the users in order by points
    val playersAndPoints = playersAndPointsUnsorted.toList.sortWith(_._2 > _._2)
    val playerWithPointsArray = for ((player, points, nextCatPicks) <- playersAndPoints) yield {
      new PlayerWithPoints(player, points,
        {
          nextCatPicks match {
            case None => ""
            case Some(nextCatPicksActual) =>
              List(nextCatPicksActual.pick1.name, nextCatPicksActual.pick2.name, nextCatPicksActual.pick3.name).mkString(", ")
          }
        })
    }
    val javaPlayersWithPointsList : java.util.List[PlayerWithPoints] = playerWithPointsArray.asJava
    req.setAttribute("usersAndPoints", javaPlayersWithPointsList)

    //get the winners list to put on the leaderboard
    //get the categories in order
    val categories = OscarNomineesDAO.getCategories
    //add winners where they exist
    val categoryWinners = categories.map { category =>
      new CategoryAndWinner(category.name, WinnersDAO.findCategoryWinner(category.id).fold("")(_.winningNominee.name))
    }
    val javaCategoryWinners : java.util.List[CategoryAndWinner] = categoryWinners.asJava

    req.setAttribute("nextCategory", nextCategoryToDisplay)
    req.setAttribute("categoryWinners", javaCategoryWinners)

    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    resp.setDateHeader("Expires", 0); // Proxies.

    getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)
  }
}

//TODO make these case classes if the JPSs will work nicely with them
class PlayerWithPoints (val userName : String, val points : Integer, val nextCategoryPicks : String) {
}

class CategoryAndWinner(val categoryName : String, val winner : String) {
}
