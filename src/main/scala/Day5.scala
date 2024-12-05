import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

object Day5 extends Exercise(2024, 5){
  def run(input: List[String]): Unit = {
    val (pairs, updates) = prepInput(input)
    part1(calculateSumOfMiddleNumbersInCorrectUpdates(pairs, updates))
    part2(calculateSumOfMiddleNumbersInIncorrectUpdates(pairs, updates))
  }

  private def prepInput(input: List[String]): (List[List[Int]], List[List[Int]]) = {
    val pairs = input.take(input.indexOf(""))
    val splitPairs = pairs.map(_.split("\\|").map(_.toInt).toList)

    val updates = input.drop(input.indexOf("")+1)
    val splitUpdates = updates.map(_.split(",").map(_.toInt).toList)
    (splitPairs, splitUpdates)
  }

  private def convertPairsToMap(pairs: List[List[Int]]): Map[Int, List[Int]] = {
    pairs.foldLeft(Map.empty[Int, List[Int]]) {
      (acc, pair) =>
        val key = pair.head
        val value = pair.tail.head
        acc.updated(key, acc.getOrElse(key, List.empty) :+ value)
    }
  }

  private def isOrderCorrectForIndexOfNumber(index: Int, update: List[Int], rulesMap: Map[Int, List[Int]]): Boolean = {
    val currentNumber = update(index)
    val isOrderBeforeNumberCorrect = update.take(index).forall(n => rulesMap.get(n).exists(_.contains(currentNumber)))
    val isOrderAfterNumberCorrect = update.drop(index + 1).forall(n => rulesMap.get(currentNumber).exists(_.contains(n)))
    isOrderBeforeNumberCorrect && isOrderAfterNumberCorrect
  }

  private def checkUpdate(update: List[Int], rulesMap: Map[Int, List[Int]]): Boolean = {
    update.indices.forall(index => isOrderCorrectForIndexOfNumber(index, update, rulesMap))
  }

  private def getMiddleNumber(update: List[Int]): Int = {
    update(update.length/2)
  }

  private def calculateSumOfMiddleNumbersInCorrectUpdates(pairs: List[List[Int]], updates: List[List[Int]]): Int = {
    val rulesMap: Map[Int, List[Int]] = convertPairsToMap(pairs)
    val correctUpdates = updates.filter(update => checkUpdate(update, rulesMap))
    correctUpdates.map(getMiddleNumber).sum
  }

  private def calculateSumOfMiddleNumbersInIncorrectUpdates(pairs: List[List[Int]], updates: List[List[Int]]): Int = {
    val rulesMap: Map[Int, List[Int]] = convertPairsToMap(pairs)
    val incorrectUpdates = updates.filterNot(update => checkUpdate(update, rulesMap))
    val orderedIncorrectUpdates = incorrectUpdates.map(orderUpdate(_, rulesMap))
    orderedIncorrectUpdates.map(getMiddleNumber).sum
  }

  private def orderUpdate(update: List[Int], rulesMap: Map[Int, List[Int]]): List[Int] = {
    val ordered = update.sortWith((a, b) => {
      rulesMap.get(a).exists(_.contains(b))
    })
    ordered
  }

}
