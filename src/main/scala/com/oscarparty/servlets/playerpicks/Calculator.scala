package com.oscarparty.servlets.playerpicks

import javax.inject.Inject

import com.oscarparty.data.dao.WinnersDao
import com.oscarparty.data.nominees.Nominee
import com.oscarparty.data.{CategoryPicks, PlayerPicks, Winner}

class Calculator @Inject() (winnersDao: WinnersDao) {

  def calculatePickPoints(playerPicks: PlayerPicks): Int = {

    //for each category, get the player picks
    val categoryPicks = playerPicks.picksByCat.values

    //map this into points won
    val pointsWonPerCat: Iterable[Int] = categoryPicks.flatMap { picksInCat: CategoryPicks =>
      val category = picksInCat.categoryName
      val maybeWinner: Option[Winner] = winnersDao.winnerForCategory(category)
      //if no winner yet, map to none
      val maybeWinnerPicked: Option[(Nominee, Int)] = maybeWinner.flatMap { winner =>
        val picksAndPoints = picksInCat.picksInOrder.zip(category.points.pointsInOrder)
        picksAndPoints.find {
          case (nomPicked, pointsIfRight) =>
            winner.winningNominee.index == nomPicked.index
        }
      }
      maybeWinnerPicked.map(_._2)
    }

    pointsWonPerCat.sum
  }

  def calculatePickPointJava(playerPicks: PlayerPicks): Integer = {
    calculatePickPoints(playerPicks)
  }
}
