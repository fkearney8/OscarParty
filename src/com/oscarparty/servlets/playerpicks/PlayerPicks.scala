package com.oscarparty.servlets.playerpicks

import scala.collection.mutable
import com.oscarparty.servlets.selection.AllOscarNominees

class PlayerPicks {

  val allPicks = new mutable.HashMap[String, CategoryPicks]()
  private val allNominees = new AllOscarNominees()

  for ((category) <- allNominees.categories) {
    allPicks(category.name) = new CategoryPicks()
  }

  def getCategoryPicks(categoryName : String) : CategoryPicks  = {
    allPicks(categoryName)
  }

  def arePicksValid : Boolean = {
    for ((catName, catPicks) <- allPicks) {
      val thisOscarCategory = allNominees.findCategory(catName)

      var topPickValid, midPickValid, botPickValid = false

      for (eachNominee <- thisOscarCategory.nominees) {
        if (catPicks.topPick == eachNominee) topPickValid = true
        if (catPicks.midPick == eachNominee) midPickValid = true
        if (catPicks.botPick == eachNominee) botPickValid = true
      }

      if (!topPickValid || !midPickValid || !botPickValid) {
        return false
      }
    }
  }
}
