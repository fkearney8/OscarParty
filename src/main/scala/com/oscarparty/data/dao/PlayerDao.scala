package com.oscarparty.data.dao

import com.oscarparty.data.Player

trait PlayerDao {

  def getPlayerByName(playerName: String): Option[Player]

  def getPlayerById(playerId: String): Player

  def savePlayer(playerName: String): Player

  def allPlayers: Seq[Player]

}
