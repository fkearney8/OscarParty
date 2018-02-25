package com.oscarparty.guice

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model.{AttributeDefinition, KeySchemaElement, KeyType, ProvisionedThroughput}
import com.google.inject.AbstractModule
import com.oscarparty.data.dao.{PlayerDataObject, PlayerPicksDataObject}

import collection.JavaConverters._

class DataConfig extends AbstractModule {
  override def configure(): Unit = {
    //TODO move to test and make this the real thing
    bind(classOf[AmazonDynamoDB]).toInstance(createLocalDynamoDb)
  }

  def createLocalDynamoDb: AmazonDynamoDB = {
    System.setProperty("sqlite4java.library.path", "native-libs")
    val localDynamoDb: AmazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB()

    localDynamoDb.createTable(
      List(
        new AttributeDefinition(PlayerDataObject.ID_ATTRIBUTE, "N")
      ).asJava,
      PlayerDataObject.PLAYER_TABLE,
      List(
        new KeySchemaElement().withAttributeName(PlayerDataObject.ID_ATTRIBUTE).withKeyType(KeyType.HASH)
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )

    localDynamoDb.createTable(
      List(
        new AttributeDefinition(PlayerPicksDataObject.PLAYER_ID_ATTR, "N"),
        new AttributeDefinition(PlayerPicksDataObject.CATEGORY_ATTR, "S"),
//        new AttributeDefinition(PlayerPicksDataObject.FIRST_PICK_ATTR, "N"),
//        new AttributeDefinition(PlayerPicksDataObject.SECOND_PICK_ATTR, "N"),
//        new AttributeDefinition(PlayerPicksDataObject.THIRD_PICK_ATTR, "N")
      ).asJava,
      PlayerPicksDataObject.PLAYER_PICKS_TABLE,
      List(
        new KeySchemaElement(PlayerPicksDataObject.PLAYER_ID_ATTR, "N").withKeyType(KeyType.HASH),
        new KeySchemaElement(PlayerPicksDataObject.CATEGORY_ATTR, "S").withKeyType(KeyType.RANGE)
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )
    localDynamoDb
  }
}
