package com.oscarparty.servlets.data

import java.util.Properties

import scala.slick.driver.PostgresDriver.simple._

class SlickDAO extends DAO {

  val DB = Database.forURL(dbUrl, USER, PASS, new Properties, driverName)
}
