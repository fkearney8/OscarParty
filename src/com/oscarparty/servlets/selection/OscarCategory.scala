package com.oscarparty.servlets.selection

import scala.collection.JavaConverters._


class OscarCategory(val name: String, val nominees: Array [String], val points: Array[Int] = Array (3,2,1)) {

  val columnPrefix = name.replace(' ', '_')

  def nomineesJava : java.util.List[String] = {
    nominees.toList.asJava
  }

  def pointsJava : java.util.List[Integer] = {
    points.map{ java.lang.Integer.valueOf }.toList.asJava
  }

}
