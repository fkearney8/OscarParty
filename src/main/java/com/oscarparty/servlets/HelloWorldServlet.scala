package com.oscarparty.servlets

import javax.inject.{Inject, Singleton}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

@Singleton
class HelloWorldServlet @Inject() extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.getWriter.println("Hello world! I can really be updated!")
  }

}
