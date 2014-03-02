package com.oscarparty.servlets.playerpicks

class CategoryPicks (val categoryName : String) {

  var topPick = "none"
  var midPick = "none"
  var botPick = "none"

  def setPickAtPriority(pickPriority : String, pick : String) {
    if (pickPriority == "topPick") topPick = pick
    else if (pickPriority == "midPick") midPick = pick
    else if (pickPriority == "botPick") botPick = pick
    else throw new IllegalArgumentException("Unsupported pick priority " + pickPriority)
  }
  
  def getPickAtPriority(pickPriority : String) : String = {
    if (pickPriority == "topPick") topPick
    else if (pickPriority == "midPick") midPick
    else if (pickPriority == "botPick") botPick
    else throw new IllegalArgumentException("Unsupported pick priority " + pickPriority)
  }
}

object CategoryPicks {
  val pickPriorities = Array("topPick", "midPick", "botPick")
}
