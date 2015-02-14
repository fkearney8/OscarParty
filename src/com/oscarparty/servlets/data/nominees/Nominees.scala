package com.oscarparty.servlets.data.nominees

import scala.slick.lifted.Tag
import scala.slick.driver.PostgresDriver.simple._

case class Nominee(id: Int, categoryId: Int, name: String)

class Nominees(tag: Tag) extends Table[Nominee] (tag, "nominees") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def categoryId = column[Int]("category")
  def name = column[String]("name")

  def * = (id, categoryId, name) <> (Nominee.tupled, Nominee.unapply)

  def category = foreignKey("nominees_category_fkey", categoryId, TableQuery[Categories])(_.id)
}