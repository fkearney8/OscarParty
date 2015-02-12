package com.oscarparty.servlets.data.nominees

import com.oscarparty.servlets.data.SlickDAO
import com.oscarparty.servlets.selection.OscarCategory

import scala.collection.JavaConverters._
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

class AllOscarNominees2015 extends SlickDAO with NomineesInterface {


  override def getCategories: Seq[OscarCategory] = DB.withSession { implicit session =>
    categories.list.map { category =>
      toOscarCategory(category)
    }
  }

  //TODO eliminate the need for this, send the Category and Nominee models throughout the code base
  private def toOscarCategory(category: Categories#TableElementType)(implicit session: Session): OscarCategory = {
    val nomineesForCategory = nominees.filter(_.categoryId === category.id)
    new OscarCategory(category.name, nomineesForCategory.list.map(_.name).toArray,
      Array(category.point1, category.points2, category.points3))
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

  case class Category(id: Int, name: String, point1: Int, points2: Int, points3: Int)

  class Categories(tag: Tag) extends Table[Category] (tag, "categories") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def points1 = column[Int]("points1")
    def points2 = column[Int]("points2")
    def points3 = column[Int]("points3")

    def * = (id, name, points1, points2, points3) <> (Category.tupled, Category.unapply)
  }
  val categories = TableQuery[Categories]

  case class Nominee(id: Int, categoryId: Int, name: String)

  class Nominees(tag: Tag) extends Table[Nominee] (tag, "nominees") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def categoryId = column[Int]("category")
    def name = column[String]("name")

    def * = (id, categoryId, name) <> (Nominee.tupled, Nominee.unapply)

    def category = foreignKey("nominees_category_fkey", categoryId, categories)(_.id)
  }
  val nominees = TableQuery[Nominees]
}


