package com.oscarparty.guice

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.{AttributeDefinition, KeySchemaElement, ProvisionedThroughput}
import com.google.inject.AbstractModule
import com.oscarparty.servlets.data.{PlayerDataObject, PlayerPicksDataObject}

import collection.JavaConverters._

class DataConfig extends AbstractModule {
  override def configure(): Unit = {
    //TODO move to test and make this the real thing
    bind(classOf[AmazonDynamoDB]).toInstance(createLocalDynamoDb)
  }

  def createLocalDynamoDb: AmazonDynamoDB = {
    val localDynamoDb: AmazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB()

    localDynamoDb.createTable(
      List(
        //        new AttributeDefinition(PlayerDataObject.ID_ATTRIBUTE, "N"),
        new AttributeDefinition(PlayerDataObject.NAME_ATTRIBUTE, "S")
      ).asJava,
      PlayerDataObject.PLAYER_TABLE,
      List(
        new KeySchemaElement(PlayerDataObject.ID_ATTRIBUTE, "N")
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )

    localDynamoDb.createTable(
      List(
        //        new AttributeDefinition(PlayerPicksDataObject.PLAYER_ID_ATTR, "N"),
        //        new AttributeDefinition(PlayerPicksDataObject.CATEGORY_ATTR, "S"),
        new AttributeDefinition(PlayerPicksDataObject.FIRST_PICK_ATTR, "N"),
        new AttributeDefinition(PlayerPicksDataObject.SECOND_PICK_ATTR, "N"),
        new AttributeDefinition(PlayerPicksDataObject.THIRD_PICK_ATTR, "N")
      ).asJava,
      PlayerPicksDataObject.PLAYER_PICKS_TABLE,
      List(
        new KeySchemaElement(PlayerPicksDataObject.PLAYER_ID_ATTR, "N"),
        new KeySchemaElement(PlayerPicksDataObject.CATEGORY_ATTR, "S")
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )
    localDynamoDb
  }
}
