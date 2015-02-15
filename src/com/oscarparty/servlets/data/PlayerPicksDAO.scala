package com.oscarparty.servlets.data

import com.oscarparty.servlets.data.nominees.{Nominees, Categories}

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag


object PlayerPicksDAO extends SlickDAO {

  case class PlayerPick(player: Int, category: Int, topPick: Int, midPick: Int, botPick: Int)

  class PlayerPicks(tag: Tag) extends Table[PlayerPick] (tag, "playerpicks") {
    def playerId = column[Int]("player")
    def categoryId = column[Int]("category")
    def pick1Id = column[Int]("toppick")
    def pick2Id = column[Int]("midpick")
    def pick3Id = column[Int]("botpick")

    def * = (playerId, categoryId, pick1Id, pick2Id, pick3Id) <> (PlayerPick.tupled, PlayerPick.unapply)

    def player = foreignKey("playerpicks_player_fkey", playerId, TableQuery[Players])(_.id) 
    def category = foreignKey("playerpicks_category_fkey", categoryId, TableQuery[Categories])(_.id)
    def pick1 = foreignKey("playerpicks_toppick_fkey", pick1Id, TableQuery[Nominees])(_.id)
    def pick2 = foreignKey("playerpicks_toppick_fkey", pick2Id, TableQuery[Nominees])(_.id)
    def pick3 = foreignKey("playerpicks_toppick_fkey", pick3Id, TableQuery[Nominees])(_.id)

  }

  case class Player(id: Int, name: String)

  case class PlayerPickForInsertion(category: Int, topPick: Int, midPick: Int, botPick: Int)

  class Players(tag: Tag) extends Table[Player] (tag, "players") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")

    def * = (id, name) <> (Player.tupled, Player.unapply)
  }

  val players = TableQuery[Players]
  val playerPicks = TableQuery[PlayerPicks]

  def addPlayerPicks(playerName: String, newPicks: List[PlayerPickForInsertion]) = DB.withSession { implicit session =>
    players += Player(-1, playerName)
    val newPlayerId = players.filter(_.name === playerName).list.head.id
    val newPlayerPicks = newPicks.map { eachPick =>
      new PlayerPick(newPlayerId, eachPick.category, eachPick.topPick, eachPick.midPick, eachPick.botPick)
    }
    playerPicks ++= newPlayerPicks
  }


}
