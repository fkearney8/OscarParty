package com.oscarparty.servlets.selection

import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

class OscarCategory(val name: String, val nominees: Array [String], val points: Array[Int] = Array (3,2,1)) {

  val columnPrefix = name.replace(' ', '_')

  def nomineesJava : java.util.List[String] = {
    val nomineesBuffer = new ArrayBuffer[String]()
    nomineesBuffer ++= nominees
    val jul: java.util.List[String] = nomineesBuffer
    jul
  }

  def pointsJava : java.util.List[Integer] = {
    val pointsBuffer = new ArrayBuffer[Integer]()
    for (pointElement <- points) pointsBuffer += pointElement
    val jul: java.util.List[Integer] = pointsBuffer
    jul
  }

}
