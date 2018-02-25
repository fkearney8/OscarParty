package com.oscarparty.data.dao

import java.util
import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.{DynamoDBMapper, DynamoDBQueryExpression}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.oscarparty.data.Player
import com.oscarparty.data.dao.mappers.PlayerMapper

import collection.JavaConverters._

class PlayerDAO @Inject() (dynamoDb: AmazonDynamoDB) {

  val playerDynamoMapper = new DynamoDBMapper(dynamoDb)
  val playerDataMapper = new PlayerMapper

  def getPlayer(playerName: String): Option[Player] = {

    val eav: util.Map[String, AttributeValue] = collection.Map(":name" -> new AttributeValue().withS(playerName)).asJava
    val queryExpr: DynamoDBQueryExpression[PlayerDataObject] = new DynamoDBQueryExpression()
        .withKeyConditionExpression(PlayerDataObject.NAME_ATTRIBUTE + " = :name")
        .withExpressionAttributeValues(eav)

    val queryResult = playerDynamoMapper.query(classOf[PlayerDataObject], queryExpr).asScala
    val maybePlayerDo = queryResult.find(_.getName == playerName)
    maybePlayerDo.map(playerDataMapper.toDomainObject)
  }

  def getPlayer(playerId: Int): Player = {
    val playerDo = playerDynamoMapper.load(classOf[PlayerDataObject], new java.lang.Integer(playerId))
    playerDataMapper.toDomainObject(playerDo)
  }

  def savePlayer(player: Player): Unit = {
    val playerDo = playerDataMapper.toDataObject(player)
    playerDynamoMapper.save(playerDo)
  }
}
