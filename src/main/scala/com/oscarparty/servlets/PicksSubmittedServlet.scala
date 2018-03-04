package com.oscarparty.servlets

import java.io.PrintWriter
import java.util.{Map => JMap}
import javax.inject.Inject
import javax.inject.Singleton
import javax.servlet
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import javax.servlet.{RequestDispatcher, ServletException}

import com.oscarparty.data.{CategoryPicks, PlayerPicks}
import com.oscarparty.servlets.PicksSubmittedServlet._
import com.oscarparty.data.dao.{PlayerDao, PlayerPicksDao}
import com.oscarparty.data.nominees.{CategoryName, Nominees2018}

import scala.collection.JavaConverters._
import scala.collection.immutable
import scala.util.{Success, Try}

/**
 * Servlet for when a player has submitted picks to be saved.
 */
@Singleton
class PicksSubmittedServlet @Inject() (playerDao: PlayerDao,
                                       playerPicksDao: PlayerPicksDao) extends HttpServlet {

  override def doPost(req: HttpServletRequest, res: HttpServletResponse) {
    res.setContentType("text/html; charset=UTF-8")
    res.setCharacterEncoding("UTF-8")

    val parameterMapJava: JMap[String, Array[String]] = req.getParameterMap
    val parameterMap = parameterMapJava.asScala.toMap

//    looks like this:
//    BestPicture.topPick -> 4
//    BestPicture.midPick -> 5
//    BestPicture.botPick -> 2
//    Actor.topPick -> 4
//    Actor.midPick -> 2
//    Actor.botPick -> 1
//    userName -> MyName

    println("Parameter map input:")
    parameterMapJava.forEach((selectionKey, selectionValue) =>
      println(s"$selectionKey -> ${selectionValue.mkString}")
    )

    val writer: PrintWriter = res.getWriter

    //find the newly added player
    val newPlayer = findPlayerFromRequest(parameterMap)
    val playerPicks = getPicksFromRequest(parameterMap)

    val newSavedPlayer = playerDao.savePlayer(newPlayer)
    val playerPicksToSave = PlayerPicks(newSavedPlayer.id, playerPicks)
    playerPicksDao.savePlayerPicks(playerPicksToSave)

    writer.println("Your picks have been submitted!")

    val dispatcher: RequestDispatcher = getServletContext.getRequestDispatcher("/picksSubmitted.jsp")
    dispatcher.forward(req, res)
  }

  //    BestPicture.0 -> 4
  //    BestPicture.1 -> 5
  //    BestPicture.2-> 2
  //    Actor.0 -> 4
  //    Actor.1-> 2
  //    Actor.2-> 1
  //    <catName>.<pickRank> -> <nomIndex>
  private def getPicksFromRequest(requestParams: Map[String, Array[String]]): Seq[CategoryPicks] = {

    def getPickRankFromKeySplit(keySplit: Array[String]) = {
      if (keySplit.length == 2) {
        getNomIndexFromValue(keySplit(1)).toOption
      } else None
    }

    def getNomIndexFromValue(value: String) = {
      Try {
        Integer.parseInt(value)
      }
    }

    //only need the first value in the multiMap
    val requestParam1stVal: Map[String, String] = requestParams.map { case (key, values) =>
      key -> values.head
    }

    //filter to only the categories, and map to all the details
    val categories = CategoryName.values

    val rankedPicks = requestParam1stVal.flatMap { case (key, value) =>
      val keySplit = key.split('.')
      val pickCategory = keySplit(0)

      val maybeMatchingCategory = categories.find(_.toString == pickCategory)
      val maybePickRank = getPickRankFromKeySplit(keySplit)
      val maybeNomIndex = getNomIndexFromValue(value)

      val maybeRankedPick: Option[RankedPick] = (maybeMatchingCategory, maybePickRank, maybeNomIndex) match {
        case (Some(category), Some(pickRank), Success(nomIndex)) =>
          Some(RankedPick(category, pickRank, nomIndex))
        case _ if key == "userName" =>
          None
        case _ =>
          println(s"Request param was not understood: $key -> $value")
          None
      }
      maybeRankedPick
    }.toSeq

    //group the ranked picks by their category
    val rankedPickByCategory: Map[CategoryName.Value, Seq[RankedPick]] =
      rankedPicks.groupBy(rankedPick => rankedPick.category)

    rankedPickByCategory.map { case (category, rankedCatPicks) =>
      val maybePick1Nom = rankedCatPicks.find(_.pickRank == 0)
      val maybePick2Nom = rankedCatPicks.find(_.pickRank == 1)
      val maybePick3Nom = rankedCatPicks.find(_.pickRank == 2)

      (maybePick1Nom, maybePick2Nom, maybePick3Nom) match {
        case (Some(nom1), Some(nom2), Some(nom3)) =>
          CategoryPicks(category, nom1.nomIndex, nom2.nomIndex, nom3.nomIndex)
        case _ =>
          throw new ServletException(s"Could not find all nominations for category $category")
      }
    }.toSeq
  }

  private def findPlayerFromRequest(requestParams: Map[String, Array[String]]): String = {
    val newPlayerEntry = requestParams.iterator.find { isPlayerNameParam }
    newPlayerEntry match {
      case Some((key, values)) =>
        //the first value is the player name, ignore anything else
        values.head
      case _ => throw new ServletException("No player name added")
    }
  }

  private def isPlayerNameParam(paramEntry: (String, Array[String])): Boolean = {
    paramEntry match {
      case (key, _) => key == "userName"
      case _ => false
    }
  }
}

object PicksSubmittedServlet {
  private case class RankedPick(category: CategoryName.Value, pickRank: Int, nomIndex: Int)

}