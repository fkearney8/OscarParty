package com.oscarparty.servlets.data.nominees
import java.util.{List => JList}

import com.oscarparty.servlets.selection.OscarCategory

trait NomineesInterface {

  def getCategories: Seq[OscarCategory]

  def categoriesJava: JList[OscarCategory]

  def findCategory(categoryName: String): OscarCategory

  def categoryNames: Array[String]
}
