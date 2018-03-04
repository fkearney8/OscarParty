package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.google.common.annotations.VisibleForTesting
import com.oscarparty.data.NextCategory
import com.oscarparty.data.dao.WinnersDAO
import com.oscarparty.data.nominees.{CategoryName, CategoryNominees, Nominee, Nominees2018}
import com.oscarparty.utils.JsonUtil
import WinnerPickerServlet._

import scala.collection.immutable.ListMap

@Singleton
class WinnerPickerServlet @Inject() (winnersDao: WinnersDAO,
                                     jsonUtil: JsonUtil) extends HttpServlet {

  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    val categoriesWithoutWinners = getCategoriesWithoutWinners(winnersDao)
    val categoriesWithoutWinnersStrings = categoriesWithoutWinners.map { eachCategory =>
      CategoryStrings(eachCategory.toString, eachCategory.displayName)
    }

    //a map of the category name to an array of nominations
    val catsToNoms = constructCatsToNomineesSelectionMap

    req.setAttribute("categoriesWithoutWinners", jsonUtil.toJson(categoriesWithoutWinnersStrings))
    req.setAttribute("nextCategory", NextCategory.nextCategory)
    req.setAttribute("catsToNomsMap", jsonUtil.toJson(catsToNoms))

    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
  }

  private def getCategoriesWithoutWinners(winnersDao: WinnersDAO): Set[CategoryName.Value] = {
    CategoryName.values.filter { eachCat =>
      winnersDao.winnerForCategory(eachCat).isEmpty
    }
  }
}

object WinnerPickerServlet {
  @VisibleForTesting
  def constructCatsToNomineesSelectionMap: ListMap[String, Seq[Nominee]] = {
    val categoryNomineesMap: Map[CategoryName.Value, Seq[Nominee]] = Nominees2018.categoryNominees.map {
      case (category, catNoms) =>
        val catNomsWithNone: Seq[Nominee] = NoneSelectedNominee +: catNoms.nominees
        category -> catNomsWithNone
    }

    //establish the ordering we want
    val inOrder: Seq[(CategoryName.Value, Seq[Nominee])] = categoryNomineesMap.toSeq.sortBy(_._1.id)
    //add the None category at the front
    val plusNone = (NoneSelectedCategory, Seq(NoneSelectedNominee)) +: inOrder

    //convert the category to a string, because jackson doesn't like the enums
    val catToString = plusNone.map { eachCatAndNoms =>
      eachCatAndNoms._1.toString -> eachCatAndNoms._2
    }

    //convert to a map, preserving the order
    ListMap(catToString: _*)
  }

  object NoneSelectedNominee extends Nominee("None", -1)
  object NoneSelectedCategory extends CategoryName.Value {
    override def id: Int = -1
    override def toString: String = "None"
  }
}

/** Jackson for scala (current version) doesn't support serializing enumerations.
  * https://github.com/FasterXML/jackson-module-scala/issues/225
  * So we make this representation of it to get the name and display name to the screen, and
  * understand the selection when it comes back in.
  */
case class CategoryStrings(enumName: String, displayName: String)


