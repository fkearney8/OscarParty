package com.oscarparty.data.nominees

case class CategoryNominees(categoryName: CategoryName.Value, nominees: Seq[Nominee]) {
  def nominee(id: Int): Nominee = {
    nominees(id)
  }
}

case class Nominee(name: String, id: Int)
