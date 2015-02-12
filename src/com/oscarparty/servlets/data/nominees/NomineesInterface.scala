package com.oscarparty.servlets.data.nominees
import java.util.{List => JList}

import com.oscarparty.servlets.selection.OscarCategory

import scala.collection.mutable.ArrayBuffer

trait NomineesInterface {

  def getCategories: ArrayBuffer[OscarCategory]

  def categoriesJava: JList[OscarCategory]

  def findCategory(categoryName: String): OscarCategory

  def categoryNames: Array[String]
}
