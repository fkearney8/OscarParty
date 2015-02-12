package com.oscarparty.servlets.playerpicks

import com.oscarparty.servlets.data.nominees.AllOscarNominees2014
import com.oscarparty.servlets.winners.WinnerDAO

object Calculator {
  def calculatePickPoints(playerPicks: PlayerPicks): Integer = {
    var totalPoints: Integer = 0
    val aon = new AllOscarNominees2014
    //for each category
    for (eachCategory <- aon.getCategories) {
      val categoryPicks = playerPicks.getCategoryPicks(eachCategory.name)
      val categoryWinner = WinnerDAO.findCategoryWinner(eachCategory.name)
      //no points if the category doesn't have a winner yet
      if (categoryWinner != null) {
        val priorities = CategoryPicks.pickPriorities
        val picksInOrder = for (priority <- priorities) yield categoryPicks.getPickAtPriority(priority)
        val picksAndPoints = picksInOrder zip eachCategory.points

        for (pickAndPointValue <- picksAndPoints) {
          if (pickAndPointValue._1.equals(categoryWinner.winner)) {
            //if this pick is right
            totalPoints = totalPoints + pickAndPointValue._2 //they get that many points
          }
        }
      }
    }
    totalPoints
  }
}
