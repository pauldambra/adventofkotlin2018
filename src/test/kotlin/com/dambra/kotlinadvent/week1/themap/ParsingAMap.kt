package com.dambra.kotlinadvent.week1.themap

import com.dambra.kotlinadvent.week1.AdventMap
import com.dambra.kotlinadvent.week1.Coordinate
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

        assertThat(map.startCoordinate).isEqualTo(Coordinate(1, 0))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(0, 1))
    }

    @Test
    fun `can parse a mirrored 2x2 map`() {
        val map = AdventMap.parseFrom("""S.
            .X
        """)

        assertThat(map.width).isEqualTo(2)
        assertThat(map.height).isEqualTo(2)

        assertThat(map.startCoordinate).isEqualTo(Coordinate(0, 0))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(1, 1))
    }

    @Test
    fun `can parse a map that originally it could not`() {
        val map = AdventMap.parseFrom("""
                                .,S,.,.
                                .,X,.,.""")

        assertThat(map.width).isEqualTo(4)
        assertThat(map.height).isEqualTo(2)

        assertThat(map.startCoordinate).isEqualTo(Coordinate(1, 0))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(1, 1))
    }

    @Test
    fun `can parse blockages out of the input`() {
        val map = AdventMap.parseFrom("""
                                .,S,B,.
                                .,X,B,.""")

        assertThat(map.width).isEqualTo(4)
        assertThat(map.height).isEqualTo(2)

        assertThat(map.startCoordinate).isEqualTo(Coordinate(1, 0))
        assertThat(map.finishCoordinate).isEqualTo(Coordinate(1, 1))

        assertThat(map.blockages).isEqualTo(setOf(Coordinate(2, 0),Coordinate(2, 1)))
    }

}

