package com.oscarparty.data.dao

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.Winner
import com.oscarparty.data.nominees.{CategoryName, Nominee, Nominees2018}
import com.oscarparty.guice.DataModule
import org.junit.Assert._
import org.junit.{Assert, Test}

class WinnerDAOTest {
  System.setProperty("sqlite4java.library.path", "native-libs")

  private val localDynamo = new DataModule().createLocalDynamoDb
  private val winnerDao = new WinnersDAO(new DynamoDBMapper(localDynamo))

  @Test
  def saveAndRetrieveWinner(): Unit = {
    val category = CategoryName.AdaptedScreenplay
    val winningNom = Nominees2018.categoryNominees(category).nominee(1)
    winnerDao.saveWinner(Winner(category, winningNom))
    val maybeWinner = winnerDao.winnerForCategory(category)
    assertTrue(maybeWinner.isDefined)
    val winner = maybeWinner.get
    assertEquals(category, winner.category)
    assertEquals(winningNom, winner.winningNominee)
  }

}
