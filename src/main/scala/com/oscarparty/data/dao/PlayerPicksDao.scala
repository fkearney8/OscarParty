package com.oscarparty.data.dao

import com.oscarparty.data.{Player, PlayerPicks}

trait PlayerPicksDao {

  def savePlayerPicks(picks: PlayerPicks): Unit

  def getPlayerPicks(playerId: String): PlayerPicks

  def allPlayerPicks: Map[Player, PlayerPicks]

}
