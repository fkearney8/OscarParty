package com.oscarparty.servlets

import java.io.PrintWriter
import java.util.{Map => JMap}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import javax.servlet.{RequestDispatcher, ServletException}

import com.oscarparty.servlets.PicksSubmittedServlet.EachPickIdentifier
import com.oscarparty.servlets.data.PlayerPicksDAO
import com.oscarparty.servlets.data.PlayerPicksDAO.PlayerPickForInsertion
import com.oscarparty.servlets.data.nominees.OscarNomineesDAO

import scala.collection.JavaConverters._

/**
 * Servlet for when a player has submitted picks to be saved.
 */
class PicksSubmittedServlet extends HttpServlet {

  override def doPost(req: HttpServletRequest, res: HttpServletResponse) {
    val parameterMapJava: JMap[String, Array[String]] = req.getParameterMap
    val parameterMap = parameterMapJava.asScala.toMap

    val writer: PrintWriter = res.getWriter

    //find the newly added player
    val newPlayer = findPlayerFromRequest(parameterMap)
    val playerPicks = filterPicksFromRequest(parameterMap)

    val convertedPlayerPicks = convertPlayerPicks(playerPicks)

    PlayerPicksDAO.addPlayerPicks(newPlayer, convertedPlayerPicks)
    writer.println("Your picks have been submitted!")
    val dispatcher: RequestDispatcher = getServletContext.getRequestDispatcher("/picksSubmitted.jsp")
    dispatcher.forward(req, res)
  }

  private def convertPlayerPicks(playerPicks: Map[EachPickIdentifier, String]): List[PlayerPickForInsertion] = {
    //iterate through the categories, finding the picks for each rank and create the PlayerPick object

    def findPick(categoryName: String, rankName: String): Int = {
      val nomineeNamePicked = playerPicks(EachPickIdentifier(categoryName, rankName))
      OscarNomineesDAO.findNomineeByName(nomineeNamePicked).id
    }

    OscarNomineesDAO.categoryNames.map { categoryName =>
      PlayerPickForInsertion(OscarNomineesDAO.findCategoryByName(categoryName).id,
        findPick(categoryName, "topPick"),
        findPick(categoryName, "midPick"),
        findPick(categoryName, "botPick"))
    }.toList
  }

  private def findPlayerFromRequest(requestParams: Map[String, Array[String]]): String = {
    val newPlayerEntry = requestParams.iterator.find { isPlayerNameParam }
    newPlayerEntry match {
      case Some((key, values)) =>
        assertOneValue(key, values)
        //the first value is the player name
        values.head
      case _ => throw new ServletException("No player name added")
    }
  }

  //TODO refactor to get the ID of the nominees and categories instead of the names. Have to thread the IDs through to the form on the page.
  private def filterPicksFromRequest(requestParams: Map[String, Array[String]]): Map[EachPickIdentifier, String] = {
    requestParams.filter(!isPlayerNameParam(_)).map { case (key, selectionList) =>
      //result params are like "Best Actress in a Supporting Role.botPick" = "[Amy Adams]"
      assertOneValue(key, selectionList)
      val splitCategory = key.split('.')
      if (splitCategory.length != 2)
        throw new ServletException(s"Request param key $key does not make sense, no '.' separator for category name and pick rank.")
      EachPickIdentifier(splitCategory(0), splitCategory(1)) -> selectionList.head
    }
  }

  private def isPlayerNameParam(paramEntry: (String, Array[String])): Boolean = {
    paramEntry match {
      case (key, values) => key == "userName"
      case _ => false
    }
  }

  def assertOneValue(key: String, values: Array[String]) {
    if (values.length != 1) {
      throw new ServletException(s"There were the wrong number of values ($values) for $key")
    }
  }
}


object PicksSubmittedServlet {
  private case class EachPickIdentifier(categoryName: String, pickIdentifier: String)
}