package com.oscarparty.servlets

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.oscarparty.servlets.playerpicks.PlayerPicksDAO
import scala.collection.JavaConversions._

class PaidPlayersServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    //get all the players
    val playerPicks = new PlayerPicksDAO().readAllPlayerPicks()
    req.setAttribute("playerNames", playerPicks)

    req.getServletContext.getRequestDispatcher("/paidPlayers.jsp").forward(req, resp)
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val paramMap = req.getParameterMap
    val picksDao = new PlayerPicksDAO()
    val playerPicks = new PlayerPicksDAO().readAllPlayerPicks()
    for (eachPickSet <- playerPicks) {
      val userName = eachPickSet.userName
      if (paramMap.containsKey(userName)) {
        picksDao.updatePaid(userName, paid = true)
      } else {
        picksDao.updatePaid(userName, paid = false)
      }
    }
    resp.getWriter.write("Payers Saved")
  }
}
