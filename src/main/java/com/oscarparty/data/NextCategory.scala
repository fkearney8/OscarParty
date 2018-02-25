package com.oscarparty.data

import com.oscarparty.data.nominees.CategoryName

/**
  * In memory cache of the next category coming up.
  */
object NextCategory {
  var storedNextCategory : CategoryName.Value = CategoryName.BestPicture

  def nextCategory: CategoryName.Value = storedNextCategory

//  def setNextCategory(nextCatName: CategoryName.Value) {
//    val categoriesWithoutWinners = OscarNomineesDAO.categoriesWithoutWinners
//    if (categoriesWithoutWinners.exists(_.id == nextCatName)) {
//      storedNextCategory = nextCatName
//    } else {
//      storedNextCategory = categoriesWithoutWinners.headOption.fold(-1)(_.id)
//    }
//  }
}
