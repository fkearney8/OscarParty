package com.oscarparty.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.oscarparty.servlets.WinnerPickerServlet
import org.junit.Assert._
import org.junit.Test

class JsonUtilTest {
  private val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  val jsonUtil = new JsonUtil(mapper)

  @Test
  def testSerializationMap(): Unit = {
    val originalMap = Map("a" -> List(1,2), "b" -> List(3,4,5), "c" -> List())
    val json = jsonUtil.toJson(originalMap)
    println(s"Json serialized to $json")
    assertEquals("{\"a\":[1,2],\"b\":[3,4,5],\"c\":[]}", json)

    val map = jsonUtil.toMap[Seq[Int]](json)
    assertEquals(4, map("b")(1))
  }

  @Test
  def testSerializationCaseClass(): Unit = {
    val testThing = StringAndInt("Five", 5)
    val json = jsonUtil.toJson(testThing)
    println(s"Json serialized to $json")
    assertEquals("{\"string\":\"Five\",\"int\":5}", json)

    val deserializedTestThing = jsonUtil.fromJson[StringAndInt](json)
    assertEquals("Five", deserializedTestThing.string)
    assertEquals(5, deserializedTestThing.int)
  }

  @Test
  def testSerializationSequence(): Unit = {
    val testSeq = Seq(5, 4, 6)
    val json = jsonUtil.toJson(testSeq)
    println(s"Json serialized to $json")
    assertEquals("[5,4,6]", json)
  }

  @Test
  def testSerializationCategoriesAndNominees(): Unit = {
    val json = jsonUtil.toJson(WinnerPickerServlet.constructCatsToNomineesSelectionMap)
    println(s"Json serialized to $json")
  }
}

case class StringAndInt(string: String, int: Int)
