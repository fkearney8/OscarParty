package com.oscarparty.data.dao.mappers

import com.oscarparty.data.{CategoryPicks, PlayerPicks}
import PlayerPicksDataObject
import com.oscarparty.data.nominees.CategoryName

class PlayerPicksMapper {

  def toDomainObject(playerId: String, playerPicksDoList: Seq[PlayerPicksDataObject]): PlayerPicks = {
    val catPicks = playerPicksDoList.map { eachPickDo =>
      CategoryPicks(CategoryName.withName(eachPickDo.getCategory),
        eachPickDo.getFirstPick,
        eachPickDo.getSecondPick,
        eachPickDo.getThirdPick)
    }

    PlayerPicks(playerId, catPicks)
  }

  def toDataObject(playerId: String, catPicks: CategoryPicks): PlayerPicksDataObject = {
    val ppdo = new PlayerPicksDataObject()
    ppdo.setPlayerId(playerId)
    ppdo.setCategory(catPicks.categoryName.toString)
    ppdo.setFirstPick(catPicks.pick1.index)
    ppdo.setSecondPick(catPicks.pick2.index)
    ppdo.setThirdPick(catPicks.pick3.index)


    ppdo
  }
}
