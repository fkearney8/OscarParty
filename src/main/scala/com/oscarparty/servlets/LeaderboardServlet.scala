package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import java.util.{List => JList}

import com.oscarparty.data.{NextCategory, Player}
import com.oscarparty.data.dao.{PlayerDAO, PlayerPicksDAO, WinnersDAO}
import com.oscarparty.data.nominees.{CategoryName, Nominees2018}
import com.oscarparty.servlets.playerpicks.Calculator
import LeaderboardServlet._

import scala.collection.JavaConverters._

object LeaderboardServlet {
  private val pickTruncationSize = 50
}

@Singleton
class LeaderboardServlet @Inject() (playerPicksDao: PlayerPicksDAO,
                                    playerDao: PlayerDAO,
                                    winnersDao: WinnersDAO,
                                    calculator: Calculator) extends HttpServlet {

  /** For display, truncate the pick text. */
  private def truncPick(string: String): String = {
    string.take(pickTruncationSize) + {if (string.length > pickTruncationSize) "..." else ""}
  }

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    //we'll show best picture picks if there's no next category assigned
    val nextCategory = NextCategory.nextCategory

    //read up all the picks
    val playerPicks = playerPicksDao.allPlayerPicks

    val playersAndPointsUnsorted = playerPicks.map { case (player, listOfPicks) =>
      val playerName = player.name
      val points = calculator.calculatePickPoints(listOfPicks)
      val nextCatPicks = playerPicks(player).picksForCategory(nextCategory)
      (player, points, nextCatPicks)
    }

    //to make things easier on the leaderboard page, we want the users in order by points
    val playersAndPoints = playersAndPointsUnsorted.toList.sortWith(_._2 > _._2)

    val playerWithPointsArray = for ((player, points, nextCatPicks) <- playersAndPoints) yield {
      PlayerWithPoints(player, points,
        {
          nextCatPicks match {
            case None => List.empty[String].asJava
            case Some(nextCatPicksActual) =>
              List(
                truncPick(nextCatPicksActual.pick1.name),
                truncPick(nextCatPicksActual.pick2.name),
                truncPick(nextCatPicksActual.pick3.name)).asJava
          }
        })
    }
    val javaPlayersWithPointsList : java.util.List[PlayerWithPoints] = playerWithPointsArray.asJava
    req.setAttribute("usersAndPoints", javaPlayersWithPointsList)

    //get the winners list to put on the leaderboard
    //get the categories in order
    val categories = CategoryName.values
    //add winners where they exist
    val categoryWinners = categories.map { category =>
      CategoryAndWinner(category.displayName, winnersDao.winnerForCategory(category).fold("")(_.winningNominee.name))
    }
    val javaCategoryWinners : JList[CategoryAndWinner] = categoryWinners.toSeq.asJava

    req.setAttribute("nextCategory", nextCategory.displayName)
    req.setAttribute("categoryWinners", javaCategoryWinners)

    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    resp.setDateHeader("Expires", 0); // Proxies.

    getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)
  }
}

case class PlayerWithPoints(player: Player, points: Integer, nextCategoryPicks: JList[String])

case class CategoryAndWinner(categoryName: String, winner: String)
