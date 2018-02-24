package com.oscarparty.servlets.data.nominees

object CategoryNominees {
  def apply(categoryName: CategoryName.Value, nomineeNames: Seq[String]): CategoryNominees = {
    val nomNameWithIndex: Seq[(String, Int)] = nomineeNames.zipWithIndex
    new CategoryNominees(categoryName,
      nomNameWithIndex.map {
        case (nomName, index) => new Nominee(nomName, index)
      })
  }
}

case class CategoryNominees(categoryName: CategoryName.Value, nominees: Seq[Nominee]) {
  def nominee(id: Int): Nominee = {
    nominees(id)
  }
}

case class Nominee(name: String, id: Int) {

}

