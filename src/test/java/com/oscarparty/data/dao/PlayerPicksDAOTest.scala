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

    playerDao.savePlayer(Player(0, "frank"))
    val picks = List(CategoryPicks(CategoryName.Actor, 0, 1, 3))
    playerPicksDao.savePlayerPicks(PlayerPicks(0, picks))

    val retrievedPicks = playerPicksDao.getPlayerPicks(0)
    val actorPicks = retrievedPicks.picksForCategory(CategoryName.Actor)
    assertEquals(0, actorPicks.pick1.index)
    assertEquals(1, actorPicks.pick2.index)
    assertEquals(3, actorPicks.pick3.index)
  }
}
