package com.dambra.kotlinadvent.themap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ParsingAMap {
    @Test
    fun `can parse a 2x2 map`() {
        val map = AdventMap.parseFrom(""".S
            X.
        """)

        assertThat(map.width).isEqualTo(2)
        assertThat(map.height).isEqualTo(2)

        assertThat(map.startCoordinate).isEqualTo(Coordinate(0,1))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(1,0))
    }

    @Test
    fun `can parse a mirrored 2x2 map`() {
        val map = AdventMap.parseFrom("""S.
            .X
        """)

        assertThat(map.width).isEqualTo(2)
        assertThat(map.height).isEqualTo(2)

        assertThat(map.startCoordinate).isEqualTo(Coordinate(0,0))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(1,1))
    }

}

class AdventMap(val width: Int, val height: Int, val startCoordinate: Coordinate, val finishCoordinate: Coordinate) {
    companion object {
        fun parseFrom(s: String): AdventMap {
            val rows = s.split("\n").map { it.trim() }.filter(String::isNotBlank)

            val rowLength = rows.firstOrNull()?.length ?: throw Exception("there are no rows?!")
            if (!rows.all { it.length == rowLength }) {
                throw Exception("not all rows are ${rowLength}long")
            }

            val startCoordinate = findStartCoordinate(rows)

            val finishCoordinate = findFinishCoordinate(rows)

            return AdventMap(rowLength, rows.size, startCoordinate, finishCoordinate)
        }

        private fun findFinishCoordinate(rows: List<String>) = findCoordinateOf(rows, 'X')

        private fun findStartCoordinate(rows: List<String>) = findCoordinateOf(rows, 'S')

        private fun findCoordinateOf(rows: List<String>, target: Char): Coordinate {
            val startRow = rows.indexOfFirst { it.contains(target) }
            val startColumn = rows[startRow].indexOf(target)
            return Coordinate(startRow, startColumn)
        }
    }
}

data class Coordinate(val x: Int, val y: Int)
