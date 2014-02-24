package com.oscarparty.servlets.playerpicks

import scala.collection.mutable
import com.oscarparty.servlets.selection.AllOscarNominees

class PlayerPicks {

  var userName : String = ""
  val allPicks = new mutable.HashMap[String, CategoryPicks]()
  private val allNominees = new AllOscarNominees()

  for ((category) <- allNominees.categories) {
    allPicks(category.name) = new CategoryPicks(category.name)
  }

  def getCategoryPicks(categoryName : String) : CategoryPicks  = {
    allPicks(categoryName)
  }

  def addPick(categoryName : String, pickPriority : String, pick : String) {
    if (!allPicks.contains(categoryName)) {
      throw new IllegalArgumentException("Unsupported pick category: " + categoryName)
    }
    val catPicks = allPicks(categoryName)
    catPicks.setPickAtPriority(pickPriority, pick)
  }

  def arePicksValid : Boolean = {
    for ((catName, catPicks) <- allPicks) {
      val thisOscarCategory = allNominees.findCategory(catName)

      var topPickValid, midPickValid, botPickValid = false

      //if none is ok, leave this uncommented. Comment out when none is not ok
      if (catPicks.topPick == "") topPickValid = true
      if (catPicks.midPick == "") midPickValid = true
      if (catPicks.botPick == "") botPickValid = true
      //if any of the picks are the same in a category, that's not valid. Uncomment this when non is not ok
//      if (catPicks.topPick == catPicks.midPick || catPicks.topPick == catPicks.botPick || catPicks.midPick == catPicks.botPick)
//        return false

      for (eachNominee <- thisOscarCategory.nominees) {
        if (catPicks.topPick == eachNominee) topPickValid = true
        if (catPicks.midPick == eachNominee) midPickValid = true
        if (catPicks.botPick == eachNominee) botPickValid = true
      }

      //if any picks are not a nominee for the category, that's not valid
      if (!topPickValid || !midPickValid || !botPickValid) {
        return false
      }

      if (userName == "") {
        return false
      }

    }
    true
  }
}
