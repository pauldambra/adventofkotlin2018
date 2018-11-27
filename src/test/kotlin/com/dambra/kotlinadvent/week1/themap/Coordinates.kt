package com.dambra.kotlinadvent.week1.themap

import com.dambra.kotlinadvent.week1.AdventMap
import com.dambra.kotlinadvent.week1.Coordinate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class Coordinates {
    @Test
    fun `can generate their surrounds`() {
        val x = Coordinate(3,3)
        val surrounds = x.generateSurroundingCoordinates()

        /**
         *    b,c,d
         *    a,x,e
         *    h,g,f
         *
         */
        val expectedSurrounds = listOf(
                Coordinate(2, 3),
                Coordinate(2, 2),
                Coordinate(3, 2),
                Coordinate(4, 2),
                Coordinate(4, 3),
                Coordinate(4, 4),
                Coordinate(3, 4),
                Coordinate(2, 4)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter the surrounds for out of bound coordinates`() {
        val map = AdventMap.parseFrom("""
                                .,S,.,.
                                .,X,.,.""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        /**
         * from
         *
         *   .,S,.,.
         *   .,X,.,.
         *
         * surrounds of S are (! is off map)
         *
         *    !,!,!,
         *    a,S,e,
         *    h,g,f
         *
         * where S is 1, 0
         *
         */
        val expectedSurrounds = listOf(
                Coordinate(0, 0),
                Coordinate(2, 0),
                Coordinate(2, 1),
                Coordinate(1, 1),
                Coordinate(0, 1)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter the surrounds when at the top right hand corner`() {
        val map = AdventMap.parseFrom("""
                                .,.,.,S
                                .,X,.,.""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        val expectedSurrounds = listOf(
                Coordinate(2, 0),
                Coordinate(3, 1),
                Coordinate(2, 1)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter the surrounds when at the bottom right hand corner`() {
        val map = AdventMap.parseFrom("""
                                .,.,.,.
                                .,X,.,S""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        val expectedSurrounds = listOf(
                Coordinate(2, 1),
                Coordinate(2, 0),
                Coordinate(3, 0)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter the surrounds when at the bottom left hand corner`() {
        val map = AdventMap.parseFrom("""
                                .,.,.,.
                                S,X,.,.""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        val expectedSurrounds = listOf(
                Coordinate(0, 0),
                Coordinate(1, 0),
                Coordinate(1, 1)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter the surrounds when at the top left hand corner`() {
        val map = AdventMap.parseFrom("""
                                S,.,.,.
                                .,X,.,.""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        val expectedSurrounds = listOf(
                Coordinate(1, 0),
                Coordinate(1, 1),
                Coordinate(0, 1)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }

    @Test
    fun `maps can filter blockages`() {
        val map = AdventMap.parseFrom("""
                                S,.,.,.
                                B,X,.,.""")

        val surrounds = map.generateSurroundingCoordinates(map.startCoordinate)

        val expectedSurrounds = listOf(
                Coordinate(1, 0),
                Coordinate(1, 1)
        )

        assertThat(surrounds).isEqualTo(expectedSurrounds)
    }
}