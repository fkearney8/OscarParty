package com.oscarparty.data.dao.mappers

import com.oscarparty.data.Player
import com.oscarparty.data.dao.PlayerDataObject

class PlayerMapper {

  def toDomainObject(playerDo: PlayerDataObject): Player = {
    Player(playerDo.getId, playerDo.getPlayerName)
  }

  def toDataObject(player: Player): PlayerDataObject = {
    val pdo = new PlayerDataObject()
    pdo.setId(player.id)
    pdo.setPlayerName(player.name)
    pdo
  }

  def toDataObject(playerName: String): PlayerDataObject = {
    val pdo = new PlayerDataObject()
    pdo.setPlayerName(playerName)
    pdo
  }

}
