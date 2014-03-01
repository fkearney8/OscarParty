package com.oscarparty.servlets.winners

import com.oscarparty.servlets.selection.AllOscarNominees

object Categories {

  def categoriesWithoutWinners() : Array[String] = {
    //get all the categories
    val aon = new AllOscarNominees()
    val catNames = aon.categoryNames

    //get all the winners so far
    val winners = new WinnerDAO().readAllWinners()
    val winnerCategories = for (winner <- winners) yield winner.category

    //what categories don't have winners?
    catNames.filter((catName) => winnerCategories.contains(catName))
    catNames
  }
}
