package com.oscarparty.servlets

import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.oscarparty.servlets.data.nominees.OscarNomineesDAO
import com.oscarparty.servlets.data.{WinnersDAO, NextCategory, PlayerPicksDAO}
import com.oscarparty.servlets.playerpicks.Calculator

import scala.collection.JavaConverters._


class LeaderboardServlet extends HttpServlet {

  private val pickTruncationSize = 50

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    //we'll show best picture picks if there's no next category assigned
    val nextCategory = OscarNomineesDAO.getCategoryMaybe(NextCategory.nextCategory).getOrElse{
      OscarNomineesDAO.getCategory(1)
    }

    //read up all the picks
    val playerPicks = PlayerPicksDAO.picksPerPlayer

    val playersAndPointsUnsorted = playerPicks.map { case (player, listOfPicks) =>
      val playerName = player.name
      val points = Calculator.calculatePickPoints(listOfPicks)
      val nextCatPicks = PlayerPicksDAO.playerPicksForCategory(player.id, nextCategory.id)
      (playerName, points, nextCatPicks)
    }

    def truncPick(string: String): String = {
      string.take(pickTruncationSize) + "..."
    }

    //to make things easier on the leaderboard page, we want the users in order by points
    val playersAndPoints = playersAndPointsUnsorted.toList.sortWith(_._2 > _._2)
    val playerWithPointsArray = for ((player, points, nextCatPicks) <- playersAndPoints) yield {
      new PlayerWithPoints(player, points,
        {
          nextCatPicks match {
            case None => List.empty[String].asJava
            case Some(nextCatPicksActual) =>
              List(truncPick(nextCatPicksActual.pick1.name),
                truncPick(nextCatPicksActual.pick2.name),
                truncPick(nextCatPicksActual.pick3.name)).asJava
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

    req.setAttribute("nextCategory", nextCategory.name)
    req.setAttribute("categoryWinners", javaCategoryWinners)

    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    resp.setDateHeader("Expires", 0); // Proxies.

    getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)
  }
}

//TODO make these case classes if the JPSs will work nicely with them
case class PlayerWithPoints (userName: String, points: Integer, nextCategoryPicks: java.util.List[String])

case class CategoryAndWinner(categoryName: String, winner: String)
