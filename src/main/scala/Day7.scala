import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

import scala.annotation.tailrec

object Day7 extends Exercise(2024, 7) {
  def run(input: List[String]): Unit = {

    val inputData = prepareData(input)
//    part1(calculateTotalCalibrationResult(inputData, List("+", "*")))
    part2(calculateTotalCalibrationResult(inputData, List("+", "*", "|")))
  }

  private def prepareData(input: List[String]): List[(Long, List[Long])] = {
    input.map({ row =>
      val splitInput = row.split(":").map(_.trim)
      val result = splitInput.head
      (result.toLong, splitInput(1).split(" ").map(_.toLong).toList)
    })
  }

  private def calculateEquation(list: List[Long], operators: String): Long = {
    var result = list.head

    for (i <- operators.indices) {
      operators(i) match {
        case '+' => result += list(i + 1)
        case '*' => result *= list(i + 1)
        case '|' => result = (result.toString + list(i + 1).toString).toLong
      }
    }
    result
  }

  private def checkEquation(result: Long, list: List[Long], operators: List[String]): Boolean = {
    val n = list.length - 1
    val totalCombinations = Math.pow(operators.size, n).toInt

    for (i <- 0 until totalCombinations) {
      var currentOperators = ""

      var temp = i
      for (j <- 0 until n) {
        val operatorIndex = temp % operators.size
        currentOperators += operators(operatorIndex)
        temp = temp / operators.size
      }

      if (calculateEquation(list, currentOperators) == result) {
        return true
      }
    }

    false
  }

  private def calculateTotalCalibrationResult(input: List[(Long, List[Long])], operators: List[String]): Long = {
    input.filter(equation => checkEquation(equation._1, equation._2, operators)).map(equation => equation._1).sum
  }
}