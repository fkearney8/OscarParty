//package com.oscarparty.servlets
//
//import java.io.PrintWriter
//import java.util.{Map => JMap}
//import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
//import javax.servlet.{RequestDispatcher, ServletException}
//
//import com.oscarparty.servlets.PicksSubmittedServlet.EachPickIdentifier
//import com.oscarparty.servlets.data.PlayerPicksDAO
//import com.oscarparty.servlets.data.PlayerPicksDAO.PlayerPickForInsertion
//import com.oscarparty.servlets.data.nominees.OscarNomineesDAO
//
//import scala.collection.JavaConverters._
//
///**
// * Servlet for when a player has submitted picks to be saved.
// */
//class PicksSubmittedServlet extends HttpServlet {
//
//  override def doPost(req: HttpServletRequest, res: HttpServletResponse) {
//    res.setContentType("text/html; charset=UTF-8")
//    res.setCharacterEncoding("UTF-8")
//
//    val parameterMapJava: JMap[String, Array[String]] = req.getParameterMap
//    val parameterMap = parameterMapJava.asScala.toMap
//
//    val writer: PrintWriter = res.getWriter
//
//    //find the newly added player
//    val newPlayer = findPlayerFromRequest(parameterMap)
//    val playerPicks = filterPicksFromRequest(parameterMap)
//
//    val convertedPlayerPicks = convertPlayerPicks(playerPicks)
//
//    PlayerPicksDAO.addPlayerPicks(newPlayer, convertedPlayerPicks)
//    writer.println("Your picks have been submitted!")
//    val dispatcher: RequestDispatcher = getServletContext.getRequestDispatcher("/picksSubmitted.jsp")
//    dispatcher.forward(req, res)
//  }
//
//  private def convertPlayerPicks(playerPicks: Map[EachPickIdentifier, Int]): List[PlayerPickForInsertion] = {
//    //iterate through the categories, finding the picks for each rank and create the PlayerPick object
//
//    def findPick(categoryId: Int, rankName: String): Int = {
//      val nomineeNamePicked = playerPicks(EachPickIdentifier(categoryId, rankName))
//      OscarNomineesDAO.getNominee(nomineeNamePicked).id
//    }
//
//    OscarNomineesDAO.getCategories.map { category =>
//      PlayerPickForInsertion(category.id,
//        findPick(category.id, "topPick"),
//        findPick(category.id, "midPick"),
//        findPick(category.id, "botPick"))
//    }.toList
//  }
//
//  private def findPlayerFromRequest(requestParams: Map[String, Array[String]]): String = {
//    val newPlayerEntry = requestParams.iterator.find { isPlayerNameParam }
//    newPlayerEntry match {
//      case Some((key, values)) =>
//        assertOneValue(key, values)
//        //the first value is the player name
//        values.head
//      case _ => throw new ServletException("No player name added")
//    }
//  }
//
//  //TODO refactor to get the ID of the nominees and categories instead of the names. Have to thread the IDs through to the form on the page.
//  private def filterPicksFromRequest(requestParams: Map[String, Array[String]]): Map[EachPickIdentifier, Int] = {
//    requestParams.filter(!isPlayerNameParam(_)).map { case (key, selectionList) =>
//      //result params are like <id>.<rank> = [<nominee>] aka "2.botPick" = "[Amy Adams]"
//      assertOneValue(key, selectionList)
//      val splitCategory = key.split('.')
//      if (splitCategory.length != 2)
//        throw new ServletException(s"Request param key $key does not make sense, no '.' separator for category name and pick rank.")
//      EachPickIdentifier(Integer.parseInt(splitCategory(0)), splitCategory(1)) -> Integer.parseInt(selectionList.head)
//    }
//  }
//
//  private def isPlayerNameParam(paramEntry: (String, Array[String])): Boolean = {
//    paramEntry match {
//      case (key, values) => key == "userName"
//      case _ => false
//    }
//  }
//
//  def assertOneValue(key: String, values: Array[String]) {
//    if (values.length != 1) {
//      throw new ServletException(s"There were the wrong number of values ($values) for $key")
//    }
//  }
//}
//
//
//object PicksSubmittedServlet {
//  private case class EachPickIdentifier(categoryId: Int, pickRank: String)
//}