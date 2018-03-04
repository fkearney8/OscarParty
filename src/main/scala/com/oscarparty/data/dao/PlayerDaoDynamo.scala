package com.oscarparty.data.dao

import java.util
import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.{DynamoDBMapper, DynamoDBQueryExpression, DynamoDBScanExpression}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.oscarparty.OscarException
import com.oscarparty.data.Player
import com.oscarparty.data.dao.mappers.{PlayerDataObject, PlayerMapper}

import collection.JavaConverters._

class PlayerDaoDynamo @Inject()(dynamoMapper: DynamoDBMapper) extends PlayerDao {

  val playerDataMapper = new PlayerMapper

  def getPlayerByName(playerName: String): Option[Player] = {
    println(s"Database Read: Looking for player named $playerName from database.")
    val eav: util.Map[String, AttributeValue] = collection.Map(":playerName" -> new AttributeValue().withS(playerName)).asJava
    val queryExpr: DynamoDBQueryExpression[PlayerDataObject] = new DynamoDBQueryExpression()
        .withIndexName(PlayerDataObject.NAME_INDEX)
        .withConsistentRead(false)
        .withKeyConditionExpression(PlayerDataObject.NAME_ATTRIBUTE + " = :playerName")
        .withExpressionAttributeValues(eav)

    val queryResult = dynamoMapper.query(classOf[PlayerDataObject], queryExpr).asScala
    val maybePlayerDo = queryResult.find(_.getPlayerName == playerName)
    maybePlayerDo.map(playerDataMapper.toDomainObject)
  }

  def getPlayerById(playerId: String): Player = {
    println(s"Database read: Loading player ID $playerId from database")
    val playerDo = dynamoMapper.load(classOf[PlayerDataObject], playerId)
    playerDataMapper.toDomainObject(playerDo)
  }

  def savePlayer(playerName: String): Player = {
    println(s"Database write: saving player $playerName to database.")
    //check we don't have a player by that name
    if (getPlayerByName(playerName).isDefined) {
      throw new OscarException(s"Already have someone by the name $playerName in the game. Please choose another name.")
    }
    val playerDo = playerDataMapper.toDataObject(playerName)
    dynamoMapper.save(playerDo)
    playerDataMapper.toDomainObject(playerDo)
  }

  def allPlayers: Seq[Player] = {
    println("Database read: Loading all players from database.")
    dynamoMapper.scan(classOf[PlayerDataObject],
      new DynamoDBScanExpression())
        .asScala
        .map(playerDataMapper.toDomainObject)
  }
}
