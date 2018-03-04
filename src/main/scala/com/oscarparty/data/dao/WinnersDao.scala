package com.oscarparty.data.dao

import com.oscarparty.data.Winner
import com.oscarparty.data.nominees.CategoryName

trait WinnersDao {

  def winnerForCategory(category: CategoryName.Value): Option[Winner]

  def saveWinner(winner: Winner): Unit

}
