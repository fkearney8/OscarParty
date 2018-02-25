package com.oscarparty.data.dao

import java.util
import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.{DynamoDBMapper, DynamoDBQueryExpression, DynamoDBScanExpression}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.oscarparty.OscarException
import com.oscarparty.data.Player
import com.oscarparty.data.dao.mappers.PlayerMapper

import collection.JavaConverters._

class PlayerDAO @Inject() (dynamoDb: AmazonDynamoDB) {

  val playerDynamoMapper = new DynamoDBMapper(dynamoDb)
  val playerDataMapper = new PlayerMapper

  def getPlayerByName(playerName: String): Option[Player] = {

    val eav: util.Map[String, AttributeValue] = collection.Map(":playerName" -> new AttributeValue().withS(playerName)).asJava
    val queryExpr: DynamoDBQueryExpression[PlayerDataObject] = new DynamoDBQueryExpression()
        .withIndexName(PlayerDataObject.NAME_INDEX)
        .withConsistentRead(false)
        .withKeyConditionExpression(PlayerDataObject.NAME_ATTRIBUTE + " = :playerName")
        .withExpressionAttributeValues(eav)

    val queryResult = playerDynamoMapper.query(classOf[PlayerDataObject], queryExpr).asScala
    val maybePlayerDo = queryResult.find(_.getPlayerName == playerName)
    maybePlayerDo.map(playerDataMapper.toDomainObject)
  }

  def getPlayerById(playerId: String): Player = {
    val playerDo = playerDynamoMapper.load(classOf[PlayerDataObject], playerId)
    playerDataMapper.toDomainObject(playerDo)
  }

  def savePlayer(playerName: String): Player = {
    //check we don't have a player by that name
    if (getPlayerByName(playerName).isDefined) {
      throw new OscarException(s"Already have someone by the name $playerName in the game. Please choose another name.")
    }
    val playerDo = playerDataMapper.toDataObject(playerName)
    playerDynamoMapper.save(playerDo)
    playerDataMapper.toDomainObject(playerDo)
  }

  def allPlayers: Seq[Player] = {
    playerDynamoMapper.scan(classOf[PlayerDataObject],
      new DynamoDBScanExpression())
        .asScala
        .map(playerDataMapper.toDomainObject)
  }
}
