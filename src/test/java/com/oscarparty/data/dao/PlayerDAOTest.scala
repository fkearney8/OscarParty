package com.oscarparty.data.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.Player
import com.oscarparty.guice.DataConfig
import org.junit.Assert._
import org.junit._

import scala.util.Try

class PlayerDAOTest {

  val localDynamo = new DataConfig().createLocalDynamoDb
  val playerDao = new PlayerDAO(new DynamoDBMapper(localDynamo))

  @Test
  def testFindPlayerById(): Unit = {
    val playerId = playerDao.savePlayer("frank").id
    println(s"Looking for player $playerId")
    val player = playerDao.getPlayerById(playerId)
    assertNotNull(player)
    assertEquals("frank", player.name)
  }

  @Test
  def tryToDoubleAddPlayer(): Unit = {
    playerDao.savePlayer("John")
    val trySaveAgain = Try { playerDao.savePlayer("John") }
    assertTrue(trySaveAgain.isFailure)
    trySaveAgain.failed.get.printStackTrace()
  }

  @Test
  def getAllPlayers(): Unit = {
    playerDao.savePlayer("Thing1")
    playerDao.savePlayer("Thing2")
    playerDao.savePlayer("Thing3")
    assertTrue(playerDao.allPlayers.length >= 3)
  }

}
