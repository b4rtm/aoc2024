package project

import sbt.*

object Dependencies {
  object Versions {
    val scala    = "3.5.1"
    val aoc = "2.0.0"
  }

  object Libraries {
    val aoc = "com.pg.bigdata" %% "da-ap-pda-nas-aoc-scala" % Versions.aoc
  }
}