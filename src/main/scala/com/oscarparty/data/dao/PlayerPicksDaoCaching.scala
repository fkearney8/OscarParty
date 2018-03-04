package com.oscarparty.data.dao

import java.util.concurrent.{ScheduledExecutorService, ScheduledThreadPoolExecutor, TimeUnit}
import javax.inject.Inject

import com.google.common.cache.{CacheBuilder, CacheLoader}
import com.oscarparty.data.{Player, PlayerPicks}

class PlayerPicksDaoCaching @Inject() (refreshScheduler: ScheduledExecutorService,
                                       delegateDao: PlayerPicksDao,
                                       playerDao: PlayerDao) extends PlayerPicksDao {

  //we don't expect picks to ever change right now, just new ones added
  private val cacheTtlMillis = 1000 * 60 * 10

  refreshScheduler.schedule(new Runnable {
    override def run(): Unit = {
      cache.invalidateAll()
    }
  }, cacheTtlMillis, TimeUnit.MILLISECONDS)

  private val cacheLoader = new CacheLoader[String, PlayerPicks] {
    override def load(playerId: String): PlayerPicks = {
      delegateDao.getPlayerPicks(playerId)
    }
  }
  private val cache = CacheBuilder.newBuilder()
      .maximumSize(100)
      .build(cacheLoader)


  override def savePlayerPicks(picks: PlayerPicks): Unit = {
    delegateDao.savePlayerPicks(picks)
    cache.put(picks.playerId, picks)
  }

  override def getPlayerPicks(playerId: String): PlayerPicks = {
    cache.get(playerId)
  }


  private var lastReadAllTime: Long = 0

  override def allPlayerPicks: Map[Player, PlayerPicks] = {
    //if past the cache duration, then read from the delegate and save everything.
    val now = System.currentTimeMillis()

    if (lastReadAllTime + cacheTtlMillis < now) {
      val allPicksFromDelegate = delegateDao.allPlayerPicks
      lastReadAllTime = now
      allPicksFromDelegate.foreach { case (player, playerPicks) =>
        cache.put(player.id, playerPicks)
      }
      allPicksFromDelegate
    } else {
      //use the players from injected DAO
      playerDao.allPlayers.map { eachPlayer =>
        val cachedPlayerPicks = cache.get(eachPlayer.id)
        eachPlayer -> cachedPlayerPicks
      }.toMap
    }
  }
}
