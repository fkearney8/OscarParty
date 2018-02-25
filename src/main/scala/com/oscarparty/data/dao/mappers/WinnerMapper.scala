package com.oscarparty.data.dao.mappers

import com.oscarparty.data.Winner
import com.oscarparty.data.nominees.{CategoryName, Nominees2018}

class WinnerMapper {
  def toDataObject(winner: Winner): WinnerDataObject = {
    val winnerDo = new WinnerDataObject()
    winnerDo.setCategory(winner.category.toString)
    winnerDo.setWinningNominee(winner.winningNominee.index)
    winnerDo
  }

  def toDomainObject(winnerDo: WinnerDataObject): Winner = {
    val category = CategoryName.withName(winnerDo.getCategory)
    val winNom = Nominees2018.categoryNominees(category).nominee(winnerDo.getWinningNominee)
    Winner(
      category,
      winNom
    )
  }

}
