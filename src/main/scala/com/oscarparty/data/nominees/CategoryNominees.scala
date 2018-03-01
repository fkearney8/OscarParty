package com.oscarparty.data.nominees

import java.util

import collection.JavaConverters._

case class CategoryNominees(categoryName: CategoryName.Value, nominees: Seq[Nominee]) {
  /** Get the nominee at the given index. */
  def nominee(index: Int): Nominee = {
    nominees(index)
  }

  def nomineesJava: util.LinkedList[Nominee] = {
    //linked list so that it can be used as a bean (beans can't be interfaces
    // If not for this restriction, we could unwrap the asJava list.
    new util.LinkedList(nominees.asJava)
  }
}

case class Nominee(name: String, index: Int)
