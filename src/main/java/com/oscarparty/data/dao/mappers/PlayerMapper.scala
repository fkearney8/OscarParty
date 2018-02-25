package com.oscarparty.data.dao.mappers

import com.oscarparty.data.Player
import com.oscarparty.data.dao.PlayerDataObject

class PlayerMapper {

  def toDomainObject(playerDo: PlayerDataObject): Player = {
    Player(playerDo.getId, playerDo.getName)
  }

  def toDataObject(player: Player): PlayerDataObject = {
    val pdo = new PlayerDataObject()
    pdo.setId(player.id)
    pdo.setName(player.name)
    pdo
  }

}
