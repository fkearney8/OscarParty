package com.oscarparty.data.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.nominees.CategoryName
import com.oscarparty.data.{CategoryPicks, PlayerPicks}
import com.oscarparty.guice.DataModule
import org.junit.Assert._
import org.junit.Test

class PlayerPicksDAOTest {
  System.setProperty("sqlite4java.library.path", "native-libs")

  @Test
  def testFindPlayerPicks(): Unit = {
    val localDynamo = new DataModule().createLocalDynamoDb
    val dynamoMapper = new DynamoDBMapper(localDynamo)
    val playerDao = new PlayerDaoDynamo(dynamoMapper)
    val playerPicksDao = new PlayerPicksDaoDynamo(dynamoMapper, playerDao)

    val playerId = playerDao.savePlayer("theresa").id
    val picks = List(CategoryPicks(CategoryName.Actor, 0, 1, 3))
    playerPicksDao.savePlayerPicks(PlayerPicks(playerId, picks))

    val retrievedPicks = playerPicksDao.getPlayerPicks(playerId)
    val maybeActorPicks = retrievedPicks.picksForCategory(CategoryName.Actor)
    assertTrue(maybeActorPicks.isDefined)
    val actorPicks = maybeActorPicks.get
    assertEquals(0, actorPicks.pick1.index)
    assertEquals(1, actorPicks.pick2.index)
    assertEquals(3, actorPicks.pick3.index)
  }
}
