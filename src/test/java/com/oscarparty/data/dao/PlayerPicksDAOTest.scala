package com.oscarparty.data.dao

import com.oscarparty.data.nominees.{CategoryName, Nominee}
import com.oscarparty.data.{CategoryPicks, Player, PlayerPicks}
import com.oscarparty.guice.DataConfig
import org.junit.Assert.{assertEquals, assertNotNull}
import org.junit.Test

class PlayerPicksDAOTest {

  @Test
  def testFindPlayerPicks(): Unit = {
    val localDynamo = new DataConfig().createLocalDynamoDb
    val playerDao = new PlayerDAO(localDynamo)
    val playerPicksDao = new PlayerPicksDAO(localDynamo)

    val playerId = playerDao.savePlayer("theresa").id
    val picks = List(CategoryPicks(CategoryName.Actor, 0, 1, 3))
    playerPicksDao.savePlayerPicks(PlayerPicks(playerId, picks))

    val retrievedPicks = playerPicksDao.getPlayerPicks(playerId)
    val actorPicks = retrievedPicks.picksForCategory(CategoryName.Actor)
    assertEquals(0, actorPicks.pick1.index)
    assertEquals(1, actorPicks.pick2.index)
    assertEquals(3, actorPicks.pick3.index)
  }
}
