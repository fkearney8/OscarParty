package com.oscarparty.servlets.winners

import com.oscarparty.servlets.selection.{OscarCategory, AllOscarNominees}

object Categories {
  val aon = new AllOscarNominees()
  def categoriesWithoutWinners() : Array[String] = {
    //get all the categories
    val catNames = aon.categoryNames

    //get all the winners so far
    val winners = new WinnerDAO().readAllWinners()
    val winnerCategories = for (winner <- winners) yield winner.category

    //what categories don't have winners?
    catNames.filter((catName) => winnerCategories.contains(catName))
    catNames
  }
}
