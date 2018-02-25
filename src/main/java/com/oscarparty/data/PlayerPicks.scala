package com.oscarparty.data

import com.oscarparty.data.nominees.{CategoryName, Nominee}

case class PlayerPicks(playerId: Int, picksByCat: Map[CategoryName.Value, CategoryPicks]) {
  def picksForCategory(category: CategoryName.Value): CategoryPicks = picksByCat(category)
}

case class CategoryPicks(categoryName: CategoryName.Value, pick1: Nominee, pick2: Nominee, pick3: Nominee)


