package com.oscarparty.guice

import java.util.concurrent.{ScheduledExecutorService, ScheduledThreadPoolExecutor}

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
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

    bind(classOf[ScheduledExecutorService]).toInstance(new ScheduledThreadPoolExecutor(3))

    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
    bind(classOf[ObjectMapper]).toInstance(mapper)

    serve("/hw*").`with`(classOf[HelloWorldServlet])
    serve("/selection")`with`(classOf[NewPlayerSelectionsServlet])
    serve("/submitPicks.do").`with`(classOf[PicksSubmittedServlet])
    serve("/leaderboard").`with`(classOf[LeaderboardServlet])
    serve("/playerPicks").`with`(classOf[PlayerPicksReviewServlet])
    serve("/winnerPicker").`with`(classOf[WinnerPickerServlet])
    serve("/winnersPicked.do").`with`(classOf[WinnerPickedServlet])
  }
}
