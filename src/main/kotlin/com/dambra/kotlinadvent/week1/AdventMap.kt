package com.dambra.kotlinadvent.week1

data class AdventMap(
        val width: Int,
        val height: Int,
        val startCoordinate: Coordinate,
        val finishCoordinate: Coordinate,
        val blockages: Set<Coordinate> = emptySet()
) {

    fun generateSurroundingCoordinates(x: Coordinate): List<Coordinate> {
        val cs = x.generateSurroundingCoordinates()
                .filter { it.x in 0..(width - 1) }
                .filter { it.y in 0..(height - 1) }
                .filter { !blockages.contains(it) }
        println("generating sourrounds $cs")
        return cs
    }

    fun printAt(x: Int, y: Int) =
            if (blockages.contains(Coordinate(x, y))) "B" else "."

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

            val blockages = findCoordinatesOf(rows, 'B')

            return AdventMap(rowLength, rows.size, startCoordinate, finishCoordinate, blockages)
        }

        private fun findFinishCoordinate(rows: List<String>) = findCoordinateOf(rows, 'X')

        private fun findStartCoordinate(rows: List<String>) = findCoordinateOf(rows, 'S')

        private fun findCoordinateOf(rows: List<String>, target: Char): Coordinate {
            val y = rows.indexOfFirst { it.contains(target) }
            val x = rows[y].filter { it != ',' }.indexOf(target)
            return Coordinate(x, y)
        }

        private fun findCoordinatesOf(rows: List<String>, target: Char) =
                rows.foldIndexed(emptySet()) { yIndex: Int, outerSet: Set<Coordinate>, r: String ->
                    outerSet + r.foldIndexed(emptySet<Coordinate>()) { xIndex, innerSet, c ->
                        if (c == target) {
                            innerSet + Coordinate(xIndex, yIndex)
                        } else {
                            innerSet
                        }
                    }
                }
    }
}