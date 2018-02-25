package com.oscarparty.data.dao

import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.dao.mappers.PlayerPicksMapper
import com.oscarparty.data.nominees._
import com.oscarparty.data.{Player, PlayerPicks}

import scala.collection.immutable.Iterable



class PlayerPicksDAO @Inject() (dynamoDb: AmazonDynamoDB,
                                playerDao: PlayerDAO) {

  private val playerPicksDataMapper = new PlayerPicksMapper()
  private val playerPicksDynamoMapper = new DynamoDBMapper(dynamoDb)

  def savePlayerPicks(picks: PlayerPicks): Unit = {

    val pickDos: Iterable[PlayerPicksDataObject] = picks.picksByCat.map { case (_, categoryPicks) =>
        playerPicksDataMapper.toDataObject(picks.playerId, categoryPicks)
    }

    pickDos.foreach(pickDo => playerPicksDynamoMapper.save(pickDo))
  }

  def getPlayerPicks(playerId: String): PlayerPicks = {
    //for each category load their picks
    val playerPicksDoList = CategoryName.values.flatMap { eachCategory =>
      val picksForCat = playerPicksDynamoMapper.load(classOf[PlayerPicksDataObject], playerId, eachCategory.toString)
      Option(picksForCat)
    }
    //convert to domain object from the list of data objects
    playerPicksDataMapper.toDomainObject(playerId, playerPicksDoList.toSeq)
  }

  def allPlayerPicks: Map[Player, PlayerPicks] = {
    playerDao.allPlayers.map { player =>
      val playerPicks = getPlayerPicks(player.id)
      player -> playerPicks
    }.toMap
  }

}
