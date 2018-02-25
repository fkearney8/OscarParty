package com.oscarparty.servlets.data

import com.oscarparty.guice.DataConfig
import org.junit.Assert.assertNotNull
import org.junit._

class PlayerDAOTest {

  @Test
  def testFindPlayerById(): Unit = {
    val localDynamo = new DataConfig().createLocalDynamoDb
    val playerDao = new PlayerDAO(localDynamo)

    val player = playerDao.getPlayer(0)
    assertNotNull(player)
  }

}
