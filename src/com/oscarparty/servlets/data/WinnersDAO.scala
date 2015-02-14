package com.oscarparty.servlets.data

import com.oscarparty.servlets.data.nominees.{Nominees, Categories}

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

object WinnersDAO extends SlickDAO {

  case class Winner(category: Int, winner: Int)

  class Winners(tag: Tag) extends Table[Winner] (tag, "winners") {
    def categoryId = column[Int]("category")
    def winnerId = column[Int]("winner")

    def * = (categoryId, winnerId) <> (Winner.tupled, Winner.unapply)

    def category = foreignKey("winners_category_fkey", categoryId, TableQuery[Categories])(_.id)
    def winningNominee = foreignKey("winners_winner_fkey", winnerId, TableQuery[Nominees])(_.id)
  }

}
