package com.dambra.kotlinadvent.week1

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class SimplifiedMapExamples {
    @Test
    fun `can route around obstacles on simple route`() {
        val route = """
S...
BB..
..X.""".findRoute()

        Assertions.assertThat(route).isEqualTo("""
**..
BB*.
..*.""".trim())
    }
}