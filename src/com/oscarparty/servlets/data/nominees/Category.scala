package com.oscarparty.servlets.data.nominees

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

case class Category(id: Int, name: String, point1: Int, points2: Int, points3: Int) {
  def nominees(implicit session: Session): List[Nominees#TableElementType] = {
    TableQuery[Nominees].filter(_.categoryId === id).list
  }
}

class Categories(tag: Tag) extends Table[Category] (tag, "categories") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def points1 = column[Int]("points1")
  def points2 = column[Int]("points2")
  def points3 = column[Int]("points3")

  def * = (id, name, points1, points2, points3) <> (Category.tupled, Category.unapply)
}


