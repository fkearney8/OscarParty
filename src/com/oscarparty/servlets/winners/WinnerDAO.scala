package com.oscarparty.servlets.winners

import com.oscarparty.servlets.data.DAO
import scala.collection.mutable.ArrayBuffer

class WinnerDAO extends DAO {
  def saveWinner(winner : Winner) {
    val insertString = "INSERT INTO winners (category, winner) values (?, ?);"
    val conn = getConnection()
    val preparedInsertStatement = conn.prepareStatement(insertString)
    preparedInsertStatement.setString(1, winner.category)
    preparedInsertStatement.setString(2, winner.winner)
    preparedInsertStatement.executeUpdate()
    conn.close()
  }

  def readAllWinners() : ArrayBuffer[Winner] = {
    val conn = getConnection()
    val returnBuffer = new ArrayBuffer[Winner]()
    val readString = "select * from winners;"
    val readWinnersPs = conn.prepareStatement(readString)
    val winnersRs = readWinnersPs.executeQuery()
    while (winnersRs.next()) {
      val category = winnersRs.getString("category")
      val winner = winnersRs.getString("winner")
      returnBuffer += new Winner(category, winner)
    }
    conn.close()
    returnBuffer
  }
}

object WinnerDAO {
  val allWinners = new WinnerDAO().readAllWinners()
  def findCategoryWinner(categoryName : String) : Winner = {
    val returnedWinnerList = allWinners.filter(winner => categoryName.equals(winner.category))
    if (returnedWinnerList.size == 1)
      returnedWinnerList(0)
    else
      null
  }
}