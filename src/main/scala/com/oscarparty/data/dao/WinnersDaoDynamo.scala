package com.oscarparty.data.dao

import javax.inject.Inject

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.oscarparty.data.Winner
import com.oscarparty.data.dao.mappers.{WinnerDataObject, WinnerMapper}
import com.oscarparty.data.nominees.CategoryName

class WinnersDaoDynamo @Inject()(dynamoMapper: DynamoDBMapper) extends WinnersDao {

  private val winnerMapper = new WinnerMapper()

  def winnerForCategory(category: CategoryName.Value): Option[Winner] = {
    println(s"Database read: reading winner for category $category")
    val maybeWinnerDo = Option(dynamoMapper.load(classOf[WinnerDataObject], category.toString))
    maybeWinnerDo.map(winnerMapper.toDomainObject)
  }

  def saveWinner(winner: Winner): Unit = {
    println(s"Database write: saving winner for category ${winner.category} to ${winner.winningNominee.name}")
    val winnerDo = winnerMapper.toDataObject(winner)
    dynamoMapper.save(winnerDo)
  }

}
