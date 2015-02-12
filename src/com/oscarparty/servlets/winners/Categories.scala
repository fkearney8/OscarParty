package com.oscarparty.servlets.winners

import com.oscarparty.servlets.data.nominees.AllOscarNominees2014
import com.oscarparty.servlets.selection.OscarCategory

object Categories {
  val aon = new AllOscarNominees2014()
  def categoriesWithoutWinners() : Array[String] = {
    //get all the categories
    val catNames = aon.categoryNames

    //get all the winners so far
    val winners = new WinnerDAO().readAllWinners()
    val winnerCategories = for (winner <- winners) yield winner.category

    //what categories don't have winners?
    val filteredNames = catNames.filter((catName) => !winnerCategories.contains(catName))
    filteredNames
  }
}
