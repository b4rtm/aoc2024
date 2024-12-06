import Day6.Direction.Up
import com.pg.bigdata.neighborhoodanalytics.aoc.imperative.Exercise

import scala.annotation.tailrec

object Day6 extends Exercise(2024, 6) {
  def run(input: List[String]): Unit = {

    part1(calculateDistinctPositions(input))
    part2(calculatePossibleObstructions(input))
  }

  enum Direction {
    case Up, Down, Left, Right
  }

  private def findGuard(map: List[String]): Option[(Int, Int)] = {
    map.zipWithIndex.collectFirst {
      case (row, rowIndex) if row.contains('^') =>
        (rowIndex, row.indexOf('^'))
    }
  }

  private def isItExit(coordinates: (Int, Int), map: List[String]): Boolean = {
    val (row, col) = coordinates
    val maxRowIndex = map.length - 1
    val maxColumnIndex = map.head.length - 1
    if ((row == 0 || row == maxRowIndex || col == maxColumnIndex || col == 0) && map(row)(col) != '#') true
    else false
  }

  @tailrec
  private def moveGuard(guardPosition: (Int, Int), map: List[String], direction: Direction): ((Int, Int), List[String], Direction) = {
    val (x, y) = guardPosition

    val (newX, newY) = direction match {
      case Direction.Up => (x - 1, y)
      case Direction.Down => (x + 1, y)
      case Direction.Left => (x, y - 1)
      case Direction.Right => (x, y + 1)
    }

    if (map(newX)(newY) != '#') {
      val updatedMap = map.updated(newX, map(newX).updated(newY, '^'))
      ((newX, newY), updatedMap, direction)
    }
    else {
      val newDirection = direction match {
        case Direction.Up => Direction.Right
        case Direction.Right => Direction.Down
        case Direction.Down => Direction.Left
        case Direction.Left => Direction.Up
      }
      moveGuard(guardPosition, map, newDirection)
    }
  }

  @tailrec
  private def findExit(map: List[String], guardPosition: (Int, Int), direction: Direction): List[String] = {
    val (newGuardPosition, updatedMap, newDirection) = moveGuard(guardPosition, map, direction)
    if (isItExit(newGuardPosition, updatedMap)) updatedMap
    else findExit(updatedMap, newGuardPosition, newDirection)
  }

  private def calculateDistinctPositions(map: List[String]): Int = {
    val guardPosition = findGuard(map).getOrElse(throw new NoSuchElementException("There is no guard"))
    val finalMap = findExit(map, guardPosition, Up)
    finalMap.flatMap(row => row.filter(_ == '^')).length
  }

  @tailrec
  private def checkGuardPath(guardPosition: (Int, Int), direction: Direction, map: List[String], visited: Set[((Int, Int), Direction)]): Boolean = {
    if(visited.contains(guardPosition, direction)) true
    else{
      val (newGuardPosition, updatedMap, newDirection) = moveGuard(guardPosition, map, direction)
      if (isItExit(newGuardPosition, updatedMap)) false
      else {
        val newVisited = visited + ((guardPosition, direction))
        checkGuardPath(newGuardPosition, newDirection, updatedMap, newVisited)
      }
    }
  }

  private def isGuardStuck(map: List[String], guardPosition: (Int, Int), obstacle: (Int, Int)): Boolean = {
    val (obstacleRow, obstacleCol) = obstacle
    val updatedMap = map.updated(obstacleRow, map(obstacleRow).updated(obstacleCol, '#'))
    checkGuardPath(guardPosition, Up, updatedMap, Set())
  }

    private def calculatePossibleObstructions(map: List[String]): Int = {
    val guardPosition = findGuard(map).getOrElse(throw new NoSuchElementException("There is no guard"))

    val allPossibleObstructions = for {
      row <- map.indices
      col <- map(row).indices
      if map(row)(col) == '.'
      if isGuardStuck(map, guardPosition, (row, col))
    } yield (row, col)

    allPossibleObstructions.length
  }

}