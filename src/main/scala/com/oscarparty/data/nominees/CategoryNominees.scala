package com.oscarparty.data.nominees

case class CategoryNominees(categoryName: CategoryName.Value, nominees: Seq[Nominee]) {
  /** Get the nominee at the given index. */
  def nominee(index: Int): Nominee = {
    nominees(index)
  }
}

case class Nominee(name: String, index: Int)
