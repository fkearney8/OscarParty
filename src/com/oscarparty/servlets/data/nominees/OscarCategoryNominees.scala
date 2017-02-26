package com.oscarparty.servlets.data.nominees

case class OscarCategoryNominees(categoryName: String, nominees: Array[String], points: Array[Int] = Array(3, 2, 1))

