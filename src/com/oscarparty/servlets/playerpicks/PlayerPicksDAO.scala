package com.oscarparty.servlets.playerpicks

import java.sql.{PreparedStatement, DriverManager, Connection}
import scala.collection.mutable.ArrayBuffer

class PlayerPicksDAO {
  final val USER: String = "postgres"
  final val PASS: String = "Amasa1217"

  def storePicks(playerPicks : PlayerPicks) {
    System.out.println("Connecting to database...")
    val dbUrl: String = "jdbc:postgresql:OscarParty"
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn.setAutoCommit(true)
    val storePicksStatement = prepareInsertStatement(conn, playerPicks)
    System.out.println("Storing picks with statement: " + storePicksStatement.toString)
    storePicksStatement.executeUpdate()
  }

  private def prepareInsertStatement(conn : Connection, playerPicks : PlayerPicks) : PreparedStatement = {
    //database columns are like best_animated_short_film_toppick
    var statementColsSql = new StringBuilder("Insert into userpicks (")
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
}
