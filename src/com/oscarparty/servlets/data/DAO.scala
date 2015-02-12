package com.oscarparty.servlets.data

import java.sql.{DriverManager, Connection}

class DAO {

  final val USER: String = "oscar"
  final val PASS: String = "whatever"
  final val dbUrl: String = "jdbc:postgresql:OscarParty"
  final val driverName: String = "org.postgresql.Driver"

  def getConnection() : Connection = {
    Class.forName(driverName)
    val conn = DriverManager.getConnection(dbUrl, USER, PASS)
    conn
  }

}
