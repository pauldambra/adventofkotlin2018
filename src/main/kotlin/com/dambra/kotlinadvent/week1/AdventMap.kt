package com.dambra.kotlinadvent.week1

data class AdventMap(val width: Int, val height: Int, val startCoordinate: Coordinate, val finishCoordinate: Coordinate) {
    fun generateSurroundingCoordinates(x: Coordinate): List<Coordinate> {
        return x.generateSurroundingCoordinates()
                .filter { it.x in 0..(width - 1) }
                .filter { it.y in 0..(height - 1) }
    }

    companion object {
        fun parseFrom(s: String): AdventMap {
            val rows = s
                    .split("\n")
                    .map { it.trim() }
                    .map { it.filter { c -> c != ',' } }
                    .filter(String::isNotBlank)

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
            val y = rows.indexOfFirst { it.contains(target) }
            val x = rows[y].filter { it != ',' }.indexOf(target)
            return Coordinate(x, y)
        }
    }
}