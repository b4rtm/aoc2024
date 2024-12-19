import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

object Day8 extends Exercise(2024, 8) {


  def run(input: List[String]): Unit = {
    part1(calculateAntinodes(input))
  }

  private def createAntennasMap(inputMap: List[String]): Map[Char, List[(Int, Int)]] = {
    var  antennasMap: Map[Char, List[(Int, Int)]] = Map.empty.withDefaultValue(List.empty)

    for (row <- inputMap.indices; col <- inputMap(row).indices) {
      val char = inputMap(row)(col)

      if (char.isLetter || char.isDigit) {
        antennasMap = antennasMap.updated(char, (row, col) :: antennasMap(char))
      }
    }

    antennasMap
  }

  private def createAntinodes(inputMap: List[String]): List[String] = {
    var antinodesMatrix = List.fill(inputMap.size)("." * inputMap.head.length)

    val antennasMap = createAntennasMap(inputMap)

    for ((_, coords) <- antennasMap) {
      for (i <- coords.indices; j <- i + 1 until coords.length) {
        val (row1, col1) = coords(i)
        val (row2, col2) = coords(j)

        val distanceBetweenRows = Math.abs(row1 - row2)
        val distanceBetweenCols = Math.abs(col1 - col2)

        val antinodeRow1 = if (row1 < row2) row1 - distanceBetweenRows else row1 + distanceBetweenRows
        val antinodeRow2 = if (row1 < row2) row2 + distanceBetweenRows else row2 - distanceBetweenRows

        val antinodeCol1 = if (col1 < col2) col1 - distanceBetweenCols else col1 + distanceBetweenCols
        val antinodeCol2 = if (col1 < col2) col2 + distanceBetweenCols else col2 - distanceBetweenCols

        if (antinodeCol1 >= 0 && antinodeCol1 < antinodesMatrix.head.length && antinodeRow1 >= 0 && antinodeRow1 < antinodesMatrix.length) {
          antinodesMatrix = antinodesMatrix.updated(antinodeRow1, antinodesMatrix(antinodeRow1).updated(antinodeCol1, '#'))
        }

        if (antinodeCol2 >= 0 && antinodeCol2 < antinodesMatrix.head.length && antinodeRow2 >= 0 && antinodeRow2 < antinodesMatrix.length) {
          antinodesMatrix = antinodesMatrix.updated(antinodeRow2, antinodesMatrix(antinodeRow2).updated(antinodeCol2, '#'))
        }
      }
    }
    antinodesMatrix
  }

  private def calculateAntinodes(input: List[String]): Int = {
    createAntinodes(input).map(row => row.count(char => char == '#')).sum
  }
}
