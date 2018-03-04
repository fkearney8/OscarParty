package com.oscarparty.data.dao

import java.util.concurrent.{ScheduledExecutorService, TimeUnit}
import javax.inject.Inject

import com.google.common.cache.{CacheBuilder, CacheLoader, LoadingCache}
import com.oscarparty.data.Winner
import com.oscarparty.data.nominees.CategoryName

class WinnersDaoCaching @Inject() (refreshScheduler: ScheduledExecutorService,
                                   delegatedDao: WinnersDao) extends WinnersDao {

  refreshScheduler.schedule(
    new Runnable() {
      override def run(): Unit = cache.invalidateAll()
    },
    60L,
    TimeUnit.SECONDS) //we expect new winners to be added frequently - though this is all on one box right now and the cache should be updated by the save.

  private val cacheLoader = new CacheLoader[CategoryName.Value, Option[Winner]] {
    override def load(key: CategoryName.Value): Option[Winner] = delegatedDao.winnerForCategory(key)
  }

  private val cache = CacheBuilder.newBuilder()
      .maximumSize(50)
      .build(cacheLoader)


  override def winnerForCategory(category: CategoryName.Value): Option[Winner] = {
    cache.get(category)
  }

  override def saveWinner(winner: Winner): Unit = {
    delegatedDao.saveWinner(winner)
    cache.put(winner.category, Some(winner))
  }
}
