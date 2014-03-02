package com.oscarparty.servlets

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.oscarparty.servlets.playerpicks.{CategoryPicks, PlayerPicksDAO}
import com.oscarparty.servlets.winners.WinnerDAO
import com.oscarparty.servlets.selection.AllOscarNominees
import collection.JavaConversions._
import com.oscarparty.servlets.data.NextCategory

class LeaderboardServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val aon = new AllOscarNominees
    //we'll show best picture picks if there's no next category assigned
    var nextCategoryToDisplay: String = NextCategory.nextCategory
    if (nextCategoryToDisplay == null || nextCategoryToDisplay.equals("None") || nextCategoryToDisplay.trim.length == 0) {
      nextCategoryToDisplay = "Best Picture"
    }

    //read up all the picks
    val playerPicks = new PlayerPicksDAO().readAllPlayerPicks()

    var playersAndPoints = new Array[(String, Integer, CategoryPicks)](playerPicks.size())
    //for each player picks, calculate how many points they have
    for (i <- 0 until playerPicks.size()) {
      val eachPickSet = playerPicks.get(i)
      var totalPoints : Integer = 0

      //for each category
      for (eachCategory <- aon.categories) {
        val categoryPicks = eachPickSet.getCategoryPicks(eachCategory.name)
        val categoryWinner = WinnerDAO.findCategoryWinner(eachCategory.name)
        //no points if the category doesn't have a winner yet
        if (categoryWinner != null) {
          val priorities = CategoryPicks.pickPriorities
          val picksInOrder = for (priority <- priorities) yield categoryPicks.getPickAtPriority(priority)
          val picksAndPoints =  picksInOrder zip eachCategory.points

          for (pickAndPointValue <- picksAndPoints) {
            if (pickAndPointValue._1.equals(categoryWinner.winner)) { //if this pick is right
              totalPoints = totalPoints + pickAndPointValue._2 //they get that many points
            }
          }
        }
      }
      playersAndPoints(i) = (eachPickSet.userName, totalPoints, eachPickSet.getCategoryPicks(nextCategoryToDisplay))
    }

    //to make things easier on the leaderboard page, we want the users in order by points
    playersAndPoints = playersAndPoints.sortWith(_._2 > _._2)
    val playerWithPointsArray = for ((player, points, nextCatPicks) <- playersAndPoints) yield {
      new PlayerWithPoints(player, points, {
        if (nextCatPicks != null)
          nextCatPicks.getOrderedPicks.mkString(", ")
        else
          ""
      })
    }
    val javaPlayersWithPointsList : java.util.List[PlayerWithPoints] = asJavaList(playerWithPointsArray)
    req.setAttribute("usersAndPoints", javaPlayersWithPointsList)

    //get the winners list to put on the leaderboard
    //get the categories in order
    val categoryNames = aon.categoryNames
    //add winners where they exist
    val categoryWinners = for (categoryName <- categoryNames)
        yield new CategoryAndWinner(categoryName,
            { val winner = WinnerDAO.findCategoryWinner(categoryName)
              if (winner != null) winner.winner else ""
            })
    val javaCategoryWinners : java.util.List[CategoryAndWinner] = asJavaList(categoryWinners)

    req.setAttribute("nextCategory", nextCategoryToDisplay)
    req.setAttribute("categoryWinners", javaCategoryWinners)

    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    resp.setDateHeader("Expires", 0); // Proxies.

    req.getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)
  }
}


class PlayerWithPoints (val userName : String, val points : Integer, val nextCategoryWinners : String) {
}

class CategoryAndWinner(val categoryName : String, val winner : String) {
}
