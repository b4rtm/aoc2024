import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

import scala.annotation.tailrec

object Day7 extends Exercise(2024, 7) {
  def run(input: List[String]): Unit = {

    val inputData = prepareData(input)
    part1(calculateTotalCalibrationResult(inputData))
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
      }
    }
    result
  }

  private def checkEquation(result: Long, list: List[Long]): Boolean = {
    val listOfOperators = generateCombinations(list.length - 1)

    @tailrec
    def checkEquationHelper(remaining: Int): Boolean = {
      if(remaining == 0) false
      else {
        val operators = listOfOperators(remaining - 1)

        if (calculateEquation(list, operators) == result) true
        else checkEquationHelper(remaining - 1)
      }
    }
    checkEquationHelper(listOfOperators.length)
  }

  private def generateCombinations(n: Int): List[String] = {
    var results = List[String]()

    for (i <- 0 until Math.pow(2, n).toInt) {
      var current = ""
      var temp = i

      for (j <- 0 until n) {
        if (temp % 2 == 0) {
          current += "+"
        } else {
          current += "*"
        }
        temp = temp / 2
      }
      results = results :+ current
    }
    results
  }

  private def calculateTotalCalibrationResult(input: List[(Long, List[Long])]): Long = {
    input.filter(equation => checkEquation(equation._1, equation._2)).map(equation => equation._1).sum
  }
}