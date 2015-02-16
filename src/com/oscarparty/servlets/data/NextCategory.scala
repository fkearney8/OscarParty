package com.oscarparty.servlets.data

import com.oscarparty.servlets.data.nominees.OscarNomineesDAO

object NextCategory {
  private var storedNextCategory : Int = 1

  def nextCategory: Int = storedNextCategory

  def setNextCategory(categoryId: Int) {
    val categoriesWithoutWinners = OscarNomineesDAO.categoriesWithoutWinners
    if (categoriesWithoutWinners.exists(_.id == categoryId)) {
      storedNextCategory = categoryId
    } else {
      storedNextCategory = categoriesWithoutWinners.headOption.fold(-1)(_.id)
    }
  }
}
