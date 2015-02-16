package com.oscarparty.servlets.data

import com.oscarparty.servlets.data.nominees._

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

object WinnersDAO extends SlickDAO {

  case class WinnerData(categoryId: Int, winnerId: Int)
  case class Winner(category: Category, winningNominee: Nominee)

  class Winners(tag: Tag) extends Table[WinnerData] (tag, "winners") {
    def categoryId = column[Int]("category")
    def winnerId = column[Int]("winner")

    def * = (categoryId, winnerId) <> (WinnerData.tupled, WinnerData.unapply)

    def category = foreignKey("winners_category_fkey", categoryId, TableQuery[Categories])(_.id)
    def winningNominee = foreignKey("winners_winner_fkey", winnerId, TableQuery[Nominees])(_.id)
  }

  val winners = TableQuery[Winners]

  //TODO does this kind of join work so I can save queries? Dont' want to try right now in the middle of a bigger refactor
//  def winnerForCategory(categoryId: Int): Option[Winner] = DB.withSession { implicit session =>
//    val aon = new AllOscarNominees2015()
//    val something = for {
//      winner <- winners if winner.categoryId === categoryId
//      category <- aon.categories.filter(_.id === winner.categoryId).list
//      nominee <- aon.nominees.filter(_.id === winner.winnerId).list
//    } yield {
//      Winner(category, nominee)
//    }
//
//    something.list.headOption
//  }

  def findCategoryWinner(categoryId: Int): Option[Winner] = DB.withSession { implicit session =>

    val winnerDataOption = winners.filter(_.categoryId === categoryId).list.lastOption
    winnerDataOption.map { winnerData =>
      Winner(OscarNomineesDAO.getCategory(winnerData.categoryId), OscarNomineesDAO.getNominee(winnerData.winnerId))
    }
  }

  def saveWinner(winnerData: WinnerData) = DB.withSession { implicit session =>
    winners += winnerData
  }

}
