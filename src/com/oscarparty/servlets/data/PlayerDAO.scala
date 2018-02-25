package com.oscarparty.servlets.data

import java.util
import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.{DynamoDBMapper, DynamoDBQueryExpression}
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.oscarparty.servlets.data.mappers.PlayerDataObjectMapper
import collection.JavaConverters._

class PlayerDAO @Inject() (dynamoDb: AmazonDynamoDB) {

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
}
