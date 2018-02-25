package com.oscarparty.guice

import com.google.inject.{Guice, Injector}
import com.google.inject.servlet.{GuiceServletContextListener, ServletModule}
import com.oscarparty.servlets.HelloWorldServlet


class OscarGuiceServletConfig extends GuiceServletContextListener {
  override def getInjector: Injector = Guice.createInjector(new StockRegretServletModule)
}

class StockRegretServletModule extends ServletModule {
  override def configureServlets(): Unit = {
    install(new DataConfig)

    serve("/hw").`with`(classOf[HelloWorldServlet])
//    serve("submitPicks").`with`(classOf[PicksSubmittedServlet])
  }
}
