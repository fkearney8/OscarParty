package com.oscarparty.data.dao

import java.util.concurrent.{ScheduledExecutorService, TimeUnit}
import javax.inject.Inject

import com.google.common.cache.{CacheBuilder, CacheLoader}
import com.oscarparty.data.Player

import scala.collection.JavaConverters._


class PlayerDaoCaching @Inject() (refreshScheduler: ScheduledExecutorService,
                                  delegateDao: PlayerDao) extends PlayerDao {

  //we don't expect players to ever change right now
  private val cacheTtlMillis = 1000 * 60 * 10

  refreshScheduler.schedule(new Runnable {
    override def run(): Unit = {
      cache.invalidateAll()
    }
  }, cacheTtlMillis, TimeUnit.MILLISECONDS)

  private val cacheLoader = new CacheLoader[String, Player] {
    override def load(id: String): Player = {
      delegateDao.getPlayerById(id)
    }
  }
  private val cache = CacheBuilder.newBuilder()
          .maximumSize(100)
          .build(cacheLoader)

  override def getPlayerByName(playerName: String): Option[Player] = {
    val entries: Map[String, Player] = cache.asMap().asScala.toMap
    val maybePlayerWithName = entries.values.find(_.name == playerName)
    maybePlayerWithName match {
      case Some(player) =>
        maybePlayerWithName
      case None =>
        //check the delegate
        val maybePlayerInDelegate = delegateDao.getPlayerByName(playerName)
        //if found, store it in cache, regardless, return it
        maybePlayerInDelegate.foreach(playerFound => cache.put(playerFound.id, playerFound))
        maybePlayerInDelegate
    }
  }

  override def getPlayerById(playerId: String): Player = {
    cache.get(playerId)
  }

  override def savePlayer(playerName: String): Player = {
    val newPlayer = delegateDao.savePlayer(playerName)
    cache.put(newPlayer.id, newPlayer)
    newPlayer
  }

  private var lastReadAllTime: Long = 0
  override def allPlayers: Seq[Player] = {
    //if past the cache duration, then read from the delegate and save everything.
    val now = System.currentTimeMillis()

    if (lastReadAllTime + cacheTtlMillis < now) {
      val allPlayersFromDelegate = delegateDao.allPlayers
      lastReadAllTime = now
      allPlayersFromDelegate.foreach(player => cache.put(player.id, player))
      allPlayersFromDelegate
    } else {
      cache.asMap().values().asScala.toSeq
    }
  }
}
