package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import com.google.common.annotations.VisibleForTesting
import com.oscarparty.data.NextCategory
import com.oscarparty.data.dao.WinnersDao
import com.oscarparty.data.nominees.{CategoryName, Nominee, Nominees2018}
import com.oscarparty.servlets.WinnerPickerServlet._
import com.oscarparty.utils.JsonUtil

@Singleton
class WinnerPickerServlet @Inject() (winnersDao: WinnersDao,
                                     jsonUtil: JsonUtil) extends HttpServlet {

  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.setContentType("text/html; charset=UTF-8")
    resp.setCharacterEncoding("UTF-8")

    val categoriesWithoutWinners = getCategoriesWithoutWinners

    val categoriesWithoutWinnersStrings = categoriesWithoutWinners.toSeq.sortBy(_.id).map { eachCategory =>
      CategoryStrings(eachCategory.toString, eachCategory.displayName)
    }
    val categoriesWithoutWinnersStringsPlusNone = CategoryStrings("None", "None") +: categoriesWithoutWinnersStrings

    //a map of the category name to an array of nominations
    val catsToNoms = constructCatsToNomineesSelectionMap

    req.setAttribute("categoriesWithoutWinners", jsonUtil.toJson(categoriesWithoutWinnersStringsPlusNone))
    req.setAttribute("nextCategory", NextCategory.nextCategory)
    req.setAttribute("catsToNomsMap", jsonUtil.toJson(catsToNoms))

    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
  }

  private def getCategoriesWithoutWinners: Set[CategoryName.Value] = {
    CategoryName.values.filter { eachCat =>
      winnersDao.winnerForCategory(eachCat).isEmpty
    }
  }
}

object WinnerPickerServlet {
  @VisibleForTesting
  def constructCatsToNomineesSelectionMap: Map[String, Seq[Nominee]] = {
    Nominees2018.categoryNominees.map {
      case (category, catNoms) =>
        val catNomsWithNone: Seq[Nominee] = NoneSelectedNominee +: catNoms.nominees
        category.toString -> catNomsWithNone
    } + ("None" -> Seq(NoneSelectedNominee))
  }

  object NoneSelectedNominee extends Nominee("None", -1)
}

/** Jackson for scala (current version) doesn't support serializing enumerations.
  * https://github.com/FasterXML/jackson-module-scala/issues/225
  * So we make this representation of it to get the name and display name to the screen, and
  * understand the selection when it comes back in.
  */
case class CategoryStrings(enumName: String, displayName: String)


