import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

import scala.util.matching.Regex

object Day3 extends Exercise(2024, 3) {

  def run(input: List[String]): Unit = {
    val inputData = prepInput(input)
    part1(calculateMultiplications(inputData))
  }

  private def prepInput(input: List[String]): String = input.mkString

  private def calculateMultiplications(str: String): Int = {
    val regex: Regex = "mul\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)".r

    var totalSum = 0
    for (case regex(x, y) <- regex.findAllIn(str)) {
      totalSum += x.toInt * y.toInt
    }
    totalSum
  }



}