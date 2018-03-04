package com.oscarparty.data.dao

import javax.inject.Inject

import com.oscarparty.data.Winner
import com.oscarparty.data.nominees.CategoryName

class WinnersDaoCaching @Inject() (delegatedDao: WinnersDao) extends WinnersDao {
  override def winnerForCategory(category: CategoryName.Value): Option[Winner] = {
    delegatedDao.winnerForCategory(category)
  }

  override def saveWinner(winner: Winner): Unit = {
    delegatedDao.saveWinner(winner)
  }
}
