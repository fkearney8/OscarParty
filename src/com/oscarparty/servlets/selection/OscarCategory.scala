package com.oscarparty.servlets.selection

import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

class OscarCategory(val name: String, val nominees: ArrayBuffer[String]) {

  def nomineesJava : java.util.List[String] = {
    val jul: java.util.List[String] = nominees
    jul
  }

}
