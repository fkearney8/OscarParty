package com.oscarparty.servlets.playerpicks

import com.oscarparty.servlets.data.PlayerPicksDAO.PlayerPick
import com.oscarparty.servlets.data.WinnersDAO
import com.oscarparty.servlets.data.nominees.AllOscarNominees2015

object Calculator {
  def calculatePickPoints(playerPicks: List[PlayerPick]): Integer = {

    var totalPoints: Integer = 0
    val aon = new AllOscarNominees2015
    //for each category
    for (eachCategory <- aon.getCategories) {
      val playerCategoryPicksOption = playerPicks.filter(_.category == eachCategory.id).lastOption
      //no points if the player doesn't have picks for this category somehow
      playerCategoryPicksOption.map { playerCategoryPicks =>
        //no points if the category doesn't have a winner yet
        WinnersDAO.findCategoryWinner(eachCategory.id).map { categoryWinner =>

          val picksInOrder = Array(playerCategoryPicks.topPick, playerCategoryPicks.midPick, playerCategoryPicks.botPick)
          val picksAndPoints = picksInOrder zip Array(eachCategory.points1, eachCategory.points2, eachCategory.points3)

          for (pickAndPointValue <- picksAndPoints) {
            if (pickAndPointValue._1.equals(categoryWinner.winningNominee.id)) {
              //if this pick is right
              totalPoints = totalPoints + pickAndPointValue._2 //they get that many points
            }
          }
        }
      }
    }
    totalPoints
  }
}
