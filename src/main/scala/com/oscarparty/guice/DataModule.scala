package com.oscarparty.guice

import java.util.concurrent.ScheduledExecutorService
import javax.inject.Singleton

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.model._
import com.google.inject.{AbstractModule, Provides, Stage}
import com.oscarparty.data.dao._
import com.oscarparty.data.dao.mappers.{PlayerDataObject, PlayerPicksDataObject, WinnerDataObject}

import collection.JavaConverters._

class DataModule extends AbstractModule {
  override def configure(): Unit = {

    bind(classOf[AmazonDynamoDB]).toInstance(
      if (currentStage() == Stage.PRODUCTION) productionDynamoDb else localhostDynamoDb)
  }

  @Singleton
  @Provides
  def playerDao(scheduler: ScheduledExecutorService, dynamoMapper: DynamoDBMapper): PlayerDao = {
    new PlayerDaoCaching(scheduler, new PlayerDaoDynamo(dynamoMapper))
  }

  @Singleton
  @Provides
  def playerPicksDao(scheduler: ScheduledExecutorService, dynamoMapper: DynamoDBMapper, playerDao: PlayerDao): PlayerPicksDao = {
    new PlayerPicksDaoCaching(scheduler, new PlayerPicksDaoDynamo(dynamoMapper, playerDao), playerDao)
  }

  @Singleton
  @Provides
  def winnersDao(scheduler: ScheduledExecutorService, dynamoMapper: DynamoDBMapper): WinnersDao = {
    new WinnersDaoCaching(scheduler, new WinnersDaoDynamo(dynamoMapper))
  }

  @Singleton
  @Provides
  def dynamoMapper(dynamoDb: AmazonDynamoDB): DynamoDBMapper = new DynamoDBMapper(dynamoDb)

  def productionDynamoDb: AmazonDynamoDB = {
    println("Using PRODUCTION database.")
    AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build()
  }

  def localhostDynamoDb: AmazonDynamoDB = {
    val endpoint = "http://localhost:8120"
    println(s"Running against local database at $endpoint")
    AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
      new AwsClientBuilder.EndpointConfiguration(endpoint, "us-west-2"))
        .build()
  }

  def createLocalDynamoDb: AmazonDynamoDB = {
    //To run this, you need to set up a native library path.
    // in Tomcat:
    //   export CATALINA_OPTS=-Djava.library.path=$PATH:/home/fkearney8/IdeaProjects/OscarParty/native-libs
    //   This is in my current local install's setenv.sh.
    // in Junit:
    //  set the system property:
    //      System.setProperty("sqlite4java.library.path", "native-libs")

    val localDynamoDb: AmazonDynamoDB = DynamoDBEmbedded.create().amazonDynamoDB()

    createPlayerTable
    createPlayerPicksTable
    createWinnersTable

    def createPlayerTable = {
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
    }

    def createPlayerPicksTable = {
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
    }

    def createWinnersTable = {
      localDynamoDb.createTable(
        List(
          new AttributeDefinition(WinnerDataObject.CATEGORY_ATTR, "S")
        ).asJava,
        WinnerDataObject.WINNER_TABLE,
        List(
          new KeySchemaElement()
              .withAttributeName(WinnerDataObject.CATEGORY_ATTR)
              .withKeyType(KeyType.HASH)
        ).asJava,
        new ProvisionedThroughput(5L, 5L)
      )
    }

    localDynamoDb
  }
}
