package com.oscarparty.data.dao

import javax.inject.Inject

import com.oscarparty.data.{Player, PlayerPicks}

class PlayerPicksDaoCaching @Inject() (delegateDao: PlayerPicksDao) extends PlayerPicksDao {
  override def savePlayerPicks(picks: PlayerPicks): Unit = {
    delegateDao.savePlayerPicks(picks)
  }

  override def getPlayerPicks(playerId: String): PlayerPicks = {
    delegateDao.getPlayerPicks(playerId)
  }

  override def allPlayerPicks: Map[Player, PlayerPicks] = {
    delegateDao.allPlayerPicks
  }
}
