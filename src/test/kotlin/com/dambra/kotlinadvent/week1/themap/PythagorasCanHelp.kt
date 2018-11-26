package com.dambra.kotlinadvent.week1.themap

import com.dambra.kotlinadvent.week1.AdventMap
import com.dambra.kotlinadvent.week1.distanceBetween
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset
import org.junit.jupiter.api.Test

internal class PythagorasCanHelp {
    @Test
    fun `find the shortest distance between start and finish coordinate`() {
        val map = AdventMap.parseFrom(""".S
            ..
            X.
        """)

        val distance = distanceBetween(map.startCoordinate, map.finishCoordinate)

        /**
         * start coordinate will be 0,1
         * end coordinate wil be 1, 2
         *
         *  .S
         *  ..
         *  X.
         *
         *  so one side of the triangle is 1 and the other is 2
         *  2 squared + 1 squared is 5
         *  the square root of 5 is 2.23
         *
         */
        assertThat(distance).isCloseTo(2.236, Offset.offset(0.01))
    }
}