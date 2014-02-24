package com.oscarparty.servlets.playerpicks

import java.sql.{ResultSet, PreparedStatement, DriverManager, Connection}
import scala.collection.mutable.ArrayBuffer
import com.oscarparty.servlets.selection.AllOscarNominees

class PlayerPicksDAO {
  final val USER: String = "postgres"
  final val PASS: String = "Simple"
  val dbUrl: String = "jdbc:postgresql:OscarParty"

  def storePicks(playerPicks : PlayerPicks) {
    System.out.println("Connecting to database...")
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn.setAutoCommit(true)
    val storePicksStatement = prepareInsertStatement(conn, playerPicks)
    System.out.println("Storing picks with statement: " + storePicksStatement.toString)
    storePicksStatement.executeUpdate()
  }

  private def prepareInsertStatement(conn : Connection, playerPicks : PlayerPicks) : PreparedStatement = {
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
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    val readPicksStatement = conn.prepareStatement("select * from userpicks where username = ?")
    readPicksStatement.setString(1, userName)
    val resultSet = readPicksStatement.executeQuery()
    //read in all picks
    val allNoms = new AllOscarNominees
    val playerPicks = new PlayerPicks
    while (resultSet.next()) {
      //iterate through each category
      for (eachCat <- allNoms.categories) {
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
}
