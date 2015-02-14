package com.oscarparty.servlets.data.nominees

import com.oscarparty.servlets.data.SlickDAO
import com.oscarparty.servlets.selection.OscarCategory

import scala.collection.JavaConverters._
import scala.slick.driver.PostgresDriver.simple._

class AllOscarNominees2015 extends SlickDAO with NomineesInterface {

  val categories = TableQuery[Categories]
  val nominees = TableQuery[Nominees]

  override def getCategories: Seq[OscarCategory] = DB.withSession { implicit session =>
    categories.list.map { category =>
      toOscarCategory(category)
    }
  }

  override def categoryNames: Array[String] = DB.withSession { implicit session =>
    categories.list.map { _.name }.toArray
  }

  override def categoriesJava: java.util.List[OscarCategory] = getCategories.toList.asJava

  override def findCategory(categoryName: String): OscarCategory = DB.withSession { implicit session =>
    categories.filter(_.name === categoryName).list.headOption match {
      case None => throw new IllegalArgumentException("Could not find category " + categoryName)
      case Some(category) => toOscarCategory(category)
    }
  }


  //TODO eliminate the need for this, send the Category and Nominee models throughout the code base
  private def toOscarCategory(category: Categories#TableElementType)(implicit session: Session): OscarCategory = {
    new OscarCategory(category.name, category.nominees.map(_.name).toArray,
      Array(category.point1, category.points2, category.points3))
  }
}


