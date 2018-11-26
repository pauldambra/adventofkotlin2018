package com.dambra.kotlinadvent.week1

data class Coordinate(val x: Int, val y: Int) {
    fun generateSurroundingCoordinates(): List<Coordinate> {
        return listOf(
                Coordinate(x-1, y),
                Coordinate(x-1, y-1),
                Coordinate(x, y-1),
                Coordinate(x+1, y-1),
                Coordinate(x+1, y),
                Coordinate(x+1, y+1),
                Coordinate(x, y+1),
                Coordinate(x-1, y+1)
        )
    }
}