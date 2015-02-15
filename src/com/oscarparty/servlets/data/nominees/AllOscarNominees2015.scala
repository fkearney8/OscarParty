package com.oscarparty.servlets.data.nominees

import javax.servlet.ServletException

import com.oscarparty.servlets.data.{WinnersDAO, SlickDAO}

import scala.collection.JavaConverters._
import scala.slick.driver.PostgresDriver.simple._

class AllOscarNominees2015 extends SlickDAO {

  val categories = TableQuery[Categories]
  val nominees = TableQuery[Nominees]

  def getNominee(id: Int): Nominee = DB.withSession { implicit session =>
    nominees.filter(_.id === id).list.head
  }

  def getCategory(id: Int): Category = DB.withSession { implicit session =>
    categories.filter(_.id === id).list.head
  }

  def getCategories: Seq[Category] = DB.withSession { implicit session =>
    categories.list
  }

  def categoryNames: Array[String] = DB.withSession { implicit session =>
    categories.list.map { _.name }.toArray
  }

  def categoriesJava: java.util.List[Category] = getCategories.toList.asJava

  def categoryNominees(category: Category): List[Nominee] = DB.withSession { implicit session =>
      nominees.filter(_.categoryId === category.id).list
  }

  def categoryNomineesJava(category: Category): java.util.List[Nominee] =
      categoryNominees(category).asJava

  def findNomineeByName(nomineeName: String): Nominee = DB.withSession { implicit session =>
    nominees.filter(nominee => nominee.name === nomineeName).list.headOption match {
      case Some(nom) => nom
      case None => throw new ServletException(s"No nominee with name $nomineeName")
    }
  }

  def findCategoryByName(categoryName: String): Category = DB.withSession { implicit session =>
    categories.filter(_.name === categoryName).list.headOption match {
      case Some(cat) => cat
      case None => throw new ServletException(s"No category with name $categoryName")
    }
  }

  def categoriesWithoutWinners: List[Category] = DB.withSession { implicit session =>
    categories.list.map { category =>
      val winnerForCat = WinnersDAO.findCategoryWinner(category.id)
      (category, winnerForCat)
    }.filter { case (category, winnerOption) =>
      winnerOption.isEmpty
    }.map { case (category, winnerOption) =>
      category
    }
  }
}


