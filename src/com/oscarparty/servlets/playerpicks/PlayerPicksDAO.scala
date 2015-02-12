package com.oscarparty.servlets.playerpicks

import java.sql.{Connection, PreparedStatement}

import com.oscarparty.servlets.data.DAO
import com.oscarparty.servlets.data.nominees.AllOscarNominees2015

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class PlayerPicksDAO extends DAO {
  def storePicks(playerPicks: PlayerPicks) {
    System.out.println("Connecting to database...")
    val conn = getConnection()
    conn.setAutoCommit(true)
    val storePicksStatement = prepareInsertStatement(conn, playerPicks)
    System.out.println("Storing picks with statement: " + storePicksStatement.toString)
    storePicksStatement.executeUpdate()
  }

  private def prepareInsertStatement(conn: Connection, playerPicks: PlayerPicks) : PreparedStatement = {
    //database columns are like best_animated_short_film_toppick
    val statementColsSql = new StringBuilder("Insert into userpicks (")
    val statementValuesSql = new StringBuilder("values (")
    val orderedPicks = new ArrayBuffer[String]()
    val pickPriorities = Array("topPick", "midPick", "botPick")

    for ((eachCatName, eachCatPicks) <- playerPicks.allPicks) {
      for (eachPriority <- pickPriorities) {
        var colName = eachCatName + "_" + eachPriority
        colName = colName.replace(' ', '_')
        val pick = eachCatPicks.getPickAtPriority(eachPriority)

        statementColsSql.append(colName).append(", ")
        statementValuesSql.append("?").append(", ")

        orderedPicks += pick
      }
    }

    //append for the username
    statementColsSql.append("username")
    statementValuesSql.append("?")
    orderedPicks += playerPicks.userName

    statementColsSql.append(") ")
    statementValuesSql.append(");")

    val preparedStatementAssembledSql = statementColsSql.toString + statementValuesSql.toString
    val preparedStatement = conn.prepareStatement(preparedStatementAssembledSql)
    for (i <- 0 until orderedPicks.length) {
      val eachOrderedPick = orderedPicks(i)
      preparedStatement.setString(i+1, eachOrderedPick)
    }
    preparedStatement
  }
  
  def readLastPicksForUsername(userName : String) : PlayerPicks = {
    val conn = getConnection()
    val readPicksStatement = conn.prepareStatement("select * from userpicks where username = ?")
    readPicksStatement.setString(1, userName)
    val resultSet = readPicksStatement.executeQuery()
    conn.close()
    //read in all picks
    val allNoms = new AllOscarNominees2015
    val playerPicks = new PlayerPicks
    while (resultSet.next()) {
      //iterate through each category
      for (eachCat <- allNoms.getCategories) {
        val colPrefixForCat = eachCat.columnPrefix
        for (eachPriority <- Array("topPick", "midPick", "botPick")) {
          val colName = colPrefixForCat + "_" + eachPriority
          val valueRead = resultSet.getString(colName)
          System.out.println("Read value " + valueRead + " for column name " + colName)
          playerPicks.addPick(eachCat.name, eachPriority, valueRead)
        }
      }
      //set username
      playerPicks.userName = resultSet.getString("username")
    }
    playerPicks
  }

  //only reads the last picks for a player with the same name
  def readAllPlayerPicks() : java.util.List[PlayerPicks] = {
    val returnMap = new mutable.HashMap[String, PlayerPicks]()
    val conn = getConnection()
    //read in all picks
    val allNoms = new AllOscarNominees2015
    val readPicksStatement = conn.prepareStatement("select * from userpicks")
    val resultSet = readPicksStatement.executeQuery()
    while (resultSet.next()) {
      //iterate through each category
      val playerPicks = new PlayerPicks
      //set username
      playerPicks.userName = resultSet.getString("username")
      val paidString: String = resultSet.getString("paid")
      playerPicks.paid = paidString != null && paidString.equals("true")

      for (eachCat <- allNoms.getCategories) {
        val colPrefixForCat = eachCat.columnPrefix
        for (eachPriority <- Array("topPick", "midPick", "botPick")) {
          val colName = colPrefixForCat + "_" + eachPriority
          val valueRead = resultSet.getString(colName)
          System.out.println("Read value " + valueRead + " for column name " + colName)
          playerPicks.addPick(eachCat.name, eachPriority, valueRead)
        }
      }
      returnMap += (playerPicks.userName -> playerPicks)
    }
    val returnList = new java.util.LinkedList[PlayerPicks]()
    for ((username, playerPicks) <- returnMap) {
      returnList.add(playerPicks)
    }
    conn.close()
    returnList
  }

  def updatePaid(userName : String, paid : Boolean) {
    val conn = getConnection()
    val updatePaidPs = conn.prepareStatement("update userpicks set paid=? where userName=?" )
    updatePaidPs.setString(1, if (paid) "true" else "false")
    updatePaidPs.setString(2, userName)
    updatePaidPs.executeUpdate()
    conn.close()
  }
}
