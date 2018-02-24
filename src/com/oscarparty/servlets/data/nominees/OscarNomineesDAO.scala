//package com.oscarparty.servlets.data.nominees
//
//import javax.servlet.ServletException
//
//import com.oscarparty.servlets.data.{WinnersDAO, SlickDAO}
//
//import scala.collection.JavaConverters._
//import scala.slick.driver.PostgresDriver.simple._
//
//object OscarNomineesDAO extends SlickDAO {
//
//  val categories = TableQuery[Categories]
//  val nominees = TableQuery[Nominees]
//
//  def getNominee(id: Int): Nominee = DB.withSession { implicit session => getNomineeMaybe(id).get }
//
//  def getNomineeMaybe(id: Int): Option[Nominee] = DB.withSession { implicit session =>
//    nominees.filter(_.id === id).list.lastOption
//  }
//
//  def getCategory(id: Int): Category = DB.withSession { implicit session => getCategoryMaybe(id).get }
//
//  def getCategoryMaybe(id: Int): Option[Category] = DB.withSession { implicit session =>
//    categories.filter(_.id === id).list.headOption.map { catData =>
//      val nomineesForCat = nomineesForCategory(catData.id)
//      new Category(catData, nomineesForCat)
//    }
//  }
//
//  def getCategories: Seq[Category] = DB.withSession { implicit session =>
//    categories.sortBy(_.id).list.map { categoryData =>
//      val noms = nomineesForCategory(categoryData.id)
//      new Category(categoryData, noms)
//    }
//  }
//
//  private def nomineesForCategory(categoryId: Int)(implicit session: Session): List[Nominees#TableElementType] = {
//    nominees.filter(_.categoryId === categoryId).list
//  }
//
//  def categoryNames: Array[String] = DB.withSession { implicit session =>
//    categories.list.map { _.name }.toArray
//  }
//
//  def categoriesJava: java.util.List[Category] = getCategories.toList.asJava
//
//
//  def findNomineeByName(nomineeName: String): Nominee = DB.withSession { implicit session =>
//    nominees.filter(nominee => nominee.name === nomineeName).list.headOption match {
//      case Some(nom) => nom
//      case None => throw new ServletException(s"No nominee with name $nomineeName")
//    }
//  }
//
//  def findCategoryByName(categoryName: String): Category = DB.withSession { implicit session =>
//    val categoryData = categories.filter(_.name === categoryName).list.head
//    val noms = nomineesForCategory(categoryData.id)
//    new Category(categoryData, noms)
//  }
//
//  def categoriesWithoutWinners: List[Category] = DB.withSession { implicit session =>
//    categories.list.map { category =>
//      val winnerForCat = WinnersDAO.findCategoryWinner(category.id)
//      (category, winnerForCat)
//    }.filter { case (category, winnerOption) =>
//      winnerOption.isEmpty
//    }.map { case (category, winnerOption) =>
//      new Category(category, nomineesForCategory(category.id))
//    }
//  }
//}
//
//
