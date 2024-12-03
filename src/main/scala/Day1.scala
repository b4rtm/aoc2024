import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise
import scala.math.abs

object Day1 extends Exercise(2024, 1) {
  def run(input: List[String]): Unit = {
    val inputData = prepInput(input)
    part1(calculateDistance(inputData))
    part2(calculateSimilarity(inputData))
  }

  private def prepInput(input: List[String]): (List[Int], List[Int]) = input.map(_.split(" {3}").map(_.toInt)).map(arr => (arr(0), arr(1))).unzip()

  private def calculateDistance(data: (List[Int], List[Int])): Int = {
    data._1.sorted.zip(data._2.sorted).map((a,b) => abs(a-b)).sum
  }

  private def calculateSimilarity(data: (List[Int], List[Int])): Int = {
    data._1.map(number => number * countNumberOfAppearances(number, data._2)).sum
  }

  private def countNumberOfAppearances(num: Int, list: List[Int]): Int = {
    list.count(_ == num)
  }

}