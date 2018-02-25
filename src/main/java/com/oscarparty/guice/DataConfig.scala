package com.oscarparty.guice

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model._
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

    val createPlayerTableReq = new CreateTableRequest(
      List(
        new AttributeDefinition(PlayerDataObject.ID_ATTRIBUTE, "S"),
        new AttributeDefinition(PlayerDataObject.NAME_ATTRIBUTE, "S")
      ).asJava,
      PlayerDataObject.PLAYER_TABLE,
      List(
        new KeySchemaElement()
            .withAttributeName(PlayerDataObject.ID_ATTRIBUTE)
            .withKeyType(KeyType.HASH)
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )

    createPlayerTableReq
        .withGlobalSecondaryIndexes(
          Seq(
            new GlobalSecondaryIndex()
                .withIndexName(PlayerDataObject.NAME_INDEX)
                .withKeySchema(Seq(
                  new KeySchemaElement()
                      .withAttributeName(PlayerDataObject.NAME_ATTRIBUTE)
                      .withKeyType(KeyType.HASH)
                ).asJava)
            .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L))
            .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY))
          ).asJava
        )

    localDynamoDb.createTable(createPlayerTableReq)

    localDynamoDb.createTable(
      List(
        new AttributeDefinition(PlayerPicksDataObject.PLAYER_ID_ATTR, "S"),
        new AttributeDefinition(PlayerPicksDataObject.CATEGORY_ATTR, "S")
      ).asJava,
      PlayerPicksDataObject.PLAYER_PICKS_TABLE,
      List(
        new KeySchemaElement()
            .withAttributeName(PlayerPicksDataObject.PLAYER_ID_ATTR)
            .withKeyType(KeyType.HASH),
        new KeySchemaElement()
            .withAttributeName(PlayerPicksDataObject.CATEGORY_ATTR)
            .withKeyType(KeyType.RANGE)
      ).asJava,
      new ProvisionedThroughput(5L, 5L)
    )
    localDynamoDb
  }
}
