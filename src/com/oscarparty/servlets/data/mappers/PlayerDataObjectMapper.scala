package com.oscarparty.servlets.data.mappers

import com.oscarparty.servlets.data.{Player, PlayerDataObject}

class PlayerDataObjectMapper {

  def convertToDomainObject(playerDo: PlayerDataObject): Player = {
    Player(playerDo.getId, playerDo.getName)
  }

}
