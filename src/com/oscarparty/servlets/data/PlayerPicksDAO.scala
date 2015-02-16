package com.oscarparty.servlets.data

import com.oscarparty.servlets.data.nominees._

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag


object PlayerPicksDAO extends SlickDAO {

//  TODO rename to PlayerPickData, make an external facing one tha thas the linkages filled in so I don't have to dereference IDs everywhere in the code
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

  case class PlayerPickForInsertion(category: Int, pick1: Int, pick2: Int, pick3: Int)

  class Players(tag: Tag) extends Table[Player] (tag, "players") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")

    def * = (id, name) <> (Player.tupled, Player.unapply)
  }

  val players = TableQuery[Players]
  val playerPicks = TableQuery[PlayerPicks]

  def getPlayer(playerName: String): Option[Player] = DB.withSession { implicit session =>
    players.filter(_.name === playerName).list.lastOption
  }

  def getPlayer(playerId: Int): Player = DB.withSession { implicit session =>
    players.filter(_.id === playerId).list.last
  }

  def addPlayerPicks(playerName: String, newPicks: List[PlayerPickForInsertion]) = DB.withSession { implicit session =>
    players += Player(-1, playerName)
    val newPlayerId = players.filter(_.name === playerName).list.head.id
    val newPlayerPicks = newPicks.map { eachPick =>
      new PlayerPick(newPlayerId, eachPick.category, eachPick.pick1, eachPick.pick2, eachPick.pick3)
    }
    playerPicks ++= newPlayerPicks
  }

  def allPlayerPicks: List[PlayerPick] = DB.withSession { implicit session =>
    playerPicks.list
  }

  def allPlayers: List[Player] = DB.withSession { implicit session =>
    players.list
  }

  def picksByPlayer(playerId: Int): List[PlayerPick] = DB.withSession { implicit session =>
    playerPicks.filter(_.playerId === playerId).list
  }

  def picksPerPlayer: Map[Player, List[PlayerPick]] = DB.withSession { implicit session =>
    allPlayers.map { player =>
      player -> picksByPlayer(player.id)
    }.toMap
  }

  case class PlayerPickWithReferences(player: Player, category: Category, pick1: Nominee, pick2: Nominee, pick3: Nominee)

  def playerPicksForCategory(playerId: Int, categoryId: Int): Option[PlayerPickWithReferences] = DB.withSession { implicit session =>
    playerPicks.filter(_.playerId === playerId).filter(_.categoryId === categoryId).list.headOption.map {
      playerPick =>
        val player = getPlayer(playerPick.player)
        val category = OscarNomineesDAO.getCategory(categoryId)
        val pick1 = OscarNomineesDAO.getNominee(playerPick.topPick)
        val pick2 = OscarNomineesDAO.getNominee(playerPick.midPick)
        val pick3 = OscarNomineesDAO.getNominee(playerPick.botPick)
        PlayerPickWithReferences(player, category, pick1, pick2, pick3)
    }
  }
}
