package com.oscarparty.servlets.data.nominees

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.Tag

import scala.collection.JavaConverters._

case class CategoryData(id: Int, name: String, points1: Int, points2: Int, points3: Int)

case class Category(id: Int, name: String, points1: Int, points2: Int, points3: Int, nominees: List[Nominee]) {
  def this(categoryData: CategoryData, nominees: List[Nominee]) = {
    this(categoryData.id, categoryData.name, categoryData.points1, categoryData.points2, categoryData.points3, nominees)
  }

  def nomineesJava: java.util.List[Nominee] = nominees.asJava
}

class Categories(tag: Tag) extends Table[CategoryData] (tag, "categories") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def points1 = column[Int]("points1")
  def points2 = column[Int]("points2")
  def points3 = column[Int]("points3")

  def * = (id, name, points1, points2, points3) <> (CategoryData.tupled, CategoryData.unapply)
}


