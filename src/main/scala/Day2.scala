import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

import scala.annotation.tailrec
import scala.math.abs

object Day2 extends Exercise(2024,2){

  def run(input: List[String]): Unit = {
    val inputData = prepInput(input)
    part1(calculateSafeReports(inputData))
  }

  private def prepInput(input: List[String]): List[List[Int]] = input.map(_.split(" ").map(_.toInt).toList)

  private def calculateSafeReports(reports: List[List[Int]]): Int = {
    reports.count(report => isReportSafe(report))
  }

  private def isReportSafe(levels: List[Int]): Boolean = {
    @tailrec
    def safetyHelper(counter: Int, isIncreasing: Boolean): Boolean = {
      if(counter >= levels.length - 1) true
      else{
        val n1 = levels(counter)
        val n2 = levels(counter + 1)
        if ((isIncreasing && n1 >= n2) || (!isIncreasing && n1 <= n2)) false
        else if (abs(n1 - n2) > 3) false
        else safetyHelper(counter + 1, isIncreasing)
      }
    }
    safetyHelper(0, levels.head < levels(1))

  }
}
