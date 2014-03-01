package com.oscarparty.servlets.data

import java.sql.{DriverManager, Connection}

class DAO {

  final val USER: String = "postgres"
  final val PASS: String = "Simple"
  val dbUrl: String = "jdbc:postgresql:OscarParty"

  def getConnection() : Connection = {
    Class.forName("org.postgresql.Driver");
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn
  }

}
