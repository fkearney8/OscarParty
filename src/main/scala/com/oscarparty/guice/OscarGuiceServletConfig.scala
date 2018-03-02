package com.oscarparty.guice

import com.google.inject.{Guice, Injector, Stage}
import com.google.inject.servlet.{GuiceServletContextListener, ServletModule}
import com.oscarparty.servlets._


class OscarGuiceServletConfig extends GuiceServletContextListener {
  val stage: Stage = System.getenv("STAGE") match {
    case "production" => Stage.PRODUCTION
    case _ => Stage.DEVELOPMENT
  }
  override def getInjector: Injector = Guice.createInjector(Stage.DEVELOPMENT, new OscarServletModule)
}

class OscarServletModule extends ServletModule {
  override def configureServlets(): Unit = {

    install(new DataModule)

    serve("/hw*").`with`(classOf[HelloWorldServlet])
    serve("/selection")`with`(classOf[NewPlayerSelectionsServlet])
    serve("/submitPicks.do").`with`(classOf[PicksSubmittedServlet])
    serve("/leaderboard").`with`(classOf[LeaderboardServlet])
    serve("/playerPicks").`with`(classOf[PlayerPicksReviewServlet])
  }
}
