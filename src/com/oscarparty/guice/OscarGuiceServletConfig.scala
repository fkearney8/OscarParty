package com.oscarparty.guice

import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.Guice
import com.google.inject.servlet.ServletModule
import com.oscarparty.servlets.PicksSubmittedServlet


class OscarGuiceServletConfig extends GuiceServletContextListener {
  override def getInjector: Injector = Guice.createInjector(new StockRegretServletModule)
}

class StockRegretServletModule extends ServletModule {
  override def configureServlets(): Unit = {
//    serve("/hw").`with`(classOf[OscarHelloWorldServlet])
    serve("submitPicks").`with`(classOf[PicksSubmittedServlet])
  }
}
