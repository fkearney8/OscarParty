package com.oscarparty.data

import com.oscarparty.data.nominees.{CategoryName, Nominee, Nominees2018}

object PlayerPicks {
  def apply(playerId: Int, picks: Seq[CategoryPicks]): PlayerPicks = {
    PlayerPicks(playerId, picks.map(eachPick => eachPick.categoryName -> eachPick).toMap)
  }
}

case class PlayerPicks(playerId: Int, picksByCat: Map[CategoryName.Value, CategoryPicks]) {
  def picksForCategory(category: CategoryName.Value): CategoryPicks = picksByCat(category)
}

object CategoryPicks {
  def apply(categoryName: CategoryName.Value,
            pick1Index: Int,
            pick2Index: Int,
            pick3Index: Int): CategoryPicks = {
    val catNoms = Nominees2018.categoryNominees(categoryName)
    CategoryPicks(categoryName, catNoms.nominee(pick1Index), catNoms.nominee(pick2Index), catNoms.nominee(pick3Index))
  }
}

case class CategoryPicks(categoryName: CategoryName.Value, pick1: Nominee, pick2: Nominee, pick3: Nominee)


