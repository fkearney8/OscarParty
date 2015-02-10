package com.oscarparty.servlets.data

import java.sql.{DriverManager, Connection}

class DAO {

  final val USER: String = "oscar"
  final val PASS: String = "whatever"
  val dbUrl: String = "jdbc:postgresql:OscarParty"

  def getConnection() : Connection = {
    Class.forName("org.postgresql.Driver")
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn
  }

}
