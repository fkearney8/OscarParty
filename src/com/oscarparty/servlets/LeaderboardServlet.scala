package com.oscarparty.servlets

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.oscarparty.servlets.playerpicks.{CategoryPicks, PlayerPicksDAO}
import com.oscarparty.servlets.winners.WinnerDAO
import com.oscarparty.servlets.selection.AllOscarNominees
import collection.JavaConverters._
import collection.JavaConversions._
import com.oscarparty.servlets.leaderboard.PlayerWithPoints

class LeaderboardServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    //read up all the picks
    val playerPicks = new PlayerPicksDAO().readAllPlayerPicks()

    var playersAndPoints = new Array[(String, Integer)](playerPicks.size())
    //for each player picks, calculate how many points they have
    for (i <- 0 until playerPicks.size()) {
      val eachPickSet = playerPicks.get(i)
      var totalPoints : Integer = 0

      //for each category
      val aon = new AllOscarNominees
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
      playersAndPoints(i) = (eachPickSet.userName, totalPoints)
    }

    //to make things easier on the leaderboard page, we want the users in order by points
    playersAndPoints = playersAndPoints.sortWith(_._2 > _._2)

    val playerWithPointsArray = for ((player, points) <- playersAndPoints) yield new PlayerWithPoints(player, points)

    val javaList : java.util.List[PlayerWithPoints] = asJavaList(playerWithPointsArray)

    req.setAttribute("usersAndPoints", javaList)
    req.getServletContext.getRequestDispatcher("/leaderboard.jsp").forward(req, resp)

  }
}
