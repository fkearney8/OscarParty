package com.oscarparty.servlets.selection

import scala.collection.mutable.ArrayBuffer
import collection.JavaConversions._

class AllOscarNominees {

//  val oscarCategories = new Array[OscarCategory]
  val hiString = "scala class here saying hi with an implicit getter"

  val categories = new ArrayBuffer[OscarCategory]()
  categories += new OscarCategory("Best Picture",
    ArrayBuffer ("Her", "Nebraska", "Wolf of Wall Street", "Gravity"))

  def categoriesJava : java.util.List[OscarCategory] = {
      val jul: java.util.List[OscarCategory] = categories
      jul
  }

}
