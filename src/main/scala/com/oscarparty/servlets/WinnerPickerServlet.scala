//package com.oscarparty.servlets
//
//import javax.servlet.http.HttpServlet
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//import com.oscarparty.data.nominees.OscarNomineesDAO._
//
//import scala.util.parsing.json.{JSONArray, JSONObject}
//import com.oscarparty.data.NextCategory
//
//@Singleton
//class WinnerPickerServlet extends HttpServlet {
//  protected override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
//
//    resp.setContentType("text/html; charset=UTF-8")
//    resp.setCharacterEncoding("UTF-8")
//
//    val catsToNomsJsonArrays: Map[String, JSONArray] = categoriesWithoutWinners.map { category =>
//      val nomineesJson: List[JSONObject] = new JSONObject(Map("id" -> -1, "name" -> "None")) +: {
//        category.nominees.map { nominee =>
//          new JSONObject(Map("id" -> nominee.id, "name" -> nominee.name))
//        }
//      }
//      category.id.toString -> new JSONArray(nomineesJson)
//    }.toMap
//
//    val catsToNomsWithNoneOption: Map[String, JSONArray] = catsToNomsJsonArrays + ("None" -> new JSONArray(List("None")))
//    val catsToNomsJson = new JSONObject(catsToNomsWithNoneOption).toString()
//
//    val categoriesWithoutWinnerJson = new JSONArray(
//      new JSONObject(Map("name" -> "None", "id" -> -1)) +: {
//        categoriesWithoutWinners.map { category =>
//          new JSONObject(Map("name" -> category.name, "id" -> category.id))
//        }
//      }
//    )
//
//    req.setAttribute("categoriesWithoutWinners", categoriesWithoutWinnerJson.toString())
//    req.setAttribute("catsToNomsMap", catsToNomsJson)
//    req.setAttribute("nextCategory", NextCategory.nextCategory)
//
//    req.getServletContext.getRequestDispatcher("/winnerPicker.jsp").forward(req, resp)
//  }
//}