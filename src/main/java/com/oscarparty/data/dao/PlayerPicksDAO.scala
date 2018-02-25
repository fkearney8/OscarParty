package com.oscarparty.data.dao

import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.PlayerPicks
import com.oscarparty.data.dao.mappers.PlayerPicksMapper
import com.oscarparty.data.nominees._

import scala.collection.immutable
import scala.collection.immutable.Iterable



class PlayerPicksDAO @Inject() (dynamoDb: AmazonDynamoDB) {

  private val playerPicksDataMapper = new PlayerPicksMapper()
  private val playerPicksDynamoMapper = new DynamoDBMapper(dynamoDb)

  def savePlayerPicks(picks: PlayerPicks): Unit = {

    val pickDos: Iterable[PlayerPicksDataObject] = picks.picksByCat.map { case (_, categoryPicks) =>
        playerPicksDataMapper.toDataObject(picks.playerId, categoryPicks)
    }

    pickDos.foreach(pickDo => playerPicksDynamoMapper.save(pickDo))
  }

  def getPlayerPicks(playerId: String): PlayerPicks = {
    //for each category load their picks
    val playerPicksDoList = CategoryName.values.flatMap { eachCategory =>
      val picksForCat = playerPicksDynamoMapper.load(classOf[PlayerPicksDataObject], playerId, eachCategory.toString)
      Option(picksForCat)
    }
    //convert to domain object from the list of data objects
    playerPicksDataMapper.toDomainObject(playerId, playerPicksDoList.toSeq)
  }


//  def allPlayerPicks: List[PlayerPick] = DB.withSession { implicit session =>
//    playerPicks.list
//  }
//
//  def allPlayers: List[Player] = DB.withSession { implicit session =>
//    players.list
//  }
//
//  def picksByPlayer(playerId: Int): List[PlayerPick] = DB.withSession { implicit session =>
//    playerPicks.filter(_.playerId === playerId).list
//  }
//
//  def picksPerPlayer: Map[Player, List[PlayerPick]] = DB.withSession { implicit session =>
//    allPlayers.map { player =>
//      player -> picksByPlayer(player.id)
//    }.toMap
//  }
//
//  case class PlayerPickWithReferences(player: Player, category: Category, pick1: Nominee, pick2: Nominee, pick3: Nominee)
//
//  def playerPicksForCategory(playerId: Int, categoryId: Int): Option[PlayerPickWithReferences] = DB.withSession { implicit session =>
//    playerPicks.filter(_.playerId === playerId).filter(_.categoryId === categoryId).list.headOption.map {
//      playerPick =>
//        val player = getPlayer(playerPick.player)
//        val category = OscarNomineesDAO.getCategory(categoryId)
//        val pick1 = OscarNomineesDAO.getNominee(playerPick.topPick)
//        val pick2 = OscarNomineesDAO.getNominee(playerPick.midPick)
//        val pick3 = OscarNomineesDAO.getNominee(playerPick.botPick)
//        PlayerPickWithReferences(player, category, pick1, pick2, pick3)
//    }
//  }
}
