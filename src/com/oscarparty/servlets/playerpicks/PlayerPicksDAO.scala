package com.oscarparty.servlets.playerpicks

import java.sql.{PreparedStatement, DriverManager, Connection}
import scala.collection.mutable.ArrayBuffer
import com.oscarparty.servlets.selection.AllOscarNominees

class PlayerPicksDAO {
  final val USER: String = "postgres"
  final val PASS: String = "Amasa1217"

  def storePicks(playerPicks : PlayerPicks) {
    System.out.println("Connecting to database...")
    val dbUrl: String = "jdbc:postgresql:OscarParty"
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn.setAutoCommit(true)
    val storePicksStatement = prepareInsertStatement(conn, playerPicks)

    storePicksStatement.executeUpdate(insertPicksSql)
  }

  private def prepareInsertStatement(conn : Connection, playerPicks : PlayerPicks) : PreparedStatement = {
    var statementColsSql : String = "Insert into userpicks ("
    var statementValuesSql : String = "values ("
    var orderedPicks = new ArrayBuffer[String]()
    var first = true

    for ((eachCatName, eachCatPicks) <- playerPicks.allPicks) {
      addPickToStrings(statementColsSql, statementValuesSql, orderedPicks,
        eachCatName + ".topPick", eachCatPicks.topPick, first)
      first = false
      addPickToStrings(statementColsSql, statementValuesSql, orderedPicks,
        eachCatName + ".midPick", eachCatPicks.midPick, first)
      addPickToStrings(statementColsSql, statementValuesSql, orderedPicks,
        eachCatName + ".botPick", eachCatPicks.botPick, first)
    }

    statementColsSql += ") "
    statementValsSql += ")"

    val preparedStatementAssembledSql = statementColsSql + statementValesSql + ";"
    val preparedStatement = conn.prepareStatement(preparedStatementAssembledSql)
    for (orderedPick <- ordererdPicks) {
      preparedStatement.setString()
    }
  }

  private def addPickToStrings(statementColsSql : String, statementValuesSql : String,
                               orderedPicks : ArrayBuffer[String], colName : String, pick : String, first : Boolean) {
    var colNameAdjusted = colName
    colNameAdjusted = colName.replace(" ", "_")
    colNameAdjusted = colName.replace(".", "_")
    if (!first) {
      statementColsSql += ", "
      statementValuesSql += ", "
    }
    statementColsSql += colNameAdjusted
    statementValuesSql += "?"
    orderedPicks += pick
  }

}
