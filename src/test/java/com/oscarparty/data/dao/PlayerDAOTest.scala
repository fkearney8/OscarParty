package com.oscarparty.data.dao

import com.oscarparty.data.Player
import com.oscarparty.guice.DataConfig
import org.junit.Assert._
import org.junit._

class PlayerDAOTest {

  @Test
  def testFindPlayerById(): Unit = {
    val localDynamo = new DataConfig().createLocalDynamoDb
    val playerDao = new PlayerDAO(localDynamo)
    playerDao.savePlayer("frank")

    val player = playerDao.getPlayer(0)
    assertNotNull(player)
    assertEquals("frank", player.name)
  }

}
