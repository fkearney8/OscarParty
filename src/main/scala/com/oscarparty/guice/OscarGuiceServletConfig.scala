package com.oscarparty.guice

import com.google.inject.{Guice, Injector}
import com.google.inject.servlet.{GuiceServletContextListener, ServletModule}
import com.oscarparty.servlets.{HelloWorldServlet, NewPlayerSelectionsServlet}


class OscarGuiceServletConfig extends GuiceServletContextListener {
  override def getInjector: Injector = Guice.createInjector(new OscarServletModule)
}

class OscarServletModule extends ServletModule {
  override def configureServlets(): Unit = {
    install(new DataConfig)

    serve("/hw*").`with`(classOf[HelloWorldServlet])
    serve("/selection")`with`(classOf[NewPlayerSelectionsServlet])
//    serve("submitPicks").`with`(classOf[PicksSubmittedServlet])
  }
}
