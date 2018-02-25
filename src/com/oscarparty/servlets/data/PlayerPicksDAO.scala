package com.oscarparty.servlets.data

import java.util
import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.{DynamoDBMapper, DynamoDBQueryExpression, DynamoDBScanExpression}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.oscarparty.servlets.data.mappers.PlayerDataObjectMapper
import com.oscarparty.servlets.data.nominees._

import collection.JavaConverters._

case class PlayerPickData(playerId: Int, picksByCat: Map[CategoryName.Value, CategoryPicks]) {
  def picksForCategory(category: CategoryName.Value): CategoryPicks = picksByCat(category)
}

case class CategoryPicks(categoryName: CategoryName.Value, pick1: Nominee, pick2: Nominee, pick3: Nominee)

case class Player(id: Int, name: String)

class PlayerPicksDAO @Inject() (dynamoDb: AmazonDynamoDB) {

  val playerDynamoMapper = new DynamoDBMapper(dynamoDb)
  val playerDataMapper = new PlayerDataObjectMapper

  def getPlayer(playerName: String): Option[Player] = {

    val eav: util.Map[String, AttributeValue] = collection.Map(":name" -> new AttributeValue().withS(playerName)).asJava
    val queryExpr: DynamoDBQueryExpression[PlayerDataObject] = new DynamoDBQueryExpression()
        .withKeyConditionExpression(PlayerDataObject.NAME_ATTRIBUTE + " = :name")
        .withExpressionAttributeValues(eav)

    val queryResult = playerDynamoMapper.query(classOf[PlayerDataObject], queryExpr).asScala
    val maybePlayerDo = queryResult.find(_.getName == playerName)
    maybePlayerDo.map(playerDataMapper.convertToDomainObject)
  }

  def getPlayer(playerId: Int): Player = {
    val playerDo = playerDynamoMapper.load(classOf[PlayerDataObject], new java.lang.Integer(playerId))
    playerDataMapper.convertToDomainObject(playerDo)
  }

//  def addPlayerPicks(playerName: String, newPicks: List[PlayerPickForInsertion]) = DB.withSession { implicit session =>
//    players += Player(-1, playerName)
//    val newPlayerId = players.filter(_.name === playerName).list.head.id
//    val newPlayerPicks = newPicks.map { eachPick =>
//      new PlayerPick(newPlayerId, eachPick.category, eachPick.pick1, eachPick.pick2, eachPick.pick3)
//    }
//    playerPicks ++= newPlayerPicks
//  }
//
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
